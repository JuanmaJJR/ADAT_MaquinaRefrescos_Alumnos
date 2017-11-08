package accesoDatos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC   {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;
	private Deposito deposito;
	private Dispensador dispensador;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		// this.setPrincipal(principal);

		String query = "SELECT * FROM `depositos`";
		try {

			if (conn1 != null) {

				Statement stmt = conn1.createStatement();
				ResultSet rset = stmt.executeQuery(query);

				while (rset.next()) {
					deposito = new Deposito(rset.getString(2), rset.getInt(3), rset.getInt(4));
					depositosCreados.put(rset.getInt(3), deposito);
				}
				rset.close();
				stmt.close();

			} else {
				System.out.println("NULO");
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return depositosCreados;

	}

	
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		// this.setPrincipal(principal);

		String query = "SELECT * FROM `dispensadores`";
		try {

			if (conn1 != null) {

				Statement stmt = conn1.createStatement();
				ResultSet rset = stmt.executeQuery(query);

				while (rset.next()) {
					dispensador = new Dispensador(rset.getString(2), rset.getString(3), rset.getInt(4), rset.getInt(5));
					// System.out.println(dispensador);
					dispensadoresCreados.put(rset.getString(2), dispensador);
				}
				rset.close();
				stmt.close();

			} else {
				System.out.println("NULO");
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return dispensadoresCreados;
	}

	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		String query = "";
		try {

			if (conn1 != null) {

				Statement stmt = conn1.createStatement();

				for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
					deposito = entry.getValue();
					query = "UPDATE `depositos` SET `cantidad` = '" + deposito.getCantidad()
							+ "' WHERE `depositos`.`nombre` = '" + deposito.getNombreMoneda() + "';";
					int rset = stmt.executeUpdate(query);
				}
				stmt.close();

			} else {
				System.out.println("NULO");
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		boolean todoOK = true;

		return todoOK;
	}

	
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		String query = "";
		try {

			if (conn1 != null) {

				Statement stmt = conn1.createStatement();

				for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
					dispensador = entry.getValue();
					query = "UPDATE `dispensadores` SET `cantidad` = '" + dispensador.getCantidad()
							+ "' WHERE `dispensadores`.`nombre`='" + dispensador.getNombreProducto() + "';";
					int rset = stmt.executeUpdate(query);
				}
				stmt.close();

			} else {
				System.out.println("NULO");
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		boolean todoOK = true;

		return todoOK;
	}

} // Fin de la clase