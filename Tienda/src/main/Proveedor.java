package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Proveedor {
    public static void crearTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Proveedores ("
                + "id_proveedor INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "contacto VARCHAR(100))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void insertarProveedor(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del proveedor: ");
        String nombre = sc.nextLine();
        
        String buscarProveedores = "SELECT id_provedor FROM Proveedores WHERE nombre = '" + nombre + "'";
        ResultSet rsProveedor = stmt.executeQuery(buscarProveedores);
        
        int idproveedor = -1;
        if (rsProveedor.next()) {
            idproveedor = rsProveedor.getInt("id_producto");
        }
        if (idproveedor != -1) {
            System.out.println("Cliente existente");
          
        }else {
        
        System.out.print("Contacto: ");
        String contacto = sc.nextLine();


        String sql = "INSERT INTO Proveedor (nombre, contacto) VALUES ('" + nombre + "', '" + contacto + "')";
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Proveedor agregado con éxito.");
        }
    }
    
    public static void mostrarProveedores(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();
	    String query = "SELECT * FROM Proveedores";
	    ResultSet rs = stmt.executeQuery(query);
	    
	    System.out.println("\n=== LISTADO DE PROVEEDORES ===");
	    System.out.println("ID | Nombre | Contacto | Dirección");
	    System.out.println("------------------------------------------------");
	    
	    while (rs.next()) {
	        System.out.printf("%d | %s | %s | %s%n",
	            rs.getInt("id_proveedor"),
	            rs.getString("nombre"),
	            rs.getString("contacto"),
	            rs.getString("direccion")
	        );
	    }
	    
	    System.out.println("------------------------------------------------");
	    
	    System.out.println("\nPresiona Enter para continuar...");
	    Scanner sc = new Scanner(System.in);
	    sc.nextLine();
	    rs.close();
	    stmt.close();
	}

    
}
