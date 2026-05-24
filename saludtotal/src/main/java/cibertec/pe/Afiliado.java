package cibertec.pe;

import java.io.Serializable;

//agregamos serealizacion extentendiend la clase
public class Afiliado implements Serializable {
    private int codigo;
    private double monto;
    private boolean condicion;

    public Afiliado() {
        
    }

    public Afiliado(int codigo, double monto, boolean condicion) {
        this.codigo = codigo;
        this.monto = monto;
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

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

}
