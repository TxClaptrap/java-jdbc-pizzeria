package ies;

public class posible {
    public static void main(String[] args) {
        
    }

    /*
     * 
     import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import ies.modelo.Pedido;
import ies.modelo.LineaPedido;
import ies.modelo.Producto;

public class Varios {

    // Otros métodos...

    public boolean exportarPedidoTXT(Pedido pedido, String rutaArchivo) throws IOException {
        // Construye el contenido del pedido
        StringBuilder contenido = new StringBuilder();
        contenido.append("Pedido ID: ").append(pedido.getId()).append("\n")
                 .append("Cliente: ").append(pedido.getCliente().getNombre()).append("\n")
                 .append("Fecha: ").append(pedido.getFecha()).append("\n")
                 .append("Estado: ").append(pedido.getEstado()).append("\n\n")
                 .append("Líneas del Pedido:\n");

        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto producto = linea.getProducto();
            contenido.append("Producto: ").append(producto.getNombre()).append("\n")
                     .append("Cantidad: ").append(linea.getCantidad()).append("\n")
                     .append("Precio Unitario: ").append(producto.getPrecio()).append(" €\n")
                     .append("Subtotal: ").append(linea.getSubtotal()).append(" €\n");
            
            if (linea.getSize() != null) {
                contenido.append("Tamaño: ").append(linea.getSize()).append("\n");
            }
            contenido.append("\n");
        }

        contenido.append("Total del Pedido: ").append(pedido.getTotal()).append(" €");

        // Guarda el contenido en el archivo de texto
        Files.write(Path.of(rutaArchivo), contenido.toString().lines().collect(Collectors.toList()));
        return true;
    }
}


Pedido pedido = // obtener o crear pedido
String rutaArchivo = "ruta/del/archivo/pedido.txt";
try {
    controladorPruebas.exportarPedidoTXT(pedido, rutaArchivo);
    System.out.println("Pedido exportado con éxito.");
} catch (IOException e) {
    e.printStackTrace();
}

     */

     /*
      * 
      import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Pedido {
    private int id;
    private Cliente cliente;
    private String fecha;
    private String estado;
    private List<LineaPedido> lineasPedido;
    private double total;

    // Constructor, getters, setters...

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public Cliente getCliente() {
        return cliente;
    }

    @XmlElement
    public String getFecha() {
        return fecha;
    }

    @XmlElement
    public String getEstado() {
        return estado;
    }

    @XmlElement
    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    @XmlElement
    public double getTotal() {
        return total;
    }
}


import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Varios {

    // Otros métodos...

    public boolean exportarPedidoXML(Pedido pedido, String rutaArchivo) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Pedido.class, LineaPedido.class, Cliente.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Para que el XML sea legible
        marshaller.marshal(pedido, new File(rutaArchivo)); // Guarda el pedido como archivo XML

        return true;
    }
}


Pedido pedido = // obtener o crear el pedido
String rutaArchivo = "ruta/del/archivo/pedido.xml";
try {
    controladorPruebas.exportarPedidoXML(pedido, rutaArchivo);
    System.out.println("Pedido exportado a XML con éxito.");
} catch (JAXBException e) {
    e.printStackTrace();
}



      */


      /*
       * 
       * import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Varios {

    // Otros métodos...

    public boolean exportarPedidoCSV(Pedido pedido, String rutaArchivo) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivo))) {
            // Agrega la cabecera
            String[] header = { "PedidoID", "Cliente", "Fecha", "Estado", "ProductoID", "ProductoNombre", "Cantidad", "PrecioUnitario", "TotalLinea" };
            writer.writeNext(header);

            // Escribir cada línea de pedido
            for (LineaPedido linea : pedido.getLineasPedido()) {
                String[] data = {
                    String.valueOf(pedido.getId()),
                    pedido.getCliente().getNombre(),
                    pedido.getFecha(),
                    pedido.getEstado(),
                    String.valueOf(linea.getProducto().getId()),
                    linea.getProducto().getNombre(),
                    String.valueOf(linea.getCantidad()),
                    String.valueOf(linea.getProducto().getPrecio()),
                    String.valueOf(linea.getTotalLinea())
                };
                writer.writeNext(data);
            }
        }
        return true;
    }
}

       */
}
