package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.tamir7.contacts.Contacts;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {
    public static String TAG = AboutActivity.class.getSimpleName();

    TextView nameTextView;
    TextView emailTextView;
    TextView phoneNumTextView;
    CircleImageView photoImageView;

    String name;
    String phoneNum;
    String email;
    String photoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.about_contact);

        Contacts.initialize(this);

        nameTextView = findViewById(R.id.name_about);
        phoneNumTextView = findViewById(R.id.number_about);
        emailTextView = findViewById(R.id.email_about);
        photoImageView = findViewById(R.id.imageView);

       // LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) photoImageView.getLayoutParams();
        //params.width = 60;
// existing height is ok as is, no need to edit it
        //photoImageView.setLayoutParams(params);

        Toast.makeText(this, photoUri, Toast.LENGTH_SHORT).show();

        name = getIntent().getStringExtra("NAME");
        phoneNum = getIntent().getStringExtra("NUMBER");
        email = getIntent().getStringExtra("EMAIL");
        photoUri = getIntent().getStringExtra("PHOTOURI");

        Log.d(TAG, "photoUri: " + photoUri) ;
        try{
            if(photoUri != null)
                photoImageView.setImageURI(Uri.parse(photoUri));
            else
                photoImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dummyuser));
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

    }

}
