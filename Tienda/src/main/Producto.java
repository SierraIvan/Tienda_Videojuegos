package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Producto {
	
	//metodo para crear la tabla producto
    public static void crearTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Producto ("
                + "id_producto INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "precio DOUBLE, "
                + "stock INT)";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    //metodo para mostrar la tabla productos
    public static void mostrarProductos(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();
	    String query = "SELECT * FROM Producto";
	    ResultSet rs = stmt.executeQuery(query);
	    
	    System.out.println("\n=== LISTADO DE PRODUCTOS ===");
	    System.out.println("ID | Nombre | Precio | Stock");
	    System.out.println("------------------------------------");
	    
	    while (rs.next()) {
	        System.out.printf("%d | %s | %.2f | %d%n",
	            rs.getInt("id_producto"),
	            rs.getString("nombre"),
	            rs.getDouble("precio"),
	            rs.getInt("stock")
	        );
	    }
	    
	    System.out.println("------------------------------------");
	    
	    
	    System.out.println("\nPresiona Enter para continuar...");
	    Scanner sc = new Scanner(System.in);
	    sc.nextLine();
	    rs.close();
	    stmt.close();
	}

    
    //metodo para insertar datos en la tabla productos
    public static void insertarProducto(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        
        String buscarNombre = "SELECT id_producto FROM Producto WHERE nombre = '" + nombre + "'";
        ResultSet rsProducto = stmt.executeQuery(buscarNombre);
        
        int idproducto = -1;
        if (rsProducto.next()) {
            idproducto = rsProducto.getInt("id_producto");
        }
        
        if (idproducto != -1) {
            System.out.println("Producto existente, introduce nuevo stock: ");
            int stock = sc.nextInt();
            

            String updateSQL = "UPDATE Producto SET stock = " + stock + " WHERE id_producto = " + idproducto;
            stmt.executeUpdate(updateSQL);
            System.out.println("Stock actualizado con éxito.");
            
        } else {
            System.out.print("Precio: ");
            double precio = sc.nextDouble();
            System.out.print("Stock: ");
            int stock = sc.nextInt();

            String insertSQL = "INSERT INTO Producto (nombre, precio, stock) VALUES ('" + nombre + "', " + precio + ", " + stock + ")";
            stmt.executeUpdate(insertSQL);
            System.out.println("Producto agregado con éxito.");
        }

        rsProducto.close();
        stmt.close();
    }

}

