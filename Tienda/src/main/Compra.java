package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Compra {
	
	//metodo para crear la tabla Compra
    public static void crearTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Comprar ("
                + "id_compra INT AUTO_INCREMENT PRIMARY KEY, "
                + "fecha DATE NOT NULL, "
                + "id_producto INT NOT NULL, "
                + "id_proveedor INT NOT NULL, "
                + "cantidad INT NOT NULL, "
                + "FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE ON UPDATE CASCADE, "
                + "FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE ON UPDATE CASCADE"
                + ")";

        stmt.executeUpdate(sql);
        stmt.close();
    }

    
    //metodo para realizar una compra
    public static void realizarCompra(Connection conexion) throws SQLException {
    	 Statement stmt = conexion.createStatement();
         Scanner sc = new Scanner(System.in);
         
         System.out.println("Introduce el nombre del producto comprado:");
         String productoComprado = sc.nextLine();
         
         String buscarProductoCompra = "SELECT id_producto FROM Producto WHERE nombre = '" + productoComprado + "'";
         ResultSet rsProductoCompra = stmt.executeQuery(buscarProductoCompra);
         int idProductoCompra = rsProductoCompra.next() ? rsProductoCompra.getInt("id_producto") : -1;
         
         if (idProductoCompra == -1) {
             System.out.println("Producto no encontrado. Introduciendo un nuevo producto...");
             Producto.insertarProducto(conexion);
             rsProductoCompra = stmt.executeQuery(buscarProductoCompra);
             idProductoCompra = rsProductoCompra.next() ? rsProductoCompra.getInt("id_producto") : -1;
         }

         System.out.println("Introduce el nombre del proveedor:");
         String proveedorCompra = sc.nextLine();
         
         String buscarProveedor = "SELECT id_proveedor FROM Proveedores WHERE nombre = '" + proveedorCompra + "'";
         ResultSet rsProveedor = stmt.executeQuery(buscarProveedor);
         int idProveedor = rsProveedor.next() ? rsProveedor.getInt("id_proveedor") : -1;
         
         if (idProveedor == -1) {
             System.out.println("Proveedor no encontrado. Introduciendo un nuevo proveedor...");
             Proveedor.insertarProveedor(conexion);
             rsProveedor = stmt.executeQuery(buscarProveedor);
             idProveedor = rsProveedor.next() ? rsProveedor.getInt("id_proveedor") : -1;
         }

         System.out.println("Introduce la fecha de la compra (YYYY-MM-DD):");
         String fechaCompra = sc.nextLine();
         
         
         System.out.println("Introduce la cantidad ");
         int cantidad = sc.nextInt();

         if (idProductoCompra != -1 && idProveedor != -1) {
             String sqlComprar = "INSERT INTO Comprar (fecha, id_producto, id_proveedor, cantidad) VALUES ('" + fechaCompra + "', "
                     + idProductoCompra + ", " + idProveedor + ", " + cantidad + ")";
             String sqlStock = "UPDATE Producto SET stock = stock + " + cantidad + " WHERE nombre = '" + productoComprado + "'";

             stmt.executeUpdate(sqlStock);
             stmt.executeUpdate(sqlComprar);

             System.out.println("Compra registrada correctamente.");
         } else {
             System.out.println("Error: No se pudo registrar la compra.");
         }
     }
    
    
    //metodo para mostrar la tabla compras
    public static void mostrarCompras(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();

	    // Query JOIN para obtener detalles de compras
	    String query = "SELECT c.id_compra, c.fecha, p.nombre AS producto, pr.nombre AS proveedor, c.cantidad " +
	                   "FROM Comprar c " +
	                   "JOIN Producto p ON c.id_producto = p.id_producto " +
	                   "JOIN Proveedores pr ON c.id_proveedor = pr.id_proveedor";

	    ResultSet rs = stmt.executeQuery(query);
		for(int i = 0; i<10;i++) {
			System.out.println("");
		}
	    System.out.println("Detalles de Compras:");
	    System.out.println("ID Compra | Fecha       | Producto                 | Proveedor       |Cantidad");
	    System.out.println("-----------------------------------------------------------------------------------------");

	    while (rs.next()) {
	        int idCompra = rs.getInt("id_compra");
	        String fecha = rs.getString("fecha");
	        String producto = rs.getString("producto");
	        String proveedor = rs.getString("proveedor");
	        int cantidad = rs.getInt("cantidad");

	       
	        String fila = idCompra + "         | " + fecha + " | " + producto ;
	        if (producto.length() < 25) {
	            fila += " ".repeat(25 - producto.length());
	        }
	        fila += " | " + proveedor + " | " + cantidad;
	        System.out.println(fila);
	    }
	    
	    System.out.println("\nPresiona Enter para continuar...");
	    Scanner sc = new Scanner(System.in);
	    sc.nextLine();
	    
	    rs.close();
	    stmt.close();
	}
}

