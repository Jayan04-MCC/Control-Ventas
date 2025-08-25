package com.developer.appmom.entidades;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.appmom.R;
import com.developer.appmom.dataBase.DatabaseHelper;

import java.util.ArrayList;

public class ComprasActivity extends AppCompatActivity {

    private ListView listViewCompras;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compras);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewCompras = findViewById(R.id.listViewCompras);
        dbHelper = new DatabaseHelper(this);

        int proveedorId = getIntent().getIntExtra("idProveedor", -1);

        listaCompras = dbHelper.obtenerComprasPorProveedor(proveedorId);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCompras);
        listViewCompras.setAdapter(adapter);
    }
}