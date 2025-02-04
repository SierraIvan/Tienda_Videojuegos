package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			while (opcion != 0) {
				for(int i = 0; i<10;i++) {
					System.out.println("");
				}
				System.out.println("0- Salir\n1- Añadir Datos\n2- Vender\n3- Comprar\n4- Mostrar Tabla");
				opcion = sc.nextInt();

				switch (opcion) {
				case 0:
					System.out.println("Saliendo...");
					break;
				case 1:
					System.out.println(
							"1- Productos\n2- Porveedores\n3- Clientes\n4- Compras\nEn que tabla quieres añadir: ");
					opcion = sc.nextInt();
					AñadirTabla(conexion, opcion);
					break;
				case 2:
					Vender(conexion);
					break;
				case 3:
					Comprar(conexion);
					break;
				case 4:
					for(int i = 0; i<10;i++) {
						System.out.println("");
					}
					System.out.println("1- Productos\n2- Provedores\n3- Clientes\n4- Compras\n5- Ventas\n¿Qué tabla quieres mostrar?\n ");
					opcion = sc.nextInt();
					mostrarTabla(conexion, opcion);
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

		String createTableSQL = "CREATE TABLE IF NOT EXISTS Producto (" + "id_producto INT AUTO_INCREMENT PRIMARY KEY,"
				+ "nombre VARCHAR(50) NOT NULL," + "precio DECIMAL(6,2)," + "stock INT" + ")";

		stmt.executeUpdate(createTableSQL);
		System.out.println("Tabla producto creada correctamente");

		stmt.close();
	}

	private static void CrearTablaProveedores(Connection conexion) throws SQLException {
		Statement stmt = conexion.createStatement();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS Proveedores ("
				+ "id_proveedor INT AUTO_INCREMENT PRIMARY KEY," + "nombre VARCHAR(30) NOT NULL,"
				+ "contacto VARCHAR(50)," + "direccion VARCHAR(50)" + ")";

		stmt.executeUpdate(createTableSQL);
		System.out.println("Tabla proveedores creada correctamente");

		stmt.close();
	}

	private static void CrearTablaVender(Connection conexion) throws SQLException {
		Statement stmt = conexion.createStatement();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS Vender (" + "id_venta INT AUTO_INCREMENT PRIMARY KEY,"
				+ "fecha DATE NOT NULL," + "id_producto INT," + "id_cliente INT,"
				+ "FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE ON UPDATE CASCADE,"
				+ "FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente) ON DELETE CASCADE ON UPDATE CASCADE" + ")";

		stmt.executeUpdate(createTableSQL);
		System.out.println("Tabla compra creada correctamente");

		stmt.close();
	}

	private static void CrearTablaComprar(Connection conexion) throws SQLException {
		Statement stmt = conexion.createStatement();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS Comprar (" + "id_compra INT AUTO_INCREMENT PRIMARY KEY,"
				+ "fecha DATE NOT NULL," + "id_producto INT," + "id_proveedor INT,"
				+ "FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE ON UPDATE CASCADE,"
				+ "FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE ON UPDATE CASCADE"
				+ ")";

		stmt.executeUpdate(createTableSQL);
		System.out.println("Tabla compra creada correctamente");

		stmt.close();
	}

	private static void CrearTablaClientes(Connection conexion) throws SQLException {
		Statement stmt = conexion.createStatement();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS Clientes (" + "id_cliente INT AUTO_INCREMENT PRIMARY KEY,"
				+ "nombre VARCHAR(30) NOT NULL," + "telefono INT," + "email VARCHAR(50)" + ")";

		stmt.executeUpdate(createTableSQL);
		System.out.println("Tabla clientes creada correctamente");

		stmt.close();
	}

	// metodo para añadir datos a tablas
	public static void AñadirTabla(Connection conexion, int opcion) throws SQLException {
		Statement stmt = conexion.createStatement();
		Scanner sc = new Scanner(System.in);

		switch (opcion) {
		case 1: // Insertar en Producto
			System.out.println("Introduce el nombre del producto:");
			String nombreProducto = sc.nextLine();

			System.out.println("Introduce el precio del producto (con 2 decimales):");
			Double precio = sc.nextDouble();

			System.out.println("Introduce el stock que tiene el producto:");
			int stock = sc.nextInt();

			String sqlProducto = "INSERT INTO Producto(nombre, precio, stock) VALUES ('" + nombreProducto + "', "
					+ precio + ", " + stock + ")";
			stmt.executeUpdate(sqlProducto);
			System.out.println("Producto añadido correctamente.");
			break;

		case 2: // Insertar en Proveedores
			System.out.println("Introduce el nombre del proveedor:");
			String nombreProveedor = sc.nextLine();

			System.out.println("Introduce el contacto del proveedor:");
			String contacto = sc.nextLine();

			System.out.println("Introduce la dirección del proveedor:");
			String direccion = sc.nextLine();

			String sqlProveedor = "INSERT INTO Proveedores(nombre, contacto, direccion) VALUES ('" + nombreProveedor
					+ "', '" + contacto + "', '" + direccion + "')";
			stmt.executeUpdate(sqlProveedor);
			System.out.println("Proveedor añadido correctamente.");
			break;

		case 3: // Insertar en Clientes
			System.out.println("Introduce el nombre del cliente:");
			String nombreCliente = sc.nextLine();

			System.out.println("Introduce el teléfono del cliente:");
			int telefono = sc.nextInt();

			System.out.println("Introduce el email del cliente:");
			String email = sc.nextLine();

			String sqlCliente = "INSERT INTO Clientes(nombre, telefono, email) VALUES ('" + nombreCliente + "', "
					+ telefono + ", '" + email + "')";
			stmt.executeUpdate(sqlCliente);
			System.out.println("Cliente añadido correctamente.");
			break;

		default:
			System.out.println("No se encontró la opción.");
		}

		stmt.close();
	}

	public static void mostrarTabla(Connection conexion, int opcion) throws SQLException {
		Statement stmt = conexion.createStatement();
		String query = "";

		switch (opcion) {
		case 1:
			query = "SELECT * FROM Producto";
			stmt.executeQuery(query);
			break;
		case 2:
			query = "SELECT * FROM Proveedores";
			stmt.executeQuery(query);
			break;
		case 3:
			query = "SELECT * FROM Clientes";
			stmt.executeQuery(query);
			break;
		case 4:
			mostrarCompras(conexion);
			break;
		case 5:
			mostrarVentas(conexion);
		default:
			System.out.println("Opción no válida.");
			stmt.close();
			return;
		}

		stmt.close();
	}
	
	public static void mostrarCompras(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();

	    // Query JOIN para obtener detalles de compras
	    String query = "SELECT c.id_compra, c.fecha, p.nombre AS producto, pr.nombre AS proveedor " +
	                   "FROM Comprar c " +
	                   "JOIN Producto p ON c.id_producto = p.id_producto " +
	                   "JOIN Proveedores pr ON c.id_proveedor = pr.id_proveedor";

	    ResultSet rs = stmt.executeQuery(query);
		for(int i = 0; i<10;i++) {
			System.out.println("");
		}
	    System.out.println("Detalles de Compras:");
	    System.out.println("ID Compra | Fecha       | Producto                 | Proveedor");
	    System.out.println("-----------------------------------------------------------------------------------------");

	    while (rs.next()) {
	        int idCompra = rs.getInt("id_compra");
	        String fecha = rs.getString("fecha");
	        String producto = rs.getString("producto");
	        String proveedor = rs.getString("proveedor");

	       
	        String fila = idCompra + "         | " + fecha + " | " + producto;
	        // Ajustar la longitud del nombre del producto para que la salida sea uniforme
	        if (producto.length() < 25) {
	            fila += " ".repeat(25 - producto.length());
	        }
	        fila += " | " + proveedor;
	        System.out.println(fila);
	    }
	    
	    System.out.println("\nPresiona Enter para continuar...");
	    Scanner sc = new Scanner(System.in);
	    sc.nextLine();
	    
	    rs.close();
	    stmt.close();
	}

	// Método para mostrar ventas con detalles de producto y cliente
	public static void mostrarVentas(Connection conexion) throws SQLException {
	    Statement stmt = conexion.createStatement();

	    // Query JOIN para obtener detalles de ventas
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

	public static void Comprar(Connection conexion) throws SQLException {
		Statement stmt = conexion.createStatement();
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el nombre del producto comprado:");
		String productoComprado = sc.nextLine();
		
		String buscarProductoCompra = "SELECT id_producto FROM Producto WHERE nombre = '" + productoComprado + "'";
		ResultSet rsProductoCompra = stmt.executeQuery(buscarProductoCompra);
		int idProductoCompra = rsProductoCompra.next() ? rsProductoCompra.getInt("id_producto") : -1;
		
		if (idProductoCompra == 1) {
			System.out.println("Producto no encontrado, introduce los datos: ");
			AñadirTabla(conexion, 1);
		}

		System.out.println("Introduce el nombre del proveedor:");
		String proveedorCompra = sc.nextLine();
		
		String buscarProveedor = "SELECT id_proveedor FROM Proveedores WHERE nombre = '" + proveedorCompra + "'";
		ResultSet rsProveedor = stmt.executeQuery(buscarProveedor);
		int idProveedor = rsProveedor.next() ? rsProveedor.getInt("id_proveedor") : -1;
		
		if (idProveedor == -1) {
			System.out.println("Provedor no encontrado, introduce los datos: ");
			AñadirTabla(conexion, 2);
		}
		System.out.println("Introduce la fecha de la compra (YYYY-MM-DD):");
		String fechaCompra = sc.nextLine();


		  if (idProductoCompra != -1 && idProveedor != -1) {
			String sqlComprar = "INSERT INTO Comprar (fecha, id_producto, id_proveedor) VALUES ('" + fechaCompra + "', "
					+ idProductoCompra + ", " + idProveedor + ")";
			String sqlStock = "UPDATE Producto SET stock = stock - 1 " + "WHERE nombre = '" + productoComprado
					+ "' AND stock > 0";

			stmt.executeUpdate(sqlStock);
			stmt.executeUpdate(sqlComprar);

			System.out.println("Compra registrada correctamente.");
		}
	}

	public static void Vender(Connection conexion) throws SQLException {
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

			System.out.println("Cliente no encontrado, ingrese datos de nuevo cliente:");
			AñadirTabla(conexion, 3);
		}
		
		System.out.println("Introduce la fecha de la venta (YYYY-MM-DD):");
		String fechaVenta = sc.nextLine();



		if (idProducto != -1 && idCliente != -1) {
			String sqlVender = "INSERT INTO Vender (fecha, id_producto, id_cliente) VALUES ('" + fechaVenta + "', "
					+ idProducto + ", " + idCliente + ")";
			String sqlStock = "UPDATE Producto SET stock = stock - 1 " + "WHERE nombre = '" + productoVendido
					+ "' AND stock > 0";
			stmt.executeUpdate(sqlVender);
			stmt.executeUpdate(sqlStock);
			System.out.println("Venta registrada correctamente.");
		} else {
			System.out.println("Error: Producto no encontrado.");
		}

	}
}