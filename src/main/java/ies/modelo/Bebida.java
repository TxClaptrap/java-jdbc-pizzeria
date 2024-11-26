package ies.modelo;

public class Bebida extends Producto{
    private SIZE tamano;

    public Bebida(String nombre, double precio, SIZE tamano) {
        super(nombre, precio);
        this.tamano = tamano;
    }

    public SIZE getTamano() {
        return tamano;
    }

    public void setTamano(SIZE tamano) {
        this.tamano = tamano;
    }
    
}
