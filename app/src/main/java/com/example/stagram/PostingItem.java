package com.example.stagram;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PostingItem implements Serializable{

    private String postedDate;
    private String postUser;
    private String UserDetail;
    private String ImgDetail;
    private String pathImage;


    public PostingItem(){

    }

    public PostingItem(String postedDate,String postUser,String UserDetail, String ImgDetail){
        /*this.postedDate=postedDate;
        this.postUser=postUser;
        this.UserDetail=UserDetail;
        this.ImgDetail=ImgDetail;
        this.pathImage;
        */
    }


    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getUserDetail() {
        return UserDetail;
    }

    public void setUserDetail(String userDetail) {
        UserDetail = userDetail;
    }

    public String getImgDetail() {
        return ImgDetail;
    }

    public void setImgDetail(String imgDetail) {
        ImgDetail = imgDetail;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

}
