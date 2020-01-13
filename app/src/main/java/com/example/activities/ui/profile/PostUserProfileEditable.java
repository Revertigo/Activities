package com.example.activities.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.data.entities.user.PostUser;
import com.example.activities.data.entities.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PostUserProfileEditable extends AppCompatActivity {

    private EditText name, occupation, education, gender, birthday, phone;
    private TextView email, permission;

    private Button backToMainMenu;
    private ImageView saveChanges;
    private Uri mImageUri;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private ImageButton imageButtonEdit;
    private ImageView profileImage;
    private DatabaseReference mDatabaseRefProfile;
    private static final int PICK_IMAGE = 1;
    private StorageReference mRefProfileImages;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user_profile_editable);

        email = findViewById(R.id.emailOfThePostUserEdit);
        name = findViewById(R.id.nameOfThePostUserEdit);
        occupation = findViewById(R.id.occupationOfThePostUserEdit);
        education = findViewById(R.id.educationOfThePostUserEdit);
        gender = findViewById(R.id.genderOfThePostUserEdit);
        permission = findViewById(R.id.permissionOfThePostUserEdit);
        birthday = findViewById(R.id.birthdayOfThePostUserEdit);
        phone = findViewById(R.id.phoneOfThePostUserEdit);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        profileImage = findViewById(R.id.imageViewPostUserEdit);
        mDatabaseRefProfile = FirebaseDatabase.getInstance().getReference("users");
        mRefProfileImages = FirebaseStorage.getInstance().getReference("profile_images");


        imageButtonEdit = findViewById(R.id.imageButtonPostUserEdit);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage(v);
            }
        });


        loadUserPicture();
        name.setText(User.getCurrentUser().getFirstName() + " " + User.getCurrentUser().getLastName());
        phone.setText(User.getCurrentUser().getPhone());
        email.setText(User.getCurrentUser().getUsername());
        permission.setText(User.getCurrentUser().getPermission());
        birthday.setText(User.getCurrentUser().getDateOfBirth());
        gender.setText(User.getCurrentUser().getGender());
        occupation.setText(((PostUser) User.getCurrentUser()).getOccupation());
        education.setText(((PostUser) User.getCurrentUser()).getEducation());


        //update changes:
        saveChanges = findViewById(R.id.saveImagePostUserEdit);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newOccupation = occupation.getText().toString();
                String newEducation = education.getText().toString();
                String newBirthday = birthday.getText().toString();
                String newGender = gender.getText().toString();
                String newPhone = phone.getText().toString();

                String[] nameSplited = newName.split(" ");
                DatabaseReference currentUserRef = userRef.child(userID);
                currentUserRef.child("firstName").setValue(nameSplited[0]);
                currentUserRef.child("lastName").setValue(nameSplited[1]);
                currentUserRef.child("occupation").setValue(newOccupation);
                currentUserRef.child("education").setValue(newEducation);
                currentUserRef.child("dateOfBirth").setValue(newBirthday);
                currentUserRef.child("gender").setValue(newGender);
                currentUserRef.child("phone").setValue(newPhone);

                User.getCurrentUser().setFirstName(nameSplited[0]);
                User.getCurrentUser().setLastName(nameSplited[1]);
                ((PostUser) User.getCurrentUser()).setOccupation(newOccupation);
                ((PostUser) User.getCurrentUser()).setEducation(newEducation);
                User.getCurrentUser().setPhone(newPhone);
                User.getCurrentUser().setDateOfBirth(newBirthday);
                User.getCurrentUser().setGender(newGender);

                Intent i = new Intent(PostUserProfileEditable.this, PostUserProfile.class);
                startActivity(i);
                finish();
            }
        });

        //Main menu button
        backToMainMenu = findViewById(R.id.backToSearchOrPostPostUserEditable);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(PostUserProfileEditable.this);
                startActivity(intent);
                finish();
            }
        });//


    } //end of onCreate


    private void loadUserPicture() {
        String pathToPicture = User.getCurrentUser().getpictureUri();
        if (pathToPicture != null) {
            //check if the user has already an profile image
            // if so, Load it
            if (!(pathToPicture.equals(""))) {
                Picasso.get()
                        .load(pathToPicture)
                        .transform(new CircleTransform())
                        .fit()
                        .into(profileImage);
            }  else {
                profileImage.setImageResource(R.drawable.ic_person_black_24dp);

            }
            //uses default image
        }  else {
            profileImage.setImageResource(R.drawable.ic_person_black_24dp);

        } 
    }


    // 1) ------- Method called when clicking pn button upload  - open gallery -------
    public void uploadProfileImage(View view) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }

    // 2) ------- This function is called when we picked an image from gallery -------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                //change that
                profileImage.setImageBitmap(bitmap);
//                  Load new Profile image to the storage and database
                uploadFile(mRefProfileImages, mDatabaseRefProfile);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // 3) -------  uoload file to storage and data bases ------
    private void uploadFile(StorageReference mStorageRef, final DatabaseReference mDatabaseRef) {

        if (mImageUri != null) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Uploading ...");
            mProgressDialog.show();

            final StorageReference fileReference = mStorageRef
                    .child(System.currentTimeMillis() + ".jpg");

            // upload image int o storagebase
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressDialog.dismiss();
                    Toast.makeText(PostUserProfileEditable.this, "File Uploaded ", Toast.LENGTH_LONG).show();
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uploadImage = uri.toString();
                            // add url into user profile data in database
                            mDatabaseRef.child(userID).child("pictureUri").setValue(uploadImage);
                            User.getCurrentUser().setpictureUri(uploadImage);
                            loadUserPicture();

                        }
                    });

                    mProgressDialog.dismiss();
                }//END onSuccess

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(PostUserProfileEditable.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });

        } else {
            Toast.makeText(this, "No file was selected", Toast.LENGTH_SHORT).show();
        }
    }
}
