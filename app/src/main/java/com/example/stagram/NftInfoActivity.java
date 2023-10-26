package com.example.stagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NftInfoActivity extends AppCompatActivity {
    String Link = "";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nft_info);
        Intent intent = getIntent();

        Blockchain b = new Blockchain();
        ImageFile imageFile = new ImageFile();
        String tokenID = intent.getExtras().getString("userDetail");

        String hexString = null;
        try {
            hexString = b.get_NFT_info(Integer.valueOf(tokenID));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Link = "https://baobab.scope.klaytn.com/nft/";
        Link += b.contract_address + "/" + tokenID; //해당 주소가 그 NFT가 있는 주소가 된다.
        Bitmap bitmap = imageFile.hexStringToBitmap(hexString);

        ImageView img = findViewById(R.id.NFT_image);
        img.setImageBitmap(bitmap);
        TextView contractTextView = findViewById(R.id.contractTextView) ;
        contractTextView.setText("컨트랙트 주소: " + b.contract_address);
        TextView tokenIdTextView = findViewById(R.id.tokenIdTextView) ;
        tokenIdTextView.setText("토큰 ID: " + tokenID);

        Button button1 = findViewById(R.id.explorerButton);
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(Link));
                startActivity(intentUrl);
            }
        });
    }
}
