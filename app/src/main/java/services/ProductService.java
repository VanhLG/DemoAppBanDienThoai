package services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Product;
import reference.Reference;

public class ProductService {
    private final Reference reference = new Reference();

    private final DatabaseReference products_ref = this.reference.getProducts();

    public ProductService() {

    }

    public void createProduct() {

    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        products_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    products.add(product);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return products;
    }

public Product getProductById(String id) {
    final Product[] product = {new Product()};

        products_ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product[0] = dataSnapshot.getValue(Product.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return product[0];
    }
}
