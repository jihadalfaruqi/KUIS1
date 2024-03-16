package com.example.kuis1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText customerNameEditText, itemCodeEditText, itemQuantityEditText;
    RadioGroup customerTypeRadioGroup;
    Button processButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerNameEditText = findViewById(R.id.customerNameEditText);
        itemCodeEditText = findViewById(R.id.itemCodeEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        customerTypeRadioGroup = findViewById(R.id.customerTypeRadioGroup);
        processButton = findViewById(R.id.processButton);

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOrder();
            }
        });

        // Menambahkan listener untuk radio button
        customerTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                Toast.makeText(MainActivity.this, "Anda memilih: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processOrder() {
        String customerName = customerNameEditText.getText().toString();
        String itemCode = itemCodeEditText.getText().toString();
        int itemQuantity = Integer.parseInt(itemQuantityEditText.getText().toString());
        String customerType = getCustomerType();

        // Hitung total harga berdasarkan tipe pelanggan dan jumlah barang
        double totalPrice = calculateTotalPrice(itemCode, itemQuantity, customerType);

        // Buat Intent untuk beralih ke aktivitas TransaksiDetailActivity
        Intent intent = new Intent(MainActivity.this, TransaksiDetailActivity.class);
        // Sisipkan data transaksi ke Intent
        intent.putExtra("customerName", customerName);
        intent.putExtra("itemCode", itemCode);
        intent.putExtra("itemQuantity", itemQuantity);
        intent.putExtra("customerType", customerType);
        intent.putExtra("totalPrice", totalPrice);

        // Mulai aktivitas TransaksiDetailActivity
        startActivity(intent);
    }

    private String getCustomerType() {
        int selectedRadioId = customerTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioId);
        return selectedRadioButton.getText().toString();
    }

    private double calculateTotalPrice(String itemCode, int itemQuantity, String customerType) {
        // Implementasikan logika untuk menghitung total harga berdasarkan tipe pelanggan
        // Anda dapat menyesuaikan logika ini sesuai dengan kebutuhan Anda
        // Misalnya, menggunakan diskon berdasarkan tipe pelanggan dan total harga barang
        double pricePerItem = getPricePerItem(itemCode);
        double totalPrice = pricePerItem * itemQuantity;

        // Diskon tambahan untuk transaksi di atas 10 juta
        if (totalPrice > 10000000) {
            totalPrice -= 100000; // Diskon tambahan sebesar 100.000
        }

        // Menerapkan diskon berdasarkan tipe pelanggan
        switch (customerType) {
            case "Gold":
                totalPrice *= 0.90; // Diskon 10% untuk member Gold
                break;
            case "Silver":
                totalPrice *= 0.95; // Diskon 5% untuk member Silver
                break;
            case "Biasa":
                totalPrice *= 0.98; // Diskon 2% untuk member Biasa
                break;
        }

        return totalPrice;
    }

    private double getPricePerItem(String itemCode) {
        // Implementasikan logika untuk mendapatkan harga per barang berdasarkan kode barang
        // Anda dapat menyesuaikan logika ini sesuai dengan kebutuhan Anda
        if (itemCode.equals("SGS")) {
            return 12999999;
        } else if (itemCode.equals("IPX")) {
            return 5725300;
        } else if (itemCode.equals("PCO")) {
            return 2730551;
        } else {
            return 0; // Jika kode barang tidak valid, kembalikan 0
        }
    }
}
