package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Cliente {
	
	//metodo para crear la tabla de clientes
    public static void crearTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Clientes ("
                + "id_cliente INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "email VARCHAR(100))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    //metodo para mostrar la tabla clientes
    public static void mostrarClientes(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();
	    String query = "SELECT * FROM Clientes";
	    ResultSet rs = stmt.executeQuery(query);
	    
	    System.out.println("\n=== LISTADO DE CLIENTES ===");
	    System.out.println("ID | Nombre | Email | Teléfono ");
	    System.out.println("------------------------------------------------------");
	    
	    while (rs.next()) {
	        System.out.printf("%d | %s | %s | %s%n",
	            rs.getInt("id_cliente"),
	            rs.getString("nombre"),
	            rs.getString("email"),
	            rs.getString("telefono")
	        );
	    }
	    
	    System.out.println("------------------------------------------------------");
	    
	    
	    System.out.println("\nPresiona Enter para continuar...");
	    Scanner sc = new Scanner(System.in);
	    sc.nextLine();
	    rs.close();
	    stmt.close();
	}

    //metodo para insertar datos en la tabla clientes
    public static void insertarCliente(Connection conexion) throws SQLException {
    	Statement stmt = conexion.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        
        String buscarClientes = "SELECT id_cliente FROM Clientes WHERE nombre = '" + nombre + "'";
        ResultSet rsClientes = stmt.executeQuery(buscarClientes);
        
        int idcliente = -1;
        if (rsClientes.next()) {
            idcliente = rsClientes.getInt("id_cliente");
        }
        
        if (idcliente != -1) {
            System.out.println("Cliente existente");
          
        }else {
        
        System.out.println("Telefono: ");
        int telefono = sc.nextInt();
        	
        System.out.print("Email: ");
        String email = sc.nextLine();

        String sql = "INSERT INTO Clientes (nombre,telefono, email) VALUES ('" + nombre + "', '" + telefono +"', '"  + email + "')";
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Cliente agregado con éxito.");
        }
    }
}
