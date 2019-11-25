package com.example.usersapi.model;

import javax.persistence.*;

@Entity
@Table(name="user_profiles")
public class UserProfile {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="additional_email")
    private String additionalEmail;

    @Column
    private String address;

    @Column
    private String mobile;

    public UserProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
