package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tamir7.contacts.Contacts;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {
    public static String TAG = AboutActivity.class.getSimpleName();

    TextView nameTextView;
    TextView emailTextView;
    TextView phoneNumTextView;
    TextView photoUriTextView;
    CircleImageView photoImageView;

    String name;
    String phoneNum;
    String email;
    String photoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_contact);


        Contacts.initialize(this);

        nameTextView = findViewById(R.id.name_about);
        phoneNumTextView = findViewById(R.id.number_about);
        emailTextView = findViewById(R.id.email_about);
        photoUriTextView = findViewById(R.id.photouri_about);
        photoImageView = findViewById(R.id.imageView);

       // LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) photoImageView.getLayoutParams();
        //params.width = 60;
// existing height is ok as is, no need to edit it
        //photoImageView.setLayoutParams(params);

        name = getIntent().getStringExtra("NAME");
        phoneNum = getIntent().getStringExtra("NUMBER");
        email = getIntent().getStringExtra("EMAIL");
        photoUri = getIntent().getStringExtra("PHOTOURI");

        try{
            photoImageView.setImageURI(Uri.parse(photoUri));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.d(TAG, "AboutActivity: " + name);
        Log.d(TAG, "AboutActivity: " + phoneNum);
        Log.d(TAG, "AboutActivity: " + email);
        Log.d(TAG, "AboutActivity: " + photoUri);


        nameTextView.setText(name);
        phoneNumTextView.setText(phoneNum);
        emailTextView.setText(email);
        photoUriTextView.setText(photoUri);
    }

}
