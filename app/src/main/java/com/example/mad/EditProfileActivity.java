package com.example.mad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    public ImageView profileImageView;
    public Button EditButton;
    public Button BtnConfirm;
    public TextView profileChangeButon;
    public EditText ETName, ETCollege;
    public TextView TVAskName, TVAskCollege;

    private FirebaseFirestore EditProfiledb = FirebaseFirestore.getInstance();
    private CollectionReference fb = EditProfiledb.collection("Login");
    private CollectionReference EditProfilefb = EditProfiledb.collection("Profile");

    public String Name, College, identifier;
    public Uri initialUri;

    public DatabaseReference databaseReference;
    public FirebaseAuth mAuth;

    private Uri imageUri;
    public StorageTask uploadTask;
    public StorageReference storageProfilePicsRef;
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;



    // Create a storage reference from our app
//    StorageReference storageRef = storage.getReference();

    // Create a reference to "mountains.jpg"
//    StorageReference mountainsRef = storageRef.child("mountains.jpg");

    // Create a reference to 'images/mountains.jpg'
//    StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");

// While the file names are the same, the references point to different files
//    mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//    mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

    public void LiveUpdate(){
        DocumentReference docRef = EditProfilefb.document(identifier);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Map<String, Object> newData = document.getData();

                        TVAskName.setText("Name: " + newData.get("Name").toString());
                        TVAskCollege.setText("Residential College: " +newData.get("Kk").toString());
                    } else {

                    }
                } else{
                    return;
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        String email = mAuth.getCurrentUser().getEmail().toString();
        identifier = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i)=='@' || email.charAt(i)=='.'){
                identifier += "_";
            }
            else{
                identifier += email.charAt(i);
            }
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("/ProfilePic" + identifier);

        ProfileImage = (CircleImageView) findViewById(R.id.imagePFP2);

        if (initialUri==null){

        } else{
            ProfileImage.setImageURI(initialUri);
        }


        ETName = (EditText) findViewById(R.id.ETName);
        ETCollege = (EditText) findViewById(R.id.ETCollege);
        TVAskName = (TextView) findViewById(R.id.TVAskName);
        TVAskCollege = (TextView) findViewById(R.id.TVAskCollege);

        LiveUpdate();

        EditButton = (Button) findViewById(R.id.EditButton);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select Picture"), PICK_IMAGE);
            }
        });

        BtnConfirm = (Button) findViewById(R.id.BtnConfirm);
        String finalIdentifier = identifier;
        BtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = ETName.getText().toString();
                College = ETCollege.getText().toString();
                if (Name.isEmpty() || College.isEmpty()){
                    return;
                }
                Map<String,String> Profile = new HashMap<>();
                Profile.put("Name",Name);
                Profile.put("Kk",College);
                EditProfilefb.document(finalIdentifier).set(Profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditProfileActivity.this, "Profile Updated!!!", Toast.LENGTH_SHORT).show();
                        LiveUpdate();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        Toolbar toolbar = findViewById(R.id.materialToolbar);
        TextView toolbartitle = findViewById(R.id.toolbar_title);

        ViewGroup parent = (ViewGroup) toolbartitle.getParent();
        if (parent != null) {
            parent.removeView(toolbartitle);
        }

        toolbar.addView(toolbartitle);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText editText = findViewById(R.id.ETName);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        showKeyboard(editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // get value from edittext
                String s = editText.getText().toString().trim();

                if(actionId == EditorInfo.IME_ACTION_DONE){

                    hideKeyboard(editText);

                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.hideSoftInputFromWindow(editText.getApplicationWindowToken()
                , 0 );
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.showSoftInput(editText.getRootView()
                ,InputMethodManager.SHOW_IMPLICIT);

        editText.requestFocus();}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            ProfileImage.setImageURI(imageUri);
            ProfileFragment.imagePFP.setImageURI(imageUri);
            initialUri = imageUri;
//            uploadPicture(imageUri);
//            try{
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
//                ProfileImage.setImageBitmap(bitmap);

//            }catch (IOException e){
//                e.printStackTrace();
//            }
        }
    }

    public void uploadPicture(Uri imageURI){
        final String randomKey = UUID.randomUUID().toString();
        storageProfilePicsRef = FirebaseStorage.getInstance("gs://booking-735b6.appspot.com/").getReference().child("ProfilePic/" + identifier + "/" + randomKey);
        storageProfilePicsRef.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Profile Picture Uploaded!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to Upload Profile Picture!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
    }
}