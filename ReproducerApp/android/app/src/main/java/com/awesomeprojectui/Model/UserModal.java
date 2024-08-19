package com.blockerplus.blockerplus.Model;

public class UserModal {
    private String email,id,userName,photo,partnerEmail,pin,isPremiumUser,productId,productToken;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }
    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }


    public String getIsPremiumUser() {
        return isPremiumUser;
    }
    public void setIsPremiumUser(String isPremiumUser) {
        this.isPremiumUser = isPremiumUser;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductToken() {
        return productToken;
    }
    public void setProductToken(String productToken) {
        this.productToken = productToken;
    }
}
