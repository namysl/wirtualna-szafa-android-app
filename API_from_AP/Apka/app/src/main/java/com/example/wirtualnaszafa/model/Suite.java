package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Suite {
    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    String userId;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("target_image_path")
    String targetImagePath;
    @SerializedName("token")
    String token;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("tags")
    List<SuiteTag> tags;
}

