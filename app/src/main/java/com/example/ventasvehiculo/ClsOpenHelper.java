package com.example.ventasvehiculo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClsOpenHelper  extends SQLiteOpenHelper {

    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String vehiculo = "create table tbl_vehiculo(" +
            "placa text primary key, marca text not null, " +
            "modelo text not null, valor integer not null," +
            " activo not null default 'si'" +
            ")";

        String factura = "create table tbl_factura(" +
            "codigo_factura text primary key, fecha text not null, " +
            "placa text not null, activo not null default 'si', " +
            "constraint pk_factura foreign key(placa) references tbl_vehiculo(placa)" +
            ")";

        sqLiteDatabase.execSQL(vehiculo);
        sqLiteDatabase.execSQL(factura);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE tbl_vehiculo"); {
            onCreate(sqLiteDatabase);
        }

        sqLiteDatabase.execSQL("DROP TABLE tbl_factura"); {
            onCreate(sqLiteDatabase);
        }

    }
}
