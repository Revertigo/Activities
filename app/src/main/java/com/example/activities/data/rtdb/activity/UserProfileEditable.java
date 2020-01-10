package com.example.activities.data.rtdb.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.SearchActivity.SearchOrPost;
import com.example.activities.ui.login.user.User;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileEditable extends AppCompatActivity {

    private EditText name, occupation, education, gender, birthday, phone;
    private TextView email, permission;

    private Button backToMainMenu;
    private ImageView saveChanges;
    private Uri mImageUri;
    private FirebaseDatabase database;
    private DatabaseReference userRef;


    private ImageView imageProfile;
    private ImageButton changePhoto;
    private DatabaseReference mDatabaseRefProfile;
    private static final int PICK_IMAGE = 1;
    private StorageReference mRefProfileImages;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_editable);

        final String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email = findViewById(R.id.emailOfTheUserEdit);
        name = findViewById(R.id.nameOfTheUserEdit);
        occupation = findViewById(R.id.occupationOfTheUserEdit);
        education = findViewById(R.id.educationOfTheUserEdit);
        gender = findViewById(R.id.genderOfTheUserEdit);
        permission = findViewById(R.id.permissionOfTheUserEdit);
        birthday = findViewById(R.id.birthdayOfTheUserEdit);
        phone = findViewById(R.id.phoneOfTheUserEdit);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        imageProfile = findViewById(R.id.imageViewEdit);
        mDatabaseRefProfile = FirebaseDatabase.getInstance().getReference("users");
        mRefProfileImages = FirebaseStorage.getInstance().getReference("profile_images");



        //display the current data in the EditText
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("username").getValue(String.class).equals(emailStr)) {
                        email.setText(emailStr);
                        name.setText(ds.child("firstName").getValue(String.class) + " " + ds.child("lastName").getValue(String.class));
                        //--TO DO: HERE WE NEED TO IMPLEMENT SOLID PRINCIPELS OF POLIMORPHISEM!--

                        if (ds.child("phone").exists()) {
                            phone.setText(ds.child("phone").getValue(String.class));
                        } else {
                            phone.setText("Phone:");
                        }

                        if (ds.child("occupation").exists()) {
                            occupation.setText(ds.child("occupation").getValue(String.class));
                        } else {
                            occupation.setText("Occupation: ");
                        }
                        if (ds.child("education").exists()) {
                            education.setText(ds.child("education").getValue(String.class));
                        } else {
                            education.setText("Education:");
                        }
                        permission.setText(ds.child("permission").getValue(String.class));
                        birthday.setText(ds.child("dateOfBirth").getValue(String.class));
                        gender.setText(ds.child("gender").getValue(String.class));

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //update changes:
        saveChanges = findViewById(R.id.saveImageEdit);
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

                Intent i = new Intent(UserProfileEditable.this, UserProfile.class);
                startActivity(i);
                finish();
            }
        });

        //Main menu button
        backToMainMenu = findViewById(R.id.backToSearchOrPost);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(UserProfileEditable.this);
                startActivity(intent);
            }
        });

        imageProfile.setImageResource(R.drawable.ic_person_black_24dp);

        loadProfileImage();

    } //end of onCreate


    private void loadProfileImage() {
        mDatabaseRefProfile.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String pathToPicture = dataSnapshot.child(userID).child("pictureUri").getValue(String.class);

                if (pathToPicture != null) {
                    pathToPicture = pathToPicture;


                    //check if the user has already an profile image
                    // if so, Load it
                    if (!(pathToPicture.equals(""))) {
                        Picasso.get()
                                .load(pathToPicture)
                                .transform(new CircleTransform())
                                .fit()
                                .into(imageProfile);
                        //mProfileImage.setImageBitmap(BitmapFactory.decodeFile(pathToPicture));
                    }
                    //uses default image
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }// end loadProfileImage


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
                imageProfile.setImageBitmap(bitmap);
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
                    Toast.makeText(UserProfileEditable.this, "File Uploaded ", Toast.LENGTH_LONG).show();
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uploadImage = uri.toString();
                            // add url into user profile data in database
                            mDatabaseRef.child(userID).child("pictureUri").setValue(uploadImage);

                        }
                    });

                    mProgressDialog.dismiss();
                }//END onSuccess

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(UserProfileEditable.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


}//end class UserProfileEditable