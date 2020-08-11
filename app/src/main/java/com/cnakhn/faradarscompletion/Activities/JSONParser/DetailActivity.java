package com.cnakhn.faradarscompletion.Activities.JSONParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.squareup.picasso.Picasso;

import static com.cnakhn.faradarscompletion.Activities.JSONParser.JSONParserActivity.EXTRA_URL;
import static com.cnakhn.faradarscompletion.Activities.JSONParser.JSONParserActivity.EXTRA_TITLE;
import static com.cnakhn.faradarscompletion.Activities.JSONParser.JSONParserActivity.EXTRA_LIKES;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parser_detail);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorBlack));

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        int likes = intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageView = findViewById(R.id.detail_image_view);
        TextView txtTitle = findViewById(R.id.detail_txt_title);
        TextView txtLikes = findViewById(R.id.detail_txt_likes);

        Picasso.with(this).load(imageUrl).into(imageView);
        txtTitle.setText(title);
        txtLikes.setText("Likes: " + likes);
    }
}