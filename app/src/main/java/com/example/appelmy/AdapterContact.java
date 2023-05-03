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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactHolder> {
    private Context context;
    private ArrayList<ModelContact>contactList;

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
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

        holder.NameContact.setText(nom);
        if (image.equals(""))
        {
            holder.imageView.setImageResource(R.drawable.baseline_account_circle_24);
        }else
            holder.imageView.setImageURI(Uri.parse(image));


        holder.imageCall.setOnClickListener((View)->{
            Intent intent = new Intent(context, DetailContact.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder{

        ImageView imageView, imageCall;
        TextView NameContact;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            imageCall = itemView.findViewById(R.id.call);
            NameContact = itemView.findViewById(R.id.contactNom);

        }
    }
}
