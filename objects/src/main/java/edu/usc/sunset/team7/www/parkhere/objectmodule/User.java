package edu.usc.sunset.team7.www.parkhere.objectmodule;

import java.util.List;

import javax.swing.ImageIcon;

/**
 * Created by Acer on 10/14/2016.
 */

public class User extends PublicUserProfile {

    public enum defaultScreen {
        SEEKER, PROVIDER
    }

    private String lastName;
    private String userID;
    private String phoneNumber;
    private ImageIcon profilePicture; //need import
    private String email;
    private defaultScreen accountType;

    public User(String firstName, String lastName, double rating, List<Review> reviews, String phoneNumber, ImageIcon profilePicture, String email){
        super(firstName, rating, reviews);
        this.email = email;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String firstName, String lastName){
        if (firstName != null) {
            this.firstName = firstName;
        }
        if (lastName != null) {
            this.lastName = lastName;
        }
    }

    public void setDefaultScreen(defaultScreen accountType) {
        this.accountType = accountType;
    }

    public defaultScreen getAccountType() {
        return this.accountType;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ImageIcon getProfilePicture () {
        return this.profilePicture;
    }

    public void setProfilePicture (ImageIcon profilePicture){
        this.profilePicture = profilePicture;
    }

    public String getEmail () {
        return this.email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

//    public PublicUserProfile getPublicProfile () {
//        return super(this.firstName, this.rating, this.reviews);
//    }

}
