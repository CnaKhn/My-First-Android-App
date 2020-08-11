package com.cnakhn.faradarscompletion.DataModel.Product;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnakhn.faradarscompletion.R;

import java.util.List;

public class ProductListViewAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;
    private ProductDBHelper dbHelper;

    public ProductListViewAdapter(Context context, List<Product> productList) {
        super(context, R.layout.layout_products, productList);
        this.context = context;
        this.productList = productList;
        this.dbHelper = new ProductDBHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_products, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fill(position);

        return convertView;
    }

    public class ViewHolder {
        ImageView productImage, btnProductMenu;
        TextView productName, productCategory, productInstructions, productPrice;

        public ViewHolder(View view) {
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productCategory = view.findViewById(R.id.product_category);
            productInstructions = view.findViewById(R.id.product_instructions);
            productPrice = view.findViewById(R.id.product_price);
            btnProductMenu = view.findViewById(R.id.btn_product_menu);
        }

        public void fill(final int position) {
            final Product product = productList.get(position);
            productName.setText(product.getName());
            productCategory.setText(product.getCategory());
            productInstructions.setText(product.getInstructions());
            productPrice.setText(String.format("%s$", product.getPrice()));

            //Load Image
            String photo = product.getPhoto();
            if (photo.contains(".")) photo = photo.substring(0, photo.lastIndexOf('.'));
            int photoResId = context.getResources().getIdentifier(photo, "drawable", context.getApplicationContext().getPackageName());
            productImage.setImageResource(photoResId);

            //Menu Button
            final PopupMenu menu = new PopupMenu(btnProductMenu.getContext(), btnProductMenu);
            menu.inflate(R.menu.menu_product);
            btnProductMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu.show();
                }
            });

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_btn_edit) {
                        showEditProductDialog(product);
                    }

                    if (id == R.id.menu_btn_remove) {
                        dbHelper.deleteProduct(product.getId());
                        productList.remove(position);
                        notifyDataSetChanged();
                    }
                    return false;
                }
            });
        }
    }

    private void showEditProductDialog(Product product) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_edit_product);
        dialog.show();
    }
}
