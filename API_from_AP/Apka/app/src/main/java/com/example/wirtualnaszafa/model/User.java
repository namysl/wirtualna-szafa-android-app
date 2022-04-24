package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("email_verified_at")
    String emailVerifiedAt;
    @SerializedName("two_factor_confirmed_at")
    String twoFactorConfirmedAt;
    @SerializedName("current_team_id")
    String currentTeamId;
    @SerializedName("profile_photo_path")
    String profilePhotoPath;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("profile_photo_url")
    String profilePhotoUrl;
}
