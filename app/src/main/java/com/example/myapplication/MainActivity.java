package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Email;
import com.github.tamir7.contacts.PhoneNumber;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private Context context = MainActivity.this;

    RecyclerView recyclerView;
    List<ContactModel> cardList;
    FastAdapter<ContactModel> fastAdapter;
    ItemAdapter<ContactModel> itemAdapter;

    List<Contact> contacts;

    public static boolean isAppWentToBg = false;

    public static boolean isWindowFocused = false;

    public static boolean isMenuOpened = false;

    public static boolean isBackPressed = false;

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(fastAdapter);

        //itemAdapter.add(new ContactModel("hahaha", "8252385823"));
        //fastAdapter.notifyDataSetChanged();

        //Click management
        fastAdapter.withOnClickListener(new OnClickListener<ContactModel>() {
            @Override
            public boolean onClick(@Nullable View v, IAdapter<ContactModel> adapter, ContactModel item, int position) {
                //showDialog(item.getName(), item.getNumber());
                Log.d(TAG, "onClick: " + "item " + item.getName() + " clicked");
                String name = item.getName();
                String number = item.getNumber();
                String email = item.getEmail();
                String photoUri = item.getPhotoUri();

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                intent.putExtra("NAME", name);
                intent.putExtra("NUMBER", number);
                intent.putExtra("EMAIL", email);
                intent.putExtra("PHOTOURI", photoUri);
                startActivity(intent);

                return false;
            }
        });

        handlePermissions();

    }



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
//        contacts = Contacts.getQuery().find();
//        handleAddContacts();

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

    public void handleAddContacts(){
        try {
            for (Contact contact : contacts) {
                    String name = contact.getDisplayName();
                    String number = "";
                    String email = "";
                    String photoUri = contact.getPhotoUri();
                    if (contact.getPhoneNumbers().size() != 0 || contact.getPhoneNumbers() != null) {
                        for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
                            number += phoneNumber.getNumber() + "\n";
                        }
                        Log.d(TAG, "handleAddContacts: " + number);
                    }

                    if (contact.getEmails().size() != 0 || contact.getEmails() != null) {
                        for (Email e : contact.getEmails()) {
                            email += e.getAddress() + "\n";
                        }
                        Log.d(TAG, "handleAddContacts: " + email);

                    }
                    ContactModel x = new ContactModel(name, number, email, photoUri);
                    itemAdapter.add(x);
                    fastAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDialog(String name, String phoneNum){
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        final View content = layoutInflater.inflate(R.layout.about_contact, null, false);

        TextView aboutName = content.findViewById(R.id.name_about);
        TextView aboutNumber = content.findViewById(R.id.number_about);

        aboutName.setText(name);
        aboutNumber.setText(phoneNum);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setView(content);
        AlertDialog dialog = builder.create();
        // get the center for the clipping circle

        //final View view = dialog.getWindow().getDecorView();

        /**view.post(new Runnable() {
        @Override
        public void run() {
        final int centerX = view.getWidth() / 2;
        final int centerY = view.getHeight() / 2;
        // TODO Get startRadius from FAB
        // TODO Also translate animate FAB to center of screen?
        float startRadius = 20;
        float endRadius = view.getHeight();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        animator.setDuration(500);
        animator.start();
        }
        });**/

        dialog.show();
    }

    public void handlePermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//                Toast.makeText(this, "Permissions are required to open the contacts list", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // No explanation needed; request the permission
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            requestContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestContacts();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied, boo!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void requestContacts(){
        Contacts.initialize(this);
        //Populate a list with ContactModel objects
        contacts = Contacts.getQuery().find();
        handleAddContacts();
    }
}
