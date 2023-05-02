package com.example.appelmy;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddContact extends AppCompatActivity {

    private ImageView imageView;
    private EditText name,phone,emails,notes;

    String nom,email,phonee,notess;

    ActionBar actionBar;

    Dbhelpe dbhelpe;

    //constante de permission
    private  static final int IMAGE_PERMISSION = 100;
    private  static final int IMAGE_STORAGE = 200;
    private  static final int IMAGE_GALERY = 300;
    private  static final int IMAGE_CAMERA = 400;


    //tablau de permission
    private  String [] cameraPermission;
    private  String [] storagePermission;

    //adress de l'image
    Uri image;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        dbhelpe = new Dbhelpe(this);

        cameraPermission = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE };
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



        actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle("Ajouter un contact");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




        initialise();



    }

    private void showImage() {
        //option
        String[] option = {"Camera", "Galery"};

        //boite de dialogue de l'alerte
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("choix d'option");
        builder.setItems(option, (dialogInterface, i) -> {
            if (i == 0)
            {
                if (!prendrePhoto())
                    requetCamera();
                else
                    pickFrom();

            }else if (i == 1)
            {
                if (!prendrePhot())
                    requetStorage();
                else
                    picturegalery();
            }
        }).create().show();
    }

    //choix dans la gallery
    private void picturegalery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, IMAGE_GALERY);

    }


    //      prendre photo
    private void pickFrom() {
        ContentValues values =  new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image");

        image  = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image);

        startActivityForResult(intent, IMAGE_CAMERA);
    }



    //initialiser les variables
    public void initialise(){
        imageView = findViewById(R.id.imageView);
        emails = findViewById(R.id.email);
        name = findViewById(R.id.namTe);
        phone = findViewById(R.id.Phone);
        notes = findViewById(R.id.textmot);
        FloatingActionButton add = findViewById(R.id.flota);

        add.setOnClickListener((View)->{
            saveAdd();
        });

        imageView.setOnClickListener((View)->{
            showImage();
        });
    }

    private void saveAdd() {

        nom = name.getText().toString();
        email = emails.getText().toString();
        phonee = phone.getText().toString();
        notess = notes.getText().toString();
        if (!nom.isEmpty() || !email.isEmpty() || !phonee.isEmpty() || !notess.isEmpty()){
           long id =  dbhelpe.insertContact(
                                ""+image,
                                ""+nom,
                                ""+phonee,
                                ""+email,
                                ""+notess
            );
            Toast.makeText(getApplicationContext(), "Enregistrer " + id, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(), "Not save", Toast.LENGTH_SHORT).show();
        }



    }

    //prendre la photo
    private boolean prendrePhoto(){
        boolean resultat = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean resultat2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return resultat & resultat2;
    }

    //requette pour la camera
    private void requetCamera() {
        ActivityCompat.requestPermissions(this, cameraPermission,IMAGE_PERMISSION);
    }
    //requetStorage
    private void requetStorage() {
        ActivityCompat.requestPermissions(this, storagePermission, IMAGE_STORAGE);
    }
    //storage permission
    private boolean prendrePhot(){
        boolean resultat1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return resultat1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case IMAGE_PERMISSION:
                if (grantResults.length>0){

                    boolean cameraAcces = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean starageAcces = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAcces && starageAcces)
                    {
                        pickFrom();
                    }else
                        Toast.makeText(getApplicationContext(), "Camera de stockage", Toast.LENGTH_SHORT).show();
                }
                break;
            case IMAGE_STORAGE:
                if (grantResults.length>0){


                    boolean starageAcces = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (starageAcces)
                    {
                        picturegalery();
                    }else
                        Toast.makeText(getApplicationContext(), "Camera de stockage", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            if (requestCode == IMAGE_GALERY){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == IMAGE_CAMERA) {
                CropImage.activity(image)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult reult = CropImage.getActivityResult(data);
                image = reult.getUri();
                imageView.setImageURI(image);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "something", Toast.LENGTH_SHORT).show();
            }
    }
}

