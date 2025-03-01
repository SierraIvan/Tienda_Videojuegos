package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Venta {
	
	//metodo para crear la tabla de ventas
    public static void crearTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Vender ("
                + "id_venta INT AUTO_INCREMENT PRIMARY KEY, "
                + "id_producto INT, "
                + "id_cliente INT, "
                + "FOREIGN KEY (id_producto) REFERENCES Producto(id_producto), "
                + "FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    
    //metodo para realizar una venta
    public static void realizarVenta(Connection conexion) throws SQLException {
    	Statement stmt = conexion.createStatement();
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce el nombre del producto vendido:");
        String productoVendido = sc.nextLine();

        System.out.println("Introduce el nombre del cliente:");
        String clienteVenta = sc.nextLine();


        String buscarCliente = "SELECT id_cliente FROM Clientes WHERE nombre = '" + clienteVenta + "'";
        ResultSet rsCliente = stmt.executeQuery(buscarCliente);
        int idCliente = rsCliente.next() ? rsCliente.getInt("id_cliente") : -1;


        String buscarProducto = "SELECT id_producto FROM Producto WHERE nombre = '" + productoVendido + "'";
        ResultSet rsProducto = stmt.executeQuery(buscarProducto);
        int idProducto = rsProducto.next() ? rsProducto.getInt("id_producto") : -1;


        if (idProducto != -1 && idCliente == -1) {
            System.out.println("Cliente no encontrado. Ingresando un nuevo cliente...");
            Cliente.insertarCliente(conexion);
            rsCliente = stmt.executeQuery(buscarCliente);
            idCliente = rsCliente.next() ? rsCliente.getInt("id_cliente") : -1;
        }

        System.out.println("Introduce la fecha de la venta (YYYY-MM-DD):");
        String fechaVenta = sc.nextLine();


        if (idProducto != -1 && idCliente != -1) {
            String sqlVender = "INSERT INTO Vender (fecha, id_producto, id_cliente) VALUES ('" + fechaVenta + "', "
                    + idProducto + ", " + idCliente + ")";
            String sqlStock = "UPDATE Producto SET stock = stock - 1 WHERE nombre = '" + productoVendido + "' AND stock > 0";

            stmt.executeUpdate(sqlVender);
            stmt.executeUpdate(sqlStock);
            System.out.println("Venta registrada correctamente.");
        } else {
            System.out.println("Error: Producto no encontrado.");
        }
    }
    
    
    //metodo para mostrar la tabla de ventaas
    public static void mostrarVentas(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();

	    
	    String query = "SELECT v.id_venta, v.fecha, p.nombre AS producto, cl.nombre AS cliente " +
	                   "FROM Vender v " +
	                   "JOIN Producto p ON v.id_producto = p.id_producto " +
	                   "JOIN Clientes cl ON v.id_cliente = cl.id_cliente";

	    ResultSet rs = stmt.executeQuery(query);

	    System.out.println("Detalles de Ventas:");
	    System.out.println("ID Venta | Fecha       | Producto                 | Cliente");
	    System.out.println("---------------------------------------------------------------------------------------");

	    while (rs.next()) {
	        int idVenta = rs.getInt("id_venta");
	        String fecha = rs.getString("fecha");
	        String producto = rs.getString("producto");
	        String cliente = rs.getString("cliente");

	        String fila = idVenta + "        | " + fecha + " | " + producto;
	        if (producto.length() < 25) {
	            fila += " ".repeat(25 - producto.length());
	        }
	        fila += " | " + cliente;
	        System.out.println(fila);
	    }
	    
	    rs.close();
	    stmt.close();
	}
}

