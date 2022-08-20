package com.example.ventasvehiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vehiculo(View view) {
        Intent intvehiculo = new Intent(this, VehiculoActivity.class);
        startActivity(intvehiculo);
    }

    public void factura(View view) {
        Intent intfactura = new Intent(this, FacturaActivity.class);
        startActivity(intfactura);
    }
}