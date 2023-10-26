package com.example.stagram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<PostingItem> dataList ;
    private Context adapterContext;

    PostAdapter(ArrayList<PostingItem> data){
        this.dataList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adapterContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Uri uri = Uri.parse(dataList.get(position).getPathImage());
            holder.postImage.setImageURI(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.userName.setText(dataList.get(position).getPostUser());
        holder.userDetail.setText(dataList.get(position).getUserDetail());
        holder.postDetail.setText(dataList.get(position).getImgDetail());
        holder.postedTime.setText(dataList.get(position).getPostedDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView postImage;
    TextView userName ;
    TextView userDetail ;
    TextView postDetail ;
    TextView postedTime ;

    public ViewHolder(View itemView) {
        super(itemView) ;
        // 뷰 객체에 대한 참조. (hold strong reference)
        userName = itemView.findViewById(R.id.namePoster) ;
        userDetail = itemView.findViewById(R.id.userDetail) ;
        postDetail = itemView.findViewById(R.id.detailPost) ;
        postedTime = itemView.findViewById(R.id.timePost) ;
        postImage = itemView.findViewById(R.id.img_posted) ;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition(); //포지션 가져와서 아이템있으면 인텐트 활용해서 userDetail의 텍스트 전송
                if(pos!=RecyclerView.NO_POSITION){
                    Intent emptyIntent = new Intent(view.getContext().getApplicationContext(), NftInfoActivity.class);
                    emptyIntent.putExtra("userDetail",userDetail.getText().toString());
                    Toast.makeText(view.getContext(),userDetail.getText().toString(),Toast.LENGTH_SHORT).show();
                    view.getContext().startActivity(emptyIntent);
                }
            }
        });

    }
}
