package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

public class SuiteTag {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("token")
    String token;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("pivot")
    Pivot pivot;
}

