package com.example.stagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.ipfs.api.IPFS;

public class PostActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE =200;
    private ImageView Img_PostActivity;
    //PostingItem ItemList[];

    @Override
    protected void onCreate(Bundle savedInstances) {
        Blockchain b = new Blockchain();
        super.onCreate(savedInstances);
        setContentView(R.layout.activitiy_post);

        EditText detail_PostActivity = findViewById(R.id.detail_PostActivity);
        Button bt_PostActivity = findViewById(R.id.bt_PostActivity);

        Img_PostActivity = findViewById(R.id.img_PostActivity);
        Img_PostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryLoadIntent = new Intent(Intent.ACTION_PICK);
                galleryLoadIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(galleryLoadIntent,GET_GALLERY_IMAGE);
            }
        });
        bt_PostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable getImage = Img_PostActivity.getDrawable();
                BitmapDrawable drawable = (BitmapDrawable) getImage;
                long CurrTime = System.currentTimeMillis();

                String path = null;
                if(getImage==null)
                    Toast.makeText(PostActivity.this,"이미지를 추가해주세요!",Toast.LENGTH_SHORT).show();
                else {
                    ObjectOutputStream postStream = null;
                    FileOutputStream postImg = null;
                    try {
                        postStream = new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/"+Long.toString(CurrTime)+".txt"));
                        path = getFilesDir()+"/"+Long.toString(CurrTime)+".png";
                        postImg = new FileOutputStream (new File(path));
                        drawable.getBitmap().compress(Bitmap.CompressFormat.PNG,100,postImg);
                        postImg.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    PostingItem post = new PostingItem();
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date mDate = new Date(CurrTime);
                    mFormat.format(mDate);

                    ImageFile imagefile = new ImageFile(); // 이미지 변환과 관련된 클래스
                    Blockchain b = new Blockchain(); //블록체인 클래스 불러오기
                    Bitmap bitmap = imagefile.getResizeBitmap(path);
                    String img = imagefile.bitmapToByteString(bitmap);
                    int tokenNum = 0;
                    try {
                        tokenNum = b.mint_NFT(img,b.private_key,b.address);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post.setPostUser(b.address);
                    post.setImgDetail(detail_PostActivity.getText().toString());
                    post.setUserDetail(String.valueOf(tokenNum));
                    post.setPostedDate(mFormat.format(mDate));
                    post.setPathImage(getFilesDir()+"/"+Long.toString(CurrTime)+".png");
                    try {
                        postStream.writeObject(post);
                        postStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(PostActivity.this,"게시 완료!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Img_PostActivity.setImageURI(selectedImageUri);
        }
    }
}
