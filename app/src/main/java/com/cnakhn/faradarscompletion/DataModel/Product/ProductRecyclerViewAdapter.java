package com.cnakhn.faradarscompletion.DataModel.Product;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnakhn.faradarscompletion.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<FakeDataGenerator> products;
    private Context context;

    public ProductRecyclerViewAdapter(List<FakeDataGenerator> products, Context context) {
        this.products = (products == null) ? new ArrayList<FakeDataGenerator>() : products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_products, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String imageLink = "https://picsum.photos/800/800";
        if (holder instanceof ItemViewHolder) {
            FakeDataGenerator item = products.get(position);
            ((ItemViewHolder) holder).tvName.setText(item.getName());
            ((ItemViewHolder) holder).tvCategory.setText(item.getCategory());
            ((ItemViewHolder) holder).tvInstructions.setText(item.getInstructions());
            ((ItemViewHolder) holder).tvPrice.setText(item.getPrice());
            Picasso.with(context).load(imageLink).into(((ItemViewHolder) holder).image);
        }
        if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvName, tvCategory, tvInstructions, tvPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            tvName = itemView.findViewById(R.id.product_name);
            tvCategory = itemView.findViewById(R.id.product_category);
            tvInstructions = itemView.findViewById(R.id.product_instructions);
            tvPrice = itemView.findViewById(R.id.product_price);
        }


    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.products_progress_bar);
        }
    }

    private void showLoadingView(LoadingViewHolder loadingViewHolder, int position) {
        loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
    }
}
