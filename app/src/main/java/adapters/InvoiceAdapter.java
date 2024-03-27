package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_bandienthoai.R;
import com.example.app_bandienthoai.TrangDuyenMotDonHang;

import java.util.List;

import models.Invoice;

public class InvoiceAdapter extends ArrayAdapter<Invoice> {

    private Context mContext;
    private int mResource;
    public InvoiceAdapter(@NonNull Context context, int resource, @NonNull List<Invoice> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);

        }

        TextView textViewName = itemView.findViewById(R.id.invoice_description);


        Invoice  invoice = getItem(position);

        if (invoice != null) {
            textViewName.setText(String.valueOf( invoice.getTotal()));
            // textViewName.setText("user.getName()");
        }
        CheckBox checkBox = itemView.findViewById(R.id.check);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "dsgdgd", Toast.LENGTH_SHORT).show();
            }
        });
       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Toast.makeText(mContext, "hfsgv", Toast.LENGTH_SHORT).show();
               Intent i =new Intent(mContext, TrangDuyenMotDonHang.class);
               i.putExtra("id",invoice.getId());
               if (mContext != null) {
                   mContext.startActivity(i);
               } else {
                   Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
               }

           }
       });
        return itemView;
    }
}
