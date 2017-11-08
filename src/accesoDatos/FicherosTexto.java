package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos
	Deposito deposito;
	Dispensador dispensador;

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		try (BufferedReader br = new BufferedReader(new FileReader("Ficheros/datos/depositos.txt"))) {
			StringTokenizer st = null;
			String line;

			while ((line = br.readLine()) != null) {
				ArrayList<String> datos = new ArrayList<String>();
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					datos.add(String.valueOf(st.nextToken()));
				}

				deposito = new Deposito(datos.get(0),Integer.parseInt(datos.get(1)), Integer.parseInt(datos.get(2)));

				depositosCreados.put(Integer.parseInt(datos.get(1)),deposito);
			//	System.out.println(depositosCreados);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("Ficheros/datos/dispensadores.txt"))) {
			StringTokenizer st = null;
			String line;

			while ((line = br.readLine()) != null) {
				ArrayList<String> datos = new ArrayList<String>();
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					datos.add(String.valueOf(st.nextToken()));
				}

				dispensador = new Dispensador(datos.get(0),datos.get(1), Integer.parseInt(datos.get(2)), Integer.parseInt(datos.get(3)));

				dispensadoresCreados.put(datos.get(0),dispensador);
				//System.out.println(depositosCreados);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {


		FileWriter fw = null;
		try {
			fw = new FileWriter("Ficheros/datos/depositos.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw= new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
		    deposito = entry.getValue();
		    pw.println(deposito.getNombreMoneda()+";"+deposito.getValor()+";"+deposito.getCantidad());
		   
		    		
		}
		
		 pw.close();

		boolean todoOK = true;

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("Ficheros/datos/dispensadores.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw= new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
		    Dispensador dispensador = new Dispensador();
		    dispensador = entry.getValue();
		    			pw.println(entry.getKey()+";"+dispensador.getNombreProducto()+";"+dispensador.getPrecio()+";"+dispensador.getCantidad());
		}
		pw.close();

		
		boolean todoOK = true;

		return todoOK;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Dispensador getDispensador() {
		return dispensador;
	}

	public void setDispensador(Dispensador dispensador) {
		this.dispensador = dispensador;
	}

} // Fin de la clase