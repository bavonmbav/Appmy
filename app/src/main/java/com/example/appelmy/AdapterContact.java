package com.example.appelmy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactHolder> {
    private Context context;
    private ArrayList<ModelContact>contactList;

    Dbhelpe dbhelpe;

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbhelpe = new Dbhelpe(context);
    }

    //creation de la vue
    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent,false);
        ContactHolder holder = new ContactHolder(view);
        return holder;
    }
//lier les données d'un élément de la liste aux vues correspondantes qui seront affichées à l'écran.
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        ModelContact contact = contactList.get(position);
        String id = contact.getId();
        String image = contact.getImage();
        String nom = contact.getNom();
        String phone= contact.getPhhone();
        String email = contact.getEmails();
        String note = contact.getNotes();
        holder.NameContact.setText(nom);
        if (image.equals(""))
        {
            holder.imageView.setImageResource(R.drawable.baseline_account_circle_24);
        }else
            holder.imageView.setImageURI(Uri.parse(image));


        holder.imageCall.setOnClickListener((View)->{
            Intent intent = new Intent(context, DetailContact.class);
                intent.putExtra("contactId",id);
                intent.putExtra("isDetail",true);
                context.startActivity(intent);
        });
// modifier un contact
        holder.contaced.setOnClickListener((View)->{
            // create intent to move AddEditActivity to update data
            Intent intent = new Intent(context,AddContact.class);
            //pass the value of current position
            intent.putExtra("ID",id);
            intent.putExtra("NAME",nom);
            intent.putExtra("PHONE",phone);
            intent.putExtra("EMAIL",email);
            intent.putExtra("NOTE",note);
            intent.putExtra("IMAGE",image);

            // pass a boolean data to define it is for edit purpose
            intent.putExtra("isEditMode",true);

            //start intent
            context.startActivity(intent);

        });

// supprimer un contact
        holder.contactSup.setOnClickListener((View)->{
            dbhelpe.deleteContact(id);

            //refresh data by calling resume state of MainActivity
            ((MainActivity)context).onResume();
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder{

        ImageView imageView, imageCall,contaced,contactSup;
        TextView NameContact;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            imageCall = itemView.findViewById(R.id.call);
            NameContact = itemView.findViewById(R.id.contactNom);
            contaced = itemView.findViewById(R.id.Editeur);
            contactSup = itemView.findViewById(R.id.Deletes);

        }
    }
}
