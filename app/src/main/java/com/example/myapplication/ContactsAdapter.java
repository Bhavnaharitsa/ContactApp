/**import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ContactModel;
import com.example.myapplication.R;

import java.util.List;

import static android.content.ContentValues.TAG;

mport android.content.Context;

/package com.example.myapplication;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<ContactModel> mContacts;

    private Context mContext;

    public ContactsAdapter(Context context, List<ContactModel> contacts)
    {
        mContacts = contacts;
        mContext = context;
    }

    private Context getContexT(){
        return mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView nameTextView = holder.nameTextView;
        TextView numberTextView = holder.numberTextView;
        ContactModel contact = mContacts.get(position);
        nameTextView.setText(contact.getName());
        numberTextView.setText(""+contact.getNumber());
        Log.d(TAG, "onBindViewHolder: " + nameTextView.getText());
    //    button.setText(contact.isOnline() ? "Online":"Offline");
      //  button.setEnabled(contact.isOnline());
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+ mContacts.size());
        return mContacts.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView numberTextView;


        public ViewHolder(View itemView)
        {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.contact_name);
            numberTextView = itemView.findViewById(R.id.contact_number);
        }
    }
}**/