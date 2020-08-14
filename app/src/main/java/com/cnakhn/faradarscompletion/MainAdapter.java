package com.cnakhn.faradarscompletion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnakhn.faradarscompletion.Activities.ChartsActivity;
import com.cnakhn.faradarscompletion.Activities.DownloadManagerActivity;
import com.cnakhn.faradarscompletion.Activities.MapsActivity;
import com.cnakhn.faradarscompletion.Activities.MusicPlayerActivity;
import com.cnakhn.faradarscompletion.Activities.TicTacToeActivity;
import com.cnakhn.faradarscompletion.Activities.VideoPlayerActivity;
import com.cnakhn.faradarscompletion.ArchitectureExample.NotesActivity;
import com.cnakhn.faradarscompletion.Weather.WeatherActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;

import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DEFAULT_ITEM = 1;
    private static Context context;
    static Typeface typeface;
    private SimpleDraweeView draweeView;

    public MainAdapter(Context context) {
        MainAdapter.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        Fresco.initialize(context,
                ImagePipelineConfig.newBuilder(context).setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                        .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER).experiment().setNativeCodeDisabled(true).build());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                View view = LayoutInflater.from(context).inflate(R.layout.layout_main_banner, parent, false);
                return new ViewHolderBanner(view);

            case VIEW_TYPE_DEFAULT_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.layout_main_items, parent, false);
                return new ViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_DEFAULT_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Uri uri;
            switch (position) {
                case 1:
                    uri = Uri.parse("https://images.squarespace-cdn.com/content/59f371f1f43b55c37cb59a6d/1565621730021-BD6J26KRC1I1M3EUVPCU/minutes-of-a-meeting-300x300.jpg?content-type=image%2Fjpeg");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Take a note");
                    break;
                case 2:
                    uri = Uri.parse("https://www.ft.com/__origami/service/image/v2/images/raw/ftcms%3A347ece48-0f69-11e9-a3aa-118c761d2745?source=ig");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Charts");
                    break;
                case 3:
                    uri = Uri.parse("https://assets.t3n.sc/news/wp-content/uploads/2020/02/google-maps-icon-2020-hero.jpg?auto=format&fit=crop&h=348&ixlib=php-2.3.0&w=620");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Maps");
                    break;
                case 4:
                    uri = Uri.parse("https://i.pinimg.com/originals/52/ab/8c/52ab8ce239e052e34ba24d4d72a20947.png");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Tic Tac Toe Game");
                    break;
                case 5:
                    uri = Uri.parse("https://previews.123rf.com/images/mattbadal/mattbadal1903/mattbadal190300145/124798548-download-icon-with-line-style-vector-illustration-download-manager-software.jpg");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Download Manager");
                    break;
                case 6:
                    uri = Uri.parse("https://cdn.dribbble.com/users/915711/screenshots/5831407/music_player2.png");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Music Player");
                    break;
                case 7:
                    uri = Uri.parse("https://www.pngkit.com/png/detail/667-6675807_youtube-video-player-icon.png");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Video Player");
                    break;
                case 8:
                    uri = Uri.parse("https://media1.thehungryjpeg.com/thumbs2/ori_57582_f28701e8955ef6e1759038eea7ea4c55b03fa7c7_glyph-icon-weather-icons-vol-2.png");
                    viewHolder.itemImage.setImageURI(uri);
                    viewHolder.txtTitle.setText("Weather");
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 1:
                            context.startActivity(new Intent(context, NotesActivity.class));
                            break;
                        case 2:
                            context.startActivity(new Intent(context, ChartsActivity.class));
                            break;
                        case 3:
                            context.startActivity(new Intent(context, MapsActivity.class));
                            break;
                        case 4:
                            context.startActivity(new Intent(context, TicTacToeActivity.class));
                            break;
                        case 5:
                            context.startActivity(new Intent(context, DownloadManagerActivity.class));
                            break;
                        case 6:
                            context.startActivity(new Intent(context, MusicPlayerActivity.class));
                            break;
                        case 7:
                            context.startActivity(new Intent(context, VideoPlayerActivity.class));
                            break;
                        case 8:
                            context.startActivity(new Intent(context, WeatherActivity.class));
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView itemImage;
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            txtTitle = itemView.findViewById(R.id.item_title);
        }
    }

    static class ViewHolderBanner extends RecyclerView.ViewHolder {
        private TextView bannerTitle;
        private BannerSlider bannerSlider;

        public ViewHolderBanner(@NonNull View itemView) {
            super(itemView);
            bannerTitle = itemView.findViewById(R.id.banner_title);
            bannerSlider = itemView.findViewById(R.id.banner_slider);

            bannerSlider.addBanner(new DrawableBanner(R.drawable.map_banner).setScaleType(ImageView.ScaleType.CENTER_CROP));
            bannerSlider.addBanner(new DrawableBanner(R.drawable.music_banner).setScaleType(ImageView.ScaleType.CENTER_CROP));
            bannerSlider.addBanner(new DrawableBanner(R.drawable.video_banner).setScaleType(ImageView.ScaleType.CENTER_CROP));
            bannerSlider.addBanner(new DrawableBanner(R.drawable.weather_banner).setScaleType(ImageView.ScaleType.CENTER_CROP));
        }
    }
}
