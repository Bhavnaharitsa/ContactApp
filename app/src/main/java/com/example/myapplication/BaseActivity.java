/**import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;

import java.util.List;

android.content.pm.ActivityInfo;

iort android.content.pm.ActivityInfo;

i**mport android.content.pm.ActivityInfo;

/package com.example.myapplication;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getName();

    public static List<Contact> getContactList() {
        return contactList;
    }

    public static List<Contact> contactList;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Contacts.initialize(this);

        contactList = Contacts.getQuery().find();


    }

    public static boolean isAppWentToBg = false;

    public static boolean isWindowFocused = false;

    public static boolean isMenuOpened = false;

    public static boolean isBackPressed = false;

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);

        applicationWillEnterForeground();

        super.onStart();
    }

    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
            Toast.makeText(getApplicationContext(), "App is in foreground",
                    Toast.LENGTH_SHORT).show();
        }
        contactList = Contacts.getQuery().find();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop ");
        applicationdidenterbackground();
    }

    public void applicationdidenterbackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
            Toast.makeText(getApplicationContext(),
                    "App is Going to Background", Toast.LENGTH_SHORT).show();
        }

        //When the app goes to bg, try to refresh the list
        contactList = Contacts.getQuery().find();
    }

    @Override
    public void onBackPressed() {

        if (this instanceof MainActivity) {

        } else {
            isBackPressed = true;
        }

        Log.d(TAG,
                "onBackPressed " + isBackPressed + ""
                        + this.getLocalClassName());
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        isWindowFocused = hasFocus;

        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }

        super.onWindowFocusChanged(hasFocus);
    }
}
 **/