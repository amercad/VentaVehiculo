package com.example.ventasvehiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VehiculoActivity extends AppCompatActivity {

    EditText etPlaca, etMarca, etModelo, etValor;
    CheckBox cbActivo;
    ClsOpenHelper admin = new ClsOpenHelper(this, "ConcesionarioDB",null, 1);
    String placa, marca, modelo, valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        getSupportActionBar().hide();
        etPlaca = findViewById(R.id.etPlaca);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etValor = findViewById(R.id.etValor);
        cbActivo = findViewById(R.id.cbActivo);
    }

    public void guardar(View view) {


        placa = etPlaca.getText().toString();
        marca = etMarca.getText().toString();
        modelo = etModelo.getText().toString();
        valor = etValor.getText().toString();
        Long resp;
        if( placa.isEmpty() || marca.isEmpty()
            || modelo.isEmpty() || valor.isEmpty()) {

            Toast.makeText(this, "Todos los campos son requerido", Toast.LENGTH_SHORT).show();
            etPlaca.requestFocus();
        } else {

            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("placa", placa);
            registro.put("marca", marca);
            registro.put("modelo", modelo);
            registro.put("valor", Integer.parseInt(valor));

            resp = db.insert("tbl_vehiculo", null, registro);

            if (resp > 0) {
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se pudo guardar la informacion", Toast.LENGTH_SHORT).show();
            }

            db.close();

        }

    }

    public void consultar(View view) {

        placa = etPlaca.getText().toString();
        if (placa.isEmpty()) {
            Toast.makeText(this, "La placa es requerida", Toast.LENGTH_SHORT).show();
            etPlaca.requestFocus();
        } {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery(
            "SELECT * FROM tbl_vehiculo WHERE placa = '" + placa + "'", null
            );

            if (fila.moveToNext()) {

            } else {
                Toast.makeText(this, "Vehiculo no registrado", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void limpiarCampos() {
        etPlaca.setText("");
        etMarca.setText("");
        etModelo.setText("");
        etValor.setText("");
        cbActivo.setChecked(false);
        etPlaca.requestFocus();
    }

    public void main(View view) {
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }

}