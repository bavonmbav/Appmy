package com.example.appelmy;

public class ModelContact {
   private String id,image,nom,phhone,emails,notes;

    public String getImage() {
        return image;
    }

    public ModelContact(String id, String image, String nom, String phhone, String emails, String notes) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.phhone = phhone;
        this.emails = emails;
        this.notes = notes;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhhone() {
        return phhone;
    }

    public void setPhhone(String phhone) {
        this.phhone = phhone;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
