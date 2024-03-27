package com.example.app_bandienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.InvoiceAdapter;
import models.Invoice;

public class TrangDuyetDonHang extends AppCompatActivity {
    DatabaseReference usersRef;
    List<Invoice> invoiceList;
    ListView listView;
    InvoiceAdapter invoiceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_duyet_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usersRef = FirebaseDatabase.getInstance().getReference("Invoices");
        listView=findViewById(R.id.invoices);

        invoiceList =new ArrayList<>();
        invoiceAdapter =new InvoiceAdapter(this,R.layout.item_ad_duyet,invoiceList);
        listView.setAdapter(invoiceAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(TrangDuyetDonHang.this, "dùhsuvidf", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(TrangDuyetDonHang.this, TrangXemThongTinMotKH.class);
////                intent.putExtra("id",invoiceList.get(i).getId());
////                startActivity(intent);
//            }
//        });
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    if(snapshot.child("check").getValue(String.class).equals("yes"))
//                        continue;
                        Invoice invoice=snapshot.getValue(Invoice.class);
                        invoiceList.add(invoice);
                }
                invoiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}