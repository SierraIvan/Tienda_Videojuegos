package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
	
	
    public static void main(String[] args) {
        try (Connection conexion = Conexion.getConnection()) {
            Producto.crearTabla(conexion);
            Cliente.crearTabla(conexion);
            Proveedor.crearTabla(conexion);
            Compra.crearTabla(conexion);
            Venta.crearTabla(conexion);
            Scanner sc = new Scanner(System.in);
            
            int opcion;
            
            do {
                System.out.println("\n===== MENÚ PRINCIPAL =====");
                System.out.println("1. Agregar Producto");
                System.out.println("2. Mostrar Productos");
                System.out.println("3. Agregar Cliente");
                System.out.println("4. Mostrar Clientes");
                System.out.println("5. Agregar Proveedor");
                System.out.println("6. Mostrar Proveedores");
                System.out.println("7. Registrar Compra");
                System.out.println("8. Registrar Venta");
                System.out.println("9. Ver Compras");
                System.out.println("10. Ver Ventas");
                System.out.println("11. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        Producto.insertarProducto(conexion);
                        break;
                    case 2:
                        Producto.mostrarProductos(conexion);
                        break;
                    case 3:
                        Cliente.insertarCliente(conexion);
                        break;
                    case 4:
                        Cliente.mostrarClientes(conexion);
                        break;
                    case 5:
                        Proveedor.insertarProveedor(conexion);
                        break;
                    case 6:
                        Proveedor.mostrarProveedores(conexion);
                        break;
                    case 7:
                        Compra.realizarCompra(conexion);
                        break;
                    case 8:
                        Venta.realizarVenta(conexion);
                        break;
                    case 9:
                        Compra.mostrarCompras(conexion);
                        break;
                    case 10:
                        Venta.mostrarVentas(conexion);
                        break;
                    case 11:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                        break;
                }
            } while (opcion != 11);
            
            

            sc.close();
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        
    }

	}
}
