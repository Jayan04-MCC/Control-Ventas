package com.developer.appmom.entidades;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.appmom.R;
import com.developer.appmom.dataBase.DatabaseHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class DetallesProveedorActivity extends AppCompatActivity {

    private TextView tvNombreProveedor;
    private EditText etProducto, etCantidad, etPrecio;
    private Button btnRegistrarCompra;
    private DatabaseHelper dbHelper;
    private String nombreProveedor;

    private Button btnMostrarCompras;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_proveedor);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        // Recibir el proveedor del intent
        nombreProveedor = getIntent().getStringExtra("nombreProveedor");

        // Referencias UI
        tvNombreProveedor = findViewById(R.id.tvNombreProveedor);
        etProducto = findViewById(R.id.etProducto);
        etCantidad = findViewById(R.id.etCantidad);
        etPrecio = findViewById(R.id.etPrecio);
        btnRegistrarCompra = findViewById(R.id.btnRegistrarCompra);
        btnMostrarCompras = findViewById(R.id.btnMostrarCompras);

        // Mostrar proveedor
        tvNombreProveedor.setText("Proveedor: " + nombreProveedor);

        // Acción de guardar compra
        btnRegistrarCompra.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String producto = etProducto.getText().toString();
                String cantidad = etCantidad.getText().toString();
                String precio = etPrecio.getText().toString();
                int proveedorId = getIntent().getIntExtra("idProveedor", -1);
                LocalDateTime fecha = LocalDateTime.now();



                if (!producto.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty()) {
                    dbHelper.insertarCompra(proveedorId,
                            producto, Integer.parseInt(cantidad),
                            Integer.parseInt(precio),
                            fecha.toString());
                    Toast.makeText(DetallesProveedorActivity.this, "Compra registrada", Toast.LENGTH_SHORT).show();
                    finish(); // Vuelve atrás
                } else {
                    Toast.makeText(DetallesProveedorActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMostrarCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int proveedorId = getIntent().getIntExtra("idProveedor", -1);
                Intent intent = new Intent(DetallesProveedorActivity.this, ComprasActivity.class);
                intent.putExtra("idProveedor", proveedorId);
                startActivity(intent);
            }
        });



    }
}