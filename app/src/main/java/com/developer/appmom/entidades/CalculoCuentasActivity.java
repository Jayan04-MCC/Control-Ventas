package com.developer.appmom.entidades;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.appmom.R;
import com.developer.appmom.dataBase.DatabaseHelper;

import java.util.ArrayList;

public class CalculoCuentasActivity extends AppCompatActivity {
    private ListView listViewTotales;
    private ArrayList<String> listaTotales;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;
    private TextView tvTotalGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculo_cuentas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewTotales = findViewById(R.id.listViewTotales);
        tvTotalGeneral = findViewById(R.id.tvTotalGeneral);
        dbHelper = new DatabaseHelper(this);

        listaTotales = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTotales);
        listViewTotales.setAdapter(adapter);

        cargarTotales();
    }

    private void cargarTotales() {
        listaTotales.clear();
        Cursor cursor = dbHelper.obtenerTotalesPorProveedor();
        double totalGeneral = 0;

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
                listaTotales.add(nombre + " → $" + total);
                totalGeneral += total;
            } while (cursor.moveToNext());
        }
        cursor.close();

        tvTotalGeneral.setText("TOTAL GENERAL: $" + totalGeneral);
        adapter.notifyDataSetChanged();
    }
}

