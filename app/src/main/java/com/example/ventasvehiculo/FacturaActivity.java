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
import android.widget.TextView;
import android.widget.Toast;

public class FacturaActivity extends AppCompatActivity {

    EditText etCodigoF, etFecha, etPlaca;
    CheckBox cbActivoF;
    TextView tvMarca, tvModelo, tvValor;
    ClsOpenHelper admin = new ClsOpenHelper(this, "ConcesionarioDB",null, 1);
    String placa, codigo, fecha;
    int sw;
    long resp;
    int  consCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        getSupportActionBar().hide();
        etPlaca = findViewById(R.id.etPlacaF);
        etFecha = findViewById(R.id.etFecha);
        etCodigoF = findViewById(R.id.etCodigo);
        cbActivoF = findViewById(R.id.cbActivoF);
        tvMarca = findViewById(R.id.tvMarca);
        tvModelo = findViewById(R.id.tvModelo);
        tvValor = findViewById(R.id.tvValor);
        sw=0;
    }

    private void limpiarCampos() {
        etPlaca.setText("");
        etFecha.setText("");
        etCodigoF.setText("");
        cbActivoF.setChecked(false);
        etCodigoF.requestFocus();
        tvValor.setText("");
        tvModelo.setText("");
        tvMarca.setText("");
        sw=0;
        consCodigo=0;
    }

    public void main(View view) {
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }

    public void guardar(View view) {
        placa = etPlaca.getText().toString();
        codigo = etCodigoF.getText().toString();
        fecha = etFecha.getText().toString();

        if( codigo.isEmpty()
                || fecha.isEmpty()) {

            Toast.makeText(this, "Todos los campos son requerido", Toast.LENGTH_SHORT).show();
            etCodigoF.requestFocus();
        } else {
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            ContentValues registroVehiculo = new ContentValues();
            if (sw==1) {
                registro.put("codigo_factura", codigo);
                registro.put("fecha", fecha);
                registro.put("placa", placa);

                resp = db.insert("tbl_factura", null, registro);

            } else {
                Toast.makeText(this, "No existe un vehiculo con esa placa", Toast.LENGTH_SHORT).show();
                sw=0;
            }

            if (resp > 0) {
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                registroVehiculo.put("activo", "no");
                db.update("tbl_vehiculo", registroVehiculo, "placa='" + placa + "'", null);
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se pudo guardar la informacion", Toast.LENGTH_SHORT).show();
            }

            db.close();

        }

    }

    public void consultar(View view) {

        codigo = etCodigoF.getText().toString();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "El codigo de la factura es requerida", Toast.LENGTH_SHORT).show();
            etCodigoF.requestFocus();
        } {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery(
                    "SELECT * FROM tbl_factura WHERE codigo_factura = '" + codigo + "'", null
            );

            if (fila.moveToNext()) {
                consCodigo = 1;
                etPlaca.setText(fila.getString(2));
                etCodigoF.setText(fila.getString(0));
                etFecha.setText(fila.getString(1));
                if (fila.getString(3).equals("si")) {
                    cbActivoF.setChecked(true);
                } else {
                    cbActivoF.setChecked(false);
                    cbActivoF.setClickable(false);
                }

            } else {
                limpiarCampos();
                Toast.makeText(this, "Factura no registrado", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void buscarPlaca(View view) {

        placa = etPlaca.getText().toString();
        if (placa.isEmpty()) {
            Toast.makeText(this, "La placa es requerida", Toast.LENGTH_SHORT).show();
            etPlaca.requestFocus();
        } else
        {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery(
                    "SELECT * FROM tbl_vehiculo WHERE placa = '" + placa + "' AND activo = 'si'", null
            );

            if (fila.moveToNext()) {
                sw = 1;
                tvValor.setText("Valor: " + fila.getString(3));
                tvModelo.setText("Modelo: " + fila.getString(2));
                tvMarca.setText("Marca: " + fila.getString(1));

            } else {
                Toast.makeText(this, "Vehiculo no registrado", Toast.LENGTH_SHORT).show();
                tvMarca.setText("");
                tvModelo.setText("");
                tvValor.setText("");
            }
        }
    }

    public void anular(View view) {
        if(consCodigo==0) {
            Toast.makeText(this, "Debe consultar el codigo", Toast.LENGTH_SHORT).show();
            etCodigoF.requestFocus();
        } else {
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("activo", "no");
            long resp =  db.update("tbl_factura", registro, "codigo_factura='" + codigo +"'", null);
            if (resp > 0) {
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                consCodigo=0;
            } else {
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void cancelar(View view) {
        limpiarCampos();
    }

}