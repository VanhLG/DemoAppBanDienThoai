package com.example.app_bandienthoai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import models.Product;
import models.User;
import reference.Reference;

public class TrangCTSP extends AppCompatActivity {
    private final Reference reference = new Reference();

    private final DatabaseReference products_ref = reference.getProducts();

    private final DatabaseReference user_ref = reference.getUsers();

    private ImageView image_view_product_image;

    TextView text_view_product_name, text_view_price, text_view_price_sale, text_view_product_description;

    private Button button_add_to_cart;

    private ImageButton home_button;

    private String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_ctsp);

        mapping_client();

        getSupportActionBar().hide();

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangCTSP.this, TrangChu.class);
                startActivity(intent);
            }
        });

        this.product_id = getIntent().getStringExtra("product_id");

        this.button_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddToCart();
            }
        });

        products_ref.child(product_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data_snapshot) {
                Product product = data_snapshot.getValue(Product.class);

                text_view_product_name.setText(product.getName());
                text_view_price.setText(Float.toString(product.getPrice()));
                text_view_price_sale.setText(Float.toString(product.getPrice() - product.getPrice_sale()));
                text_view_product_description.setText(product.getDescription());
                Glide.with(TrangCTSP.this).load(product.getImage()).into(image_view_product_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void mapping_client() {
        image_view_product_image = findViewById(R.id.image_view_product_image);
        text_view_product_name = findViewById(R.id.text_view_product_name);
        text_view_price = findViewById(R.id.text_view_price);
        text_view_price_sale = findViewById(R.id.text_view_price_sale);
        text_view_product_description = findViewById(R.id.text_view_product_description);
        button_add_to_cart = findViewById(R.id.button_add_to_cart);
        home_button = findViewById(R.id.button1);
    }

    private void handleAddToCart() {
        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences",
                Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        user_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                assert user != null;

                user.add_to_cart(product_id);

                user_ref.child(user_id).child("cart").setValue(user.getCart());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast toast = Toast.makeText(this, user_id, Toast.LENGTH_LONG);

        toast.show();

    }

}