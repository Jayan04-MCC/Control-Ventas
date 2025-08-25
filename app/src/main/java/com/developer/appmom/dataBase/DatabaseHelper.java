package com.developer.appmom.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "compras.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de proveedores
    public static final String TABLE_PROVEEDORES = "proveedores";
    public static final String COL_PROV_ID = "id";
    public static final String COL_PROV_NOMBRE = "nombre";

    // Tabla de compras
    public static final String TABLE_COMPRAS = "compras";
    public static final String COL_COMPRA_ID = "id";
    public static final String COL_COMPRA_PROV_ID = "proveedor_id";
    public static final String COL_COMPRA_PRODUCTO = "producto";
    public static final String COL_COMPRA_CANTIDAD = "cantidad";
    public static final String COL_COMPRA_PRECIO = "precio";
    public static final String COL_COMPRA_FECHA = "fecha";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla proveedores
        String createProveedores = "CREATE TABLE " + TABLE_PROVEEDORES + "("
                + COL_PROV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PROV_NOMBRE + " TEXT NOT NULL)";

        db.execSQL(createProveedores);

        // Crear tabla compras
        String createCompras = "CREATE TABLE " + TABLE_COMPRAS + "("
                + COL_COMPRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_COMPRA_PROV_ID + " INTEGER,"
                + COL_COMPRA_PRODUCTO + " TEXT,"
                + COL_COMPRA_CANTIDAD + " INTEGER,"
                + COL_COMPRA_PRECIO + " REAL,"
                + COL_COMPRA_FECHA + " TEXT,"
                + "FOREIGN KEY(" + COL_COMPRA_PROV_ID + ") REFERENCES "
                + TABLE_PROVEEDORES + "(" + COL_PROV_ID + "))";
        db.execSQL(createCompras);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPRAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVEEDORES);
        onCreate(db);
    }

    public long insertarProveedor(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PROV_NOMBRE, nombre);
        return db.insert(TABLE_PROVEEDORES, null, values);
    }

    public Cursor obtenerProveedores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PROVEEDORES, null);
    }

    //eliminar proveedores
    public void borrarTodosProveedores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("proveedores", null, null); // elimina todos los registros
        db.close();
    }

    //realizar una compra
    public long insertarCompra(int proveedorId, String producto, int cantidad, double precio, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COMPRA_PROV_ID, proveedorId);  // Usar ID, no nombre
        values.put(COL_COMPRA_PRODUCTO, producto);
        values.put(COL_COMPRA_CANTIDAD, cantidad);
        values.put(COL_COMPRA_PRECIO, precio);
        values.put(COL_COMPRA_FECHA, fecha);

        return db.insert(TABLE_COMPRAS, null, values);
    }

    //listar compras
    public ArrayList<String> obtenerComprasPorProveedor(int proveedorId) {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT producto, cantidad, precio, fecha FROM compras WHERE proveedor_id = ?",
                new String[]{String.valueOf(proveedorId)});

        if (cursor.moveToFirst()) {
            do {
                String producto = cursor.getString(0);
                int cantidad = cursor.getInt(1);
                double precio = cursor.getDouble(2);
                String fecha = cursor.getString(3);

                String detalle = "Producto: " + producto +
                        "\nCantidad: " + cantidad +
                        "\nPrecio: " + precio +
                        "\nFecha: " + fecha;
                lista.add(detalle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }


    public Cursor obtenerTotalesPorProveedor() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta que agrupa por proveedor
        String query = "SELECT p." + COL_PROV_NOMBRE + " as nombre, " +
                "SUM(c." + COL_COMPRA_CANTIDAD + " * c." + COL_COMPRA_PRECIO + ") as total " +
                "FROM " + TABLE_COMPRAS + " c " +
                "INNER JOIN " + TABLE_PROVEEDORES + " p " +
                "ON c." + COL_COMPRA_PROV_ID + " = p." + COL_PROV_ID + " " +
                "GROUP BY p." + COL_PROV_NOMBRE;

        return db.rawQuery(query, null);
    }



}
