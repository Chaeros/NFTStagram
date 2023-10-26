package com.example.stagram;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.klaytn.caver.Caver;
import com.klaytn.caver.kct.kip17.KIP17;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import io.ipfs.api.IPFS;

public class MainActivity extends AppCompatActivity {

    ArrayList<PostingItem> ItemList = new ArrayList<PostingItem>();
    PostAdapter postAdapter = new PostAdapter(ItemList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab_post = findViewById(R.id.fbt_Post);
        RecyclerView recyclerView = findViewById(R.id.postRecyclerview);

        fab_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postIntent=new Intent(MainActivity.this,PostActivity.class);
                startActivity(postIntent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ItemList.clear();

        ObjectInputStream updateStream=null;
        String temp = "null";
        PostingItem post=new PostingItem();

        try {
            File f = new File(String.valueOf(getFilesDir()));
            File[] files = f.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().toLowerCase(Locale.US).endsWith(".txt"); //확장자
                }
            });

            for(int i =0;i<files.length;i++) {
                updateStream = new ObjectInputStream(new FileInputStream(files[i]));
                post = (PostingItem) updateStream.readObject();
                ItemList.add(post);
            }
            updateStream.close();
            Toast.makeText(MainActivity.this,"olpl",Toast.LENGTH_SHORT).show();
            postAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}