package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/tiendavideojuegos?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    
    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión establecida con éxito");
         
            CrearTablaProducto(conexion);
            CrearTablaProveedores(conexion);
            CrearTablaClientes(conexion);
            CrearTablaPersonal(conexion);
            
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
            + "fecha_creacion DATE,"
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
            + "id_personal INT AUTO_INCREMENT PRIMARY KEY,"  // Corregido a id_personal
            + "nombre VARCHAR(30) NOT NULL,"
            + "telefono INT,"
            + "email VARCHAR(50)"
            + ")";
        
        stmt.executeUpdate(createTableSQL);
        System.out.println("Tabla personal creada correctamente");  // Corregido mensaje de consola
        
        stmt.close();
    }
}
