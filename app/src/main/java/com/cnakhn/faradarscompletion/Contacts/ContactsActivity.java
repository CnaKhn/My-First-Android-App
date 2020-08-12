package com.cnakhn.faradarscompletion.Contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

public class ContactsActivity extends AppCompatActivity {
    RecyclerView rvContacts;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = findViewById(R.id.activity_contacts_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initViews();
    }

    private void initViews() {
        rvContacts = findViewById(R.id.rv_contacts);
        adapter = new ContactsAdapter(this);
        rvContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvContacts.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final Dialog dialogAddContact = new Dialog(ContactsActivity.this);
        dialogAddContact.setContentView(R.layout.layout_add_edit_contact);
        TextView tvAddContact = dialogAddContact.findViewById(R.id.tv_add_edit_contact);
        tvAddContact.setText("Add new Contact");
        menu.add("Add Contact").setIcon(R.drawable.ic_add_contact).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dialogAddContact.show();
                final EditText inputContactName = dialogAddContact.findViewById(R.id.input_add_contact_name);
                inputContactName.requestFocus();
                Button btnContactSave = dialogAddContact.findViewById(R.id.btn_save_add_contact);
                btnContactSave.setText("Save Contact");
                btnContactSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputContactName.length() > 0) {
                            adapter.addContact(inputContactName.getText().toString());
                            rvContacts.smoothScrollToPosition(0);
                            dialogAddContact.dismiss();
                        } else {
                            inputContactName.requestFocus();
                            inputContactName.setError("Enter your contact name");
                        }
                    }
                });

                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

}