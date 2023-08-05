package empresa.android;

public class ClienteBean {
    int idcliente = 0;
    String nombre, textocliente;
    int dni;

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getTextocliente() {
        return textocliente;
    }

    public void setTextocliente(String textocliente) {
        this.textocliente = textocliente;
    }

    public void generarTexto(){
        String texto = "Datos del cliente\n\n"+
                "Nombre: "+nombre+"\n"+
                "DNI: "+dni;
        this.textocliente = texto;
    }
}
