package com.example.activities.data.rtdb.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.MainActivity;
import com.example.activities.PostActivitiyJava.PostActivity;
import com.example.activities.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileEditable extends AppCompatActivity {

    private EditText  email, name, occupation, education, gender, permission, birthday;

    private Button backToMainActivity;
    private ImageView saveChanges;
    private ImageButton changePhoto;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private String userID;


    private ImageView imageProfile;
    private Uri imageUri;
    private  String imageUrl = "";
    private StorageTask uploadTask;

    byte[] file;
    StorageReference storageRef;
    StorageReference ImRef;
    private final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_editable);

        Intent intent = getIntent();
        final String emailStr = intent.getStringExtra("email");
        Log.wtf("email ", emailStr);

        name = findViewById(R.id.nameOfTheUser);
        email = findViewById(R.id.emailOfTheUser);
        occupation = findViewById(R.id.occupationOfTheUser);
        education = findViewById(R.id.educationOfTheUser);
        gender = findViewById(R.id.genderOfTheUser);
        permission = findViewById(R.id.permissionOfTheUser);
        birthday = findViewById(R.id.birthdayOfTheUser);

        imageProfile = findViewById(R.id.imageViewProfile);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //change profile photo:
        storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
        ImRef = storageRef.child(System.currentTimeMillis() + ".jpeg");
        changePhoto = findViewById(R.id.imageButton);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the galery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
                Log.wtf("before upload", "before upload");
                uploadImage();
            }
        });

        //display the current data in the EditText
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("username").getValue().equals(emailStr)) {
                        name.setText(ds.child("firstName").getValue(String.class) + " " + ds.child("lastName").getValue(String.class));
                        email.setText(emailStr);

                        //--TO DO: HERE WE NEED TO IMPLEMENT SOLID PRINCIPELS OF POLIMORPHISEM!--

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
        saveChanges = findViewById(R.id.saveImage);
        saveChanges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newMail = email.getText().toString();
                String newOccupation = occupation.getText().toString();
                String newEducation = education.getText().toString();
                String newPermission = permission.getText().toString();
                String newBirthday = birthday.getText().toString();
                String newGender = gender.getText().toString();

                DatabaseReference currentUserRef = userRef.child(userID);
                currentUserRef.child("username").setValue(newMail);
                currentUserRef.child("occupation").setValue(newOccupation);
                currentUserRef.child("education").setValue(newEducation);
                currentUserRef.child("permission").setValue(newPermission);
                currentUserRef.child("dateOfBirth").setValue(newBirthday);
                currentUserRef.child("gender").setValue(newGender);

                Intent i = new Intent(UserProfileEditable.this, UserProfile.class);
                i.putExtra("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                startActivity(i);
                finish();
            }
        });

        //Main menu button
        backToMainActivity = findViewById(R.id.backToMainActivity);
        backToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileEditable.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //CropImage.activity().setAspectRatio(1, 1).start(UserProfileEditable.this);

    } //end of onCreate


    public void uploadImage(){
        Log.wtf("inside upload image", "inside upload image");
        if(file != null){
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
        if (requestCode ==  SELECT_PICTURE ) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        //Log.wtf("inside onActivityResult", "before crop image");
                        //CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        //Log.wtf("inside onActivityResult", "before image URi");
                        imageUri = data.getData();
                        imageProfile.setImageURI(imageUri);

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        Log.wtf("inside onActivityResult", "before getRound");
                        Bitmap bitmapRounded = getRoundedCroppedBitmap(bitmap);
                        file = baos.toByteArray();
                        //imageProfile.setImageBitmap(bitmapRounded);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == this.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }//end of onActivityResult

    //round the picture
    private Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {
        Log.wtf("inside getRoundedCroppedBitmap", "in the start");
        int widthLight = bitmap.getWidth();
        int heightLight = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));
        Log.wtf("widthLight", "" + widthLight);
        Log.wtf("heightLight", "" + heightLight);
        canvas.drawRoundRect(rectF, widthLight  ,heightLight, paintColor); //heightLight / 2
        Log.wtf("inside getRoundedCroppedBitmap", "before paint image");
        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, 0, 0, paintImage);

        return output;
    }

}//end class UserProfileEditable