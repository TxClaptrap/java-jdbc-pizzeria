package ies;

import java.sql.SQLException;
import java.util.List;
import ies.controlador.ControladorCliente;
import ies.controlador.ControladorProducto;
import ies.modelo.Bebida;
import ies.modelo.Cliente;
import ies.modelo.Ingrediente;
import ies.modelo.Pasta;
import ies.modelo.Pizza;
import ies.modelo.Producto;
import ies.modelo.SIZE;
import ies.utils.DatabaseConf;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        try {
            DatabaseConf.dropAndCreateTables();
        } catch (SQLException e) {
            System.out.println("Te peinas.");
            e.printStackTrace();
        }

        ControladorCliente controladorCliente = new ControladorCliente();
        ControladorProducto controladorProducto = new ControladorProducto();

        Cliente cliente = new Cliente("11111111P", "Pepe", "C/Falsa", "666000000", "zi@f.c", "cosa");
        Cliente cliente2 = new Cliente("11111111Z", "Pepa", "C/Falsa", "766000000", "zo@f.c", "cosa");

        Ingrediente echamas = new Ingrediente("Queso", List.of("lactosa", "adictivos"));
        Ingrediente baseTrigo = new Ingrediente("Base con trigo", List.of("gluten", "sulfitos"));
        Ingrediente baseSosa = new Ingrediente("Base sin trigo", List.of());
        Ingrediente tomatico = new Ingrediente("Tomate", List.of("fructosa"));
        Ingrediente chicha = new Ingrediente("Jamón York", List.of());
        Ingrediente gazpacho = new Ingrediente("Piña", List.of("fructosa"));
        Ingrediente chicha2 = new Ingrediente("Carne picada", List.of());
        
        Producto aberrante = new Pizza("Hawaiana", 8, SIZE.MEDIANO , List.of(baseTrigo, tomatico, echamas, chicha, gazpacho));
        Producto bolognese = new Pasta("Boloñesa celiaca", 10, List.of(echamas, baseSosa, tomatico, chicha2));
        Producto cola = new Bebida("CocaCola", 2, SIZE.PEQUENO);



        Producto carbonara = new Pizza("Carbonara", 10, SIZE.GRANDE, List.of(echamas, baseTrigo, chicha));
        


        try {
            controladorCliente.registrarCliente(cliente);
            controladorCliente.registrarCliente(cliente2);
            
            List<Cliente> clientes = controladorCliente.encontrarTodos();
            for (Cliente c : clientes) {
                System.out.println(c);
            }
            
        } catch (SQLException e) {
            System.out.println("Te peinas.");
            e.printStackTrace();
        }

        try {
            controladorProducto.registrarProducto(carbonara);
            controladorProducto.registrarProducto(carbonara);


            controladorProducto.enontrarProductoById(carbonara.getId());
            
            carbonara.setNombre("Pizza Carbonara");

            controladorProducto.actualizarProducto(carbonara.getId());
            controladorProducto.registrarProducto(aberrante);
            controladorProducto.registrarProducto(bolognese);
            controladorProducto.registrarProducto(cola);
        } catch (SQLException e) {
            System.out.println("Te peinas");
            e.printStackTrace();
        }

        /* Antiguo Main

        /*
        Ingrediente echamas = new Ingrediente(1, "Queso", List.of("lactosa, adictivos"));
        Ingrediente baseTrigo = new Ingrediente(2, "Base con trigo", List.of("gluten, sulfitos"));
        Ingrediente baseSosa = new Ingrediente(3, "Base sin trigo", List.of());
        Ingrediente tomatico = new Ingrediente(4, "Tomate", List.of("fructosa"));
        Ingrediente chicha = new Ingrediente(5, "Jamón York", List.of());
        Ingrediente gazpacho = new Ingrediente(6, "Piña", List.of("fructosa"));
        Ingrediente chicha2 = new Ingrediente(7, "Carne picada", List.of());
        
        Pizza aberrante = new Pizza(1, "Hawaiana", 8, SIZE.MEDIANO , List.of(baseTrigo, tomatico, echamas, chicha, gazpacho));
        Pasta bolognese = new Pasta(2, "Boloñesa celiaca", 10, List.of(echamas, baseSosa, tomatico, chicha2));

        PagarTarjeta pagoTarjeta = new PagarTarjeta();*/

        //Creando controladores necesarios
        
        
        /*controladorCliente.registrarCliente("00000000Z", "Pepe", "C/aaaaaa", "000000000", "a@g.com", "lala");
        controladorCliente.registrarCliente("00000001X", "Pepa", "C/aaaaaa", "000000001", "b@g.com", "lale");

        for (Cliente cliente : controladorCliente.getListaClientes()) {
            System.out.println(cliente);
        }
        controladorCliente.loginCliente("a@g.com", "lala");
        controladorCliente.loginCliente("b@g.com", "lala");

        ControladorPedido controladorPedido = new ControladorPedido(1, controladorCliente.getClienteActual());
        
        controladorPedido.agregarLineaPedido(bolognese, 3, SIZE.GRANDE);
        controladorPedido.agregarLineaPedido(aberrante, 1, SIZE.MEDIANO);

        controladorPedido.finalizarPedido(pagoTarjeta);
        controladorPedido.cancelarPedido();

        controladorPedido.entregarPedido(1); /* 

        //Probando que lee el archivo .txt
        List<Cliente> administradores;
        try {
            administradores = controladorCliente.leerClientes();
            administradores.forEach(System.out::println);
        } catch (IOException e) {
            administradores = new ArrayList<>();
            e.printStackTrace();
        }

        //Probando que exporta clientes a XML
        try {
            controladorCliente.exportarClientesXML(administradores);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Probando que importa clientes desde XML
        try {
            controladorCliente.importarClientesXML().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Creando lista de ingredientes
        List<Ingrediente> ingredientes = new ArrayList<>() {{
            add(new Ingrediente(1, "Queso", List.of("lactosa", "adictivos")));
            add(new Ingrediente(2, "Base con trigo", List.of("gluten", "sulfitos")));
            add(new Ingrediente(3, "Base sin trigo", List.of()));
            add(new Ingrediente(4, "Tomate", List.of("fructosa")));
            add(new Ingrediente(5, "Jamón York", List.of()));
        }};

        //Probando que exporta ingredientes a CSV
        try {
            controladorProducto.exportarIngredientesCSV(ingredientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Probando que importa ingredientes desde CSV
        try {
            controladorProducto.importarIngredientesCSV().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        */
    }
}
