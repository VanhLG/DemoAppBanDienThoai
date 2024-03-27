package com.example.app_bandienthoai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

import java.util.Date;

public class TrangDuyenMotDonHang extends AppCompatActivity {
    DatabaseReference usersRef;
    TextView txtHoTen , txtSDT,txtNgayDat , txtDiaChi;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_duyen_mot_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i=getIntent();
        String id = i.getStringExtra("id");

        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String id_user = snapshot.child("user_id").getValue(String.class);
                    txtHoTen =findViewById(R.id.textViewSuaTen);
                    txtDiaChi=findViewById(R.id.textViewDiaChiMĐ);
                    txtSDT=findViewById(R.id.textViewSDT);
                    txtNgayDat = findViewById(R.id.textViewNgayĐH);

                    txtHoTen.setText(snapshot.child("id").getValue(String.class));
                    DataSnapshot date = snapshot.child("create_at");
                    txtNgayDat.setText(String.valueOf(date.child("year").getValue(Long.class))+"/"+String.valueOf(date.child("date").getValue(Long.class)));
                    txtDiaChi.setText(snapshot.child("address").getValue(String.class));
                    DatabaseReference usersRef2=FirebaseDatabase.getInstance().getReference("Users").child(id_user);
                    usersRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            txtHoTen.setText(snapshot.child("name").getValue(String.class));
                            txtSDT.setText(snapshot.child("phone").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}