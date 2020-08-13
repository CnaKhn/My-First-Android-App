package com.cnakhn.faradarscompletion.Contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnakhn.faradarscompletion.R;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private ArrayList<String> contacts = new ArrayList<>();
    public Context context;
    EditText inputContactName;
    Button btnSaveContact;
    Dialog dialogChangeContactInfo;

    public ContactsAdapter(Context context) {
        this.context = context;
        contacts.add("Ruthann Trustrie");
        contacts.add("Peadar Dawtrey");
        contacts.add("Felipe Bradtke");
        contacts.add("Claude Crissil");
        contacts.add("Jacky Girardeau");
        contacts.add("Rubia Dominguez");
        contacts.add("Michaela Churchley");
        contacts.add("Harvey Pentelow");
        contacts.add("Neilla Langton");
        contacts.add("Marco Greaves");
        contacts.add("Liz Batchley");
        contacts.add("Lamond Littlepage");
        contacts.add("Marco Greaves");
        contacts.add("Malina Weir");
        contacts.add("Marco Greaves");
        contacts.add("Tomlin Lenchenko");
        contacts.add("Hy Pavelin");
        contacts.add("Damon Knewstubb");
        contacts.add("Alex Ivanusyev");
        contacts.add("Hamil Callery");
        contacts.add("Karol Syer");
        Log.i("ContactsAdapter", "getItemCount: " + getItemCount());
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_contact, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactsViewHolder holder, final int position) {
        holder.bind(contacts.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
                dialogChangeContactInfo.show();
                inputContactName.requestFocus();
                inputContactName.setText(holder.tvName.getText().toString());
                btnSaveContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String contactName = inputContactName.getText().toString();
                        holder.tvName.setText(contactName);
                        updateContact(contactName, position);
                        dialogChangeContactInfo.dismiss();
                    }
                });
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Remove Contact " + holder.tvName.getText() + "?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contacts.remove(position);
                        notifyItemRemoved(position);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                builder.show();
                return false;
            }
        });
    }


    private void updateContact(String contactName, int position) {
        contacts.set(position, contactName);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addContact(String name) {
        contacts.add(0, name);
        notifyItemInserted(0);
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFirstChar, tvName;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstChar = itemView.findViewById(R.id.tv_contact_first_char);
            tvName = itemView.findViewById(R.id.tv_contact_name);
        }

        public void bind(String name) {
            tvName.setText(name);
            tvFirstChar.setText(name.substring(0, 1));
        }
    }

    private void initDialog() {
        dialogChangeContactInfo = new Dialog(context);
        dialogChangeContactInfo.setContentView(R.layout.layout_add_edit_contact);
        inputContactName = dialogChangeContactInfo.findViewById(R.id.input_add_contact_name);
        btnSaveContact = dialogChangeContactInfo.findViewById(R.id.btn_save_add_contact);
        btnSaveContact.setText("Submit");
        TextView tvAddContact = dialogChangeContactInfo.findViewById(R.id.tv_add_edit_contact);
        tvAddContact.setText("Edit Contact");
    }
}
