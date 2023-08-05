package empresa.android.bean;

public class ProductoBean {
    int idventa = 0;
    int marca;
    int talla;
    int tipoventa;
    int costo;
    int numpares;
    int venta;

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    double descuento;
    double ventaneta;

    public int getTipoventa() {
        return tipoventa;
    }

    public void setTipoventa(int tipoventa) {
        this.tipoventa = tipoventa;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getNumpares() {
        return numpares;
    }

    public void setNumpares(int numpares) {
        this.numpares = numpares;
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getVentaneta() {
        return ventaneta;
    }

    public void setVentaneta(double ventaneta) {
        this.ventaneta = ventaneta;
    }
}
