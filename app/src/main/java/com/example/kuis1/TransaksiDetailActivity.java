package com.example.kuis1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransaksiDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_detail);

        // Mendapatkan referensi tombol "Share" dari layout
        Button shareButton = findViewById(R.id.shareButton);

        // Menambahkan listener untuk tombol "Share"
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk membagikan detail transaksi
                shareTransaction();
            }
        });

        // Mendapatkan data transaksi dari intent
        Intent intent = getIntent();
        if (intent != null) {
            String customerName = intent.getStringExtra("customerName");
            String itemCode = intent.getStringExtra("itemCode");
            int itemQuantity = intent.getIntExtra("itemQuantity", 0);
            String customerType = intent.getStringExtra("customerType");
            double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

            // Menampilkan data transaksi di TextView
            TextView customerNameTextView = findViewById(R.id.customerNameTextView);
            customerNameTextView.setText("Nama Pelanggan: " + customerName);

            TextView itemCodeTextView = findViewById(R.id.itemCodeTextView);
            itemCodeTextView.setText("Kode Barang: " + itemCode);

            TextView itemQuantityTextView = findViewById(R.id.itemQuantityTextView);
            itemQuantityTextView.setText("Jumlah Barang: " + itemQuantity);

            TextView customerTypeTextView = findViewById(R.id.customerTypeTextView);
            customerTypeTextView.setText("Tipe Pelanggan: " + customerType);

            TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
            totalPriceTextView.setText("Total Harga: Rp " + String.format("%.2f", totalPrice));
        } else {
            Toast.makeText(this, "Data transaksi tidak tersedia.", Toast.LENGTH_SHORT).show();
            finish(); // Selesaikan aktivitas jika data transaksi tidak tersedia
        }
    }

    // Method untuk membagikan detail transaksi
    private void shareTransaction() {
        // Mendapatkan data transaksi dari intent
        Intent intent = getIntent();
        String customerName = intent.getStringExtra("customerName");
        String itemCode = intent.getStringExtra("itemCode");
        int itemQuantity = intent.getIntExtra("itemQuantity", 0);
        String customerType = intent.getStringExtra("customerType");
        double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

        // Format pesan untuk dibagikan
        String message = "Detail Transaksi\n"
                + "Nama Pelanggan: " + customerName + "\n"
                + "Kode Barang: " + itemCode + "\n"
                + "Jumlah Barang: " + itemQuantity + "\n"
                + "Tipe Pelanggan: " + customerType + "\n"
                + "Total Harga: Rp " + String.format("%.2f", totalPrice);

        // Buat intent untuk membagikan pesan
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Jalankan intent untuk membagikan pesan
        startActivity(Intent.createChooser(shareIntent, "Bagikan Detail Transaksi"));
    }
}
