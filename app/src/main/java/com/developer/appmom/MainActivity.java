package com.developer.appmom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.appmom.entidades.CalculoCuentasActivity;
import com.developer.appmom.entidades.ProveedoresActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnProveedores;

    private Button btnCalculoCuentas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnCalculoCuentas = findViewById(R.id.btnCalculoCuentas);
        btnProveedores = findViewById(R.id.button);

        InitzializerApp();

    }

//    public void pressButton(){
//        textView = findViewById(R.id.textview);
//        button = findViewById(R.id.button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("hola nuevo mundo!!!!");
//            }
//        });
//    }
    public void InitzializerApp(){

        btnProveedores.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProveedoresActivity.class);
            startActivity(intent);
        });

        btnCalculoCuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculoCuentasActivity.class);
                startActivity(intent);
            }
        });
    }

}