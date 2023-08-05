package com.example.examenparcial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import empresa.android.ClienteBean;

public class ConexionSQLite extends SQLiteOpenHelper {

    public ConexionSQLite(Context context) {
        super(context, "CLIENTESBD.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE clientes(idcliente INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, dni INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS clientes;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertarCliente(ClienteBean clienteBean) {
        String nombre = clienteBean.getNombre();
        int dni = clienteBean.getDni();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("dni", dni);

        this.getWritableDatabase().insert("clientes", null, valores);
    }

    public ArrayList<ClienteBean> listarClientes() {
        ArrayList<ClienteBean> clientes = new ArrayList<ClienteBean>();
        SQLiteDatabase db = this.getWritableDatabase();
        String columnas[] = {"idcliente", "nombre", "dni"};
        Cursor c = db.query("clientes", columnas, null, null, null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ClienteBean clienteBean = new ClienteBean();
            clienteBean.setIdcliente(c.getInt(0));
            clienteBean.setNombre(c.getString(1));
            clienteBean.setDni(c.getInt(2));
            clientes.add(clienteBean);
        }
        return clientes;
    }
}
