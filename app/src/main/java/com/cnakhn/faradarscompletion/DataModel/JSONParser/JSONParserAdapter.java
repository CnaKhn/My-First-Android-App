package com.cnakhn.faradarscompletion.DataModel.JSONParser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnakhn.faradarscompletion.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JSONParserAdapter extends RecyclerView.Adapter<JSONParserAdapter.JSONParserViewHolder> {
    private Context context;
    private ArrayList<JSONParserItem> jsonList;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public JSONParserAdapter(Context context, ArrayList<JSONParserItem> jsonList) {
        this.context = context;
        this.jsonList = jsonList;
    }

    @Override
    public JSONParserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_json_parser_item, parent, false);
        return new JSONParserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JSONParserViewHolder holder, int position) {
        JSONParserItem currentItem = jsonList.get(position);
        String imageUrl = currentItem.getJsonImageUrl();
        String title = currentItem.getJsonTitle();
        int likes = currentItem.getJsonLikes();

        holder.txtTitle.setText(title);
        holder.txtLikes.setText("Likes: " + likes);
        Picasso.with(context).load(imageUrl).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return jsonList.size();
    }

    public class JSONParserViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtTitle, txtLikes;

        public JSONParserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.json_image);
            txtTitle = itemView.findViewById(R.id.json_title);
            txtLikes = itemView.findViewById(R.id.json_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position  = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}
