package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/tiendavideojuegos?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    
    
    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASS);
            
            System.out.println("Conexión establecida con éxito");
            Scanner sc = new Scanner(System.in);
            
            
            
            int opcion = 1;
            while(opcion != 0) {
            	System.out.println("0- Salir\n1- Añadir Datos");
            	opcion = sc.nextInt();
            	
            switch(opcion) {
            case 0:
            	System.out.println("Saliendo...");
            	break;
            case 1:
            	AñadirTabla(conexion);
            	break;
            default:
            	System.out.println("no se");
            }
            
            	
            	
            }
            
            
         
            
            
            conexion.close();
            System.out.println("Conexión cerrada");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    } 
    
    private static void CrearTablaProducto(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Producto ("
            + "id_producto INT AUTO_INCREMENT PRIMARY KEY,"
            + "nombre VARCHAR(50) NOT NULL,"
            + "precio DECIMAL(4,2),"
            + "stock INT"
            + ")";
        
        stmt.executeUpdate(createTableSQL);
        System.out.println("Tabla producto creada correctamente");
        
        stmt.close();
    }
    
    private static void CrearTablaProveedores(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Proveedores ("
            + "id_proveedor INT AUTO_INCREMENT PRIMARY KEY,"
            + "nombre VARCHAR(30) NOT NULL,"
            + "contacto VARCHAR(50),"
            + "direccion VARCHAR(50)"
            + ")";
        
        stmt.executeUpdate(createTableSQL);
        System.out.println("Tabla proveedores creada correctamente");
        
        stmt.close();
    }
    
    private static void CrearTablaClientes(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Clientes ("
            + "id_cliente INT AUTO_INCREMENT PRIMARY KEY,"
            + "nombre VARCHAR(30) NOT NULL,"
            + "telefono INT,"
            + "email VARCHAR(50)"
            + ")";
        
        stmt.executeUpdate(createTableSQL);
        System.out.println("Tabla clientes creada correctamente");
        
        stmt.close();
    }
    
    private static void CrearTablaPersonal(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Personal ("
            + "id_personal INT AUTO_INCREMENT PRIMARY KEY,"
            + "nombre VARCHAR(30) NOT NULL,"
            + "rol ENUM('administrador', 'empleado')"
            + ")";
        
        stmt.executeUpdate(createTableSQL);
        System.out.println("Tabla personal creada correctamente");
        
        stmt.close();
    }
    
    
    //metodo para añadir datos a tablas
    public static void AñadirTabla(Connection conexion) throws SQLException {
        Statement stmt = conexion.createStatement();
        Scanner sc = new Scanner(System.in);

        System.out.println("1- Productos\n2- Porveedores\n3- Clientes\n4- Personal\nEn que tabla quieres añadir: ");
        
        int opcion = sc.nextInt();
    	
        switch(opcion) {
        	
        case 1:
            System.out.println("Introduce el nombre del producto:");
            String nombre = sc.next();

            System.out.println("Introduce el precio del producto(con 2 decimales):");
            Double precio = sc.nextDouble();

            System.out.println("Introduce el stock que tiene el prodcuto:");
            int stock = sc.nextInt();
            
            String añadir = "INSERT INTO producto(nombre, precio, stock) VALUES ('" 
                    + nombre + "', " + precio + ", " + stock + ")";
            stmt.executeUpdate(añadir);
            break;
           
         default:
        	 System.out.println("no se encontro opción");
        
        }
        
        

        System.out.println("Producto añadido correctamente.");
    }

}
