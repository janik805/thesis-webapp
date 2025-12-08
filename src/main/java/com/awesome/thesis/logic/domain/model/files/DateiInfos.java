package com.awesome.thesis.logic.domain.model.files;

import com.awesome.thesis.annotations.AggregateRoot;

import java.time.LocalDateTime;

@AggregateRoot
public class DateiInfos {
    private  String uploader = "bob";
    private  LocalDateTime uploadTime = LocalDateTime.now();
    private  String title;
    private String description = "";

    public DateiInfos(String title, String description) {
        this.title = title;
        this.description = description;
        this.uploadTime = LocalDateTime.now();
    }


    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
