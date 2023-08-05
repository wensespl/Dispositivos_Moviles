package empresa.android.dao;

import empresa.android.bean.ProductoBean;

public class ProductoDAO {
    ProductoBean objProductoBean;
    String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ProductoBean getObjProductoBean() {
        return objProductoBean;
    }

    public void setObjProductoBean(ProductoBean objProductoBean) {
        this.objProductoBean = objProductoBean;
    }

    /*
    public int getMarca() {
        return this.objProductoBean.getMarca();
    }
    */

    public String CalcularOperacion(ProductoBean objProductoBean){
        String mensaje="";
        int marca = objProductoBean.getMarca();
        int talla = objProductoBean.getTalla();
        int tipoventa = objProductoBean.getTipoventa();
        int numpares = objProductoBean.getNumpares();
        this.objProductoBean = new ProductoBean();
        this.objProductoBean.setMarca(marca);
        this.objProductoBean.setTalla(talla);
        this.objProductoBean.setTipoventa(tipoventa);
        this.objProductoBean.setNumpares(numpares);

        int costo = CalcularCostoPorZapatilla(this.objProductoBean);
        this.objProductoBean.setCosto(costo);
        int venta = CalcularVenta(this.objProductoBean);
        this.objProductoBean.setVenta(venta);
        double descuento = CalcularDescuento(this.objProductoBean);
        this.objProductoBean.setDescuento(descuento);
        double ventanet = CalcularVentaNeta(this.objProductoBean);
        this.objProductoBean.setVentaneta(ventanet);
        mensaje = "El costo del par de zapatillas: "+costo+"\n"+
                "La venta de las zapatillas: "+venta+"\n"+
                "El descuento: "+descuento+"\n"+
                "La venta neta: "+ventanet;
        this.mensaje = mensaje;
        return mensaje;
    }
    public int CalcularCostoPorZapatilla(ProductoBean objProductoBean){
        int costo=0;
        switch (objProductoBean.getMarca()){
            case 0:{
                switch (objProductoBean.getTalla()){
                    case 0:{ costo = 150;    break; }
                    case 1:{ costo = 160;    break; }
                    case 2:{ costo = 170;    break; }
                }
                break;
            }
            case 1:{
                switch (objProductoBean.getTalla()){
                    case 0:{ costo = 140;    break; }
                    case 1:{ costo = 150;    break; }
                    case 2:{ costo = 160;    break; }
                }
                break;
            }
            case 2:{
                switch (objProductoBean.getTalla()){
                    case 0:{ costo = 80;    break; }
                    case 1:{ costo = 85;    break; }
                    case 2:{ costo = 90;    break; }
                }
                break;
            }
        }
        return costo;
    }
    public int CalcularVenta(ProductoBean objProductoBean){
        int venta = objProductoBean.getCosto()* objProductoBean.getNumpares();
        return venta;
    }
    public double CalcularDescuento(ProductoBean objProductoBean){
        double descuento = 0;
        if (objProductoBean.getNumpares()>=2 && objProductoBean.getNumpares()<=5)
            descuento = 0.05*objProductoBean.getVenta();
        if (objProductoBean.getNumpares()>=6 && objProductoBean.getNumpares()<=10)
            descuento = 0.08*objProductoBean.getVenta();
        if (objProductoBean.getNumpares()>=11 && objProductoBean.getNumpares()<=20)
            descuento = 0.1*objProductoBean.getVenta();
        if (objProductoBean.getNumpares()>=20)
            descuento = 0.15*objProductoBean.getVenta();
        return descuento;
    }
    public double CalcularVentaNeta(ProductoBean objProductoBean){
        double ventaneta = 0;
        ventaneta = objProductoBean.getVenta()-objProductoBean.getDescuento();
        return ventaneta;
    }
}
