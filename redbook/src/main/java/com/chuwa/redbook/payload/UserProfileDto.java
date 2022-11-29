package com.chuwa.redbook.payload;

public class UserProfileDto {
    private String name;
    private String email;
    private String bio;
    private String gender;

    public UserProfileDto(String name, String email, String bio, String gender) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
