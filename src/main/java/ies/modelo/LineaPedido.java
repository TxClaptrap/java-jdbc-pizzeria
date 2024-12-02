package ies.modelo;

import java.util.List;

public class LineaPedido {
    
    private int id;
    private int cantidad;
    private Pedido pedido;
    private Producto producto;
    private Double precio;

    public LineaPedido(int id, int cantidad, Pedido pedido, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.pedido = pedido;
        this.producto = producto;
        this.precio = producto.getPrecio() * cantidad;
    }

    public LineaPedido(List<LineaPedido> listaLineaPedidos) {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getPrecio() {
        return precio;
    }

}
