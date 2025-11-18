package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserRequest {

    public UpdateUserRequest() {
        // no need for initializations here
    }

    public UpdateUserRequest(
            String name, String email, String phone, String alternateEmail, String alternatePhone) {
        this.email = email;
        String[] names = name.split(" ");
        if (names.length < 2) this.name = name;
        else {
            this.firstName = names[0];
            this.lastName = names[names.length - 1];
        }
        this.phone = phone;
        this.alternateEmail = alternateEmail;
        this.alternatePhone = alternatePhone;
    }

    public UpdateUserRequest(String name, String email, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public UpdateUserRequest(String alternateEmail, String alternatePhone) {
        this.alternateEmail = alternateEmail;
        this.alternatePhone = alternatePhone;
    }

    public static UpdateUserRequest getUpdateUserRequestForNameUpdate(
            String salutation, String firstName, String lastName) {
        UpdateUserRequest params = new UpdateUserRequest();
        params.salutation = salutation;
        params.firstName = firstName;
        params.lastName = lastName;
        return params;
    }

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("salutation")
    private String salutation;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("alternateEmail")
    private String alternateEmail;

    @Expose
    @SerializedName("alternatePhone")
    private String alternatePhone;
}
