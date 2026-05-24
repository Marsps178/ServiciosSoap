package cibertec.pe;

import java.io.Serializable;

//agregamos serealizacion extentendiend la clase
public class Afiliado implements Serializable {
    private int codigo;
    private double monto;
    private String nombre;
    private String apellido;
    private String correo;
    private int condicion;

    public Afiliado() {
        
    }

    public Afiliado(int codigo, double monto, String nombre, String apellido, String correo, int condicion) {
        this.codigo = codigo;
        this.monto = monto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.condicion = condicion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }



}
