package com.example.activities.data.rtdb.activity;

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
    private ImageButton changePhoto;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private String userID;


    private ImageView imageProfile;
    private Uri imageUri;
    private String imageUrl = "";
    private StorageTask uploadTask;

    byte[] file;
    StorageReference storageRef;
    StorageReference ImRef;
    private final int SELECT_PICTURE = 1;

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

        imageProfile = findViewById(R.id.imageViewEdit);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //change profile photo:
        storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
        ImRef = storageRef.child(System.currentTimeMillis() + ".jpeg");
        changePhoto = findViewById(R.id.imageButtonEdit);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the galery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                uploadImage();
            }
        });

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
                        // ----------- TO IMPLEMENT ------------- //
//                        if(ds.child("pictureUrl").exists()) {
//                            imageProfile.setImageURI();
//                        }

                        // userRef.removeEventListener(this);
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
                String newPhone= phone.getText().toString();

                String[] nameSplited=newName.split(" ");
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


        //CropImage.activity().setAspectRatio(1, 1).start(UserProfileEditable.this);

    } //end of onCreate


    public void uploadImage() {
        if (file != null) {
            UploadTask uploadTask = ImRef.putBytes(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // ------ TO COMPLETE!!!! ------ //
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
    }//end of uploadImage

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        //Log.wtf("inside onActivityResult", "before crop image");
                        //CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        //Log.wtf("inside onActivityResult", "before image URi");
                        imageUri = data.getData();
                        imageProfile.setImageURI(imageUri);

                        Picasso.get()
                                .load(imageUri)
                                .transform(new CircleTransform())
                                .fit()
                                .into(imageProfile);

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        //Bitmap bitmapRounded = getRoundedCroppedBitmap(bitmap);
                        file = baos.toByteArray();
                        //imageProfile.setImageBitmap(bitmapRounded);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == this.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }//end of onActivityResult

    //round the picture
//    private Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {
//        int widthLight = bitmap.getWidth();
//        int heightLight = bitmap.getHeight();
//
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(output);
//        Paint paintColor = new Paint();
//        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);
//
//        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));
//        canvas.drawRoundRect(rectF, widthLight  ,heightLight, paintColor); //heightLight / 2
//        Paint paintImage = new Paint();
//        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        canvas.drawBitmap(bitmap, 0, 0, paintImage);
//
//
//        return output;
//    }

}//end class UserProfileEditable