package TSP_Lector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TSP {

	// Variables:
	static int c = 0;
	static ArrayList<Double> distancias;
	static ArrayList<ArrayList<Double>> grafo;

	// ==========================================================================
	// L E C T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_lectura() {
		// Variables:
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<Double> coordenadas;
		grafo = new ArrayList<ArrayList<Double>>();
		archivo = new File("ulysses16.tsp");

		try (Scanner entrada = new Scanner(archivo)) {
			String linea = entrada.next();
			while (!(linea.matches("NODE_COORD_SECTION.*"))) {
				linea = entrada.next();
			}
			linea = entrada.next();
			while (!(linea.matches("EOF"))) {
				coordenadas = new ArrayList<Double>();
				linea = entrada.next();
				coordenadas.add(Double.parseDouble(linea));
				linea = entrada.next();
				coordenadas.add(Double.parseDouble(linea));
				linea = entrada.next();
				grafo.add(coordenadas);
			}
			System.out.println("============================");
			System.out.println("Grafo:");
			for (int i = 0; i < grafo.size(); i++) {
				System.out.println("Nodo " + (i + 1) + ": " + grafo.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	// ============================================================================
	// P E R M U T A C I O N
	// ============================================================================
	public static void Permutaciones(int m) {
		System.out.println("============================");
		System.out.println("Permutaciones:");
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < m; i++) {
			numeros.add(i + 1);
		}
		// Generamos las n permutaciones
		combinaciones("", numeros,0);
	}

	public static void combinaciones(String a, ArrayList<Integer> numeros, int n) {
		if (numeros.size() == 1) {
			System.out.println(a +" "+ numeros.get(0));
			c++;
		}
		for (int i = 0; i < numeros.size(); i++) {
			Integer b = numeros.remove(i);
			if (c < n) {
				combinaciones(a +" "+ b, numeros, n);
			}
			numeros.add(i, b);
		}
	}

	// ==========================================================================
	// D I S T A N C I A E U C L A D I A N A
	// ============================================================================
	/*
	 * public static void distancia_euclidiana() {
	 * System.out.println("=============================================="); Double
	 * distancia = 0.0; int j = 1; for (int i = 0; i < grafo.size(); i++) { if (j <
	 * grafo.size()) { distancia += Math.sqrt( Math.pow(grafo.get(i).x -
	 * grafo.get(j).x, 2) + Math.pow(grafo.get(i).y - grafo.get(j).y, 2));
	 * System.out.println("Distancia del nodo " + (i + 1) + " al  nodo " + (j + 1) +
	 * ": " + distancia); j++; } } distancia +=
	 * Math.sqrt(Math.pow(grafo.get(grafo.size() - 1).x - grafo.get(0).x, 2) +
	 * Math.pow(grafo.get(grafo.size() - 1).y - grafo.get(0).y, 2));
	 * System.out.println("Distancia del nodo " + (grafo.size()) + " al  nodo " +
	 * (0) + ": " + distancia); }
	 */

	// ============================================================================
	// M A I N
	// ============================================================================
	public static void main(String[] args) {
		// Arreglo para almacenar los tiempos de ejecucion
		long tiempo_ejecucion;

		// Variables para el reloj
		long time_start, time_end;

		// -----------------------------------------
		// Inicio del conteo del tiempo de ejecucion:
		time_start = System.nanoTime();

		// Ejecuta los metodos:
		// ========================================
		archivo_lectura();
		Permutaciones(grafo.size());
		// Calcular distancias de las permutaciones
		// distancia_euclidiana();
		// ========================================
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion = time_end - time_start;
		// -----------------------------------------
		System.out.println("Tiempos de ejecucion: " + (int) (tiempo_ejecucion / 1e6) + "ms");

	}

}
