package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import empresa.android.bean.ProductoBean;
import empresa.android.dao.ProductoDAO;

public class ConexionSQLite extends SQLiteOpenHelper {

    public ConexionSQLite(Context context) {
        super(context, "VENTASBD.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ventas(idventa INTEGER PRIMARY KEY AUTOINCREMENT, marca INTEGER NOT NULL, talla INTEGER NOT NULL, tipoventa INTEGER NOT NULL, numpares INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ventas;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertarVenta(ProductoDAO pDAO) {
        int marca = pDAO.getObjProductoBean().getMarca();
        int talla = pDAO.getObjProductoBean().getTalla();
        int tipoventa = pDAO.getObjProductoBean().getTipoventa();
        int numpares = pDAO.getObjProductoBean().getNumpares();
        ContentValues valores = new ContentValues();
        valores.put("marca", marca);
        valores.put("talla", talla);
        valores.put("tipoventa", tipoventa);
        valores.put("numpares", numpares);
        this.getWritableDatabase().insert("ventas", null, valores);
    }

    public void vaciarTabla() {
        this.getWritableDatabase().execSQL("DELETE FROM ventas;");
    }

    public void eliminarVenta(int idventa) {
        this.getWritableDatabase().execSQL("DELETE FROM ventas where idventa ='"+idventa+"'");
    }

    public void modificarVenta(ProductoDAO pDAO, int idventa) {
        int marca = pDAO.getObjProductoBean().getMarca();
        int talla = pDAO.getObjProductoBean().getTalla();
        int tipoventa = pDAO.getObjProductoBean().getTipoventa();
        int numpares = pDAO.getObjProductoBean().getNumpares();
        String sql = "UPDATE ventas SET marca='"+marca+"',talla='"+talla+"',tipoventa='"+tipoventa+"',numpares='"+numpares+"' WHERE idventa='"+idventa+"'";
        this.getWritableDatabase().execSQL(sql);
    }

    public ArrayList<ProductoDAO> listarVentas() {
        ArrayList<ProductoDAO> ventas = new ArrayList<ProductoDAO>();
        SQLiteDatabase db = this.getWritableDatabase();
        String columnas[] = {"idventa", "marca", "talla", "tipoventa", "numpares"};
        Cursor c = db.query("ventas", columnas, null, null, null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ProductoBean pBean = new ProductoBean();
            pBean.setIdventa(c.getInt(0));
            pBean.setMarca(c.getInt(1));
            pBean.setTalla(c.getInt(2));
            pBean.setTipoventa(c.getInt(3));
            pBean.setNumpares(c.getInt(4));
            ProductoDAO pDAO = new ProductoDAO();
            pDAO.setObjProductoBean(pBean);
            ventas.add(pDAO);
        }
        return ventas;
    }

    public int tamanoVentas() {
        SQLiteDatabase db = this.getWritableDatabase();
        String columnas[] = {"idventa", "marca", "talla", "tipoventa", "numpares"};
        Cursor c = db.query("ventas", columnas, null, null, null, null, null);
        return c.getCount();
    }

    public void abrir() {
        this.getWritableDatabase();
    }

    public void cerrar() {
        this.close();
    }
}
