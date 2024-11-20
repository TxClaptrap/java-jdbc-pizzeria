package ies.modelo;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "clientes")
@XmlSeeAlso({Pizza.class, Pasta.class, Bebida.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Producto {
    @XmlAttribute
    private int id;
    private String nombre;
    private double precioTotal;
    
    public Producto(int id, String nombre, double precioTotal) {
        this.id = id;
        this.nombre = nombre;
        this.precioTotal = precioTotal;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precioTotal;
    }
    public void setPrecio(double precio) {
        this.precioTotal = precio;
    } 

    
}
