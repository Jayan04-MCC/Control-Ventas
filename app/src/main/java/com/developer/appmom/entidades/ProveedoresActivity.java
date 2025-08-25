package com.developer.appmom.entidades;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.appmom.R;
import com.developer.appmom.dataBase.DatabaseHelper;

import java.util.ArrayList;

public class ProveedoresActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> listaProveedores;
    ArrayList<Integer> listaIds = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView; // si usaste ListView en vez de RecyclerView
    private Button btnAgregar;
    private EditText etNombreProveedor;
    private Button btnEliminarAll;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedores);
        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewProveedores);
        btnAgregar = findViewById(R.id.btnAgregarProveedor);
        etNombreProveedor = findViewById(R.id.etNombreProveedor);
        btnEliminarAll = findViewById(R.id.btnEliminarAll);


        listaProveedores = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProveedores);
        listView.setAdapter(adapter);

        cargarProveedores();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombreProveedor.getText().toString().trim();

                if (!nombre.isEmpty()) {
                    dbHelper.insertarProveedor(nombre);
                    setResult(RESULT_OK); // <- para avisar al activity anterior
                    finish(); // <- vuelve a ProveedoresActivity
                } else {
                    Toast.makeText(ProveedoresActivity.this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminarAll.setOnClickListener(v -> {
            dbHelper.borrarTodosProveedores();  // borra todos los registros
            cargarProveedores();                 // refresca la lista
            Toast.makeText(this, "Todos los proveedores han sido borrados", Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreProveedor = listaProveedores.get(position);
                int idProveedor = listaIds.get(position); // <-- ID correcto del proveedor

                Intent intent = new Intent(ProveedoresActivity.this, DetallesProveedorActivity.class);
                intent.putExtra("nombreProveedor", nombreProveedor);
                intent.putExtra("idProveedor", idProveedor); // ahora sí será correcto
                startActivity(intent);
            }
        });


    }

    private void cargarProveedores() {
        listaProveedores.clear();
        listaIds.clear(); // limpiar la lista de IDs también

        Cursor cursor = dbHelper.obtenerProveedores();
        if (cursor.moveToFirst()) {
            do {
                // Guardar ID y nombre del proveedor
                listaIds.add(cursor.getInt(cursor.getColumnIndexOrThrow("id"))); // reemplaza "id" por el nombre exacto de tu columna ID en la BD
                listaProveedores.add(cursor.getString(cursor.getColumnIndexOrThrow("nombre"))); // reemplaza "nombre" por la columna real
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged(); // actualizar ListView
    }


}
