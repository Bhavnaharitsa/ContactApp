package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Contacts.initialize(this);

        recyclerView = findViewById(R.id.recycler);

        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(fastAdapter);

        //itemAdapter.add(new ContactModel("hahaha", "8252385823"));
        //fastAdapter.notifyDataSetChanged();

        //Populate a list with ContactModel objects
         contacts = Contacts.getQuery().find();

        handleAddContacts();

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
        contacts = Contacts.getQuery().find();
        handleAddContacts();

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
        for(Contact contact : contacts){
            try {
                String name = contact.getDisplayName();
                String number = "";
                String email = "";
                String photoUri = contact.getPhotoUri();
                if(contact.getPhoneNumbers().size() != 0 || contact.getPhoneNumbers() != null){
                    for(PhoneNumber phoneNumber : contact.getPhoneNumbers()){
                        number += phoneNumber.getNumber() + "\n";
                    }
                    Log.d(TAG, "handleAddContacts: " + number);
                }

                if(contact.getEmails().size() != 0 || contact.getEmails() != null){
                    for(Email e : contact.getEmails()){
                        email += e.getAddress() + "\n";
                    }
                    Log.d(TAG, "handleAddContacts: " + email);

                }
                ContactModel x = new ContactModel(name, number, email, photoUri);
                itemAdapter.add(x);
                fastAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
}
