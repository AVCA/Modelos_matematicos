package TSP_Permutaciones;

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
	static double max_d = 0;
	static String max_p = "";
	static double min_d = 9999;
	static String min_p = "";
	static ArrayList<String> permutaciones;
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
		// System.out.println("============================");
		// System.out.println("Permutaciones:");
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		permutaciones = new ArrayList<String>();
		for (int i = 0; i < m; i++) {
			numeros.add(i + 1);
		}
		// Generamos las n permutaciones
		combinaciones("", numeros);
		for (int i = 0; i < permutaciones.size(); i++) {
			// System.out.println(permutaciones.get(i));
			distancia_euclidiana(permutaciones.get(i));
		}
		System.out.println("============================");
		System.out.println("Distancia maxima final: " + max_d);
		System.out.println("Permutacion: " + max_p);
		System.out.println("Distancia minima final: " + min_d);
		System.out.println("Permutacion: " + min_p);
		System.out.println("============================");
	}

	public static void combinaciones(String a, ArrayList<Integer> numeros) {
		if (numeros.size() == 1) {
			permutaciones.add(a + " " + numeros.get(0));
			c++;
		}
		for (int i = 0; i < numeros.size(); i++) {
			Integer b = numeros.remove(i);
			combinaciones(a + " " + b, numeros);
			numeros.add(i, b);
		}
	}

	//
	public static void nodos(String permutacion, ArrayList<Integer> nodos) {
		String[] nodo = permutacion.split(" ");
		for (int i = 1; i < nodo.length; i++) {
			nodos.add(Integer.parseInt(nodo[i]));
		}
	}

	// ==========================================================================
	// D I S T A N C I A E U C L A D I A N A
	// ============================================================================

	public static void distancia_euclidiana(String permutacion) {
		ArrayList<Integer> nodos = new ArrayList<Integer>();
		nodos(permutacion, nodos);
		// System.out.println("==============================================");
		Double distancia = 0.0, distancia_total = 0.0;
		int j = 1;
		int nodo1 = 0;
		int nodo2 = 0;
		for (int i = 0; i < grafo.size(); i++) {
			if (j < grafo.size()) {
				nodo1 = nodos.get(i) - 1;
				nodo2 = nodos.get(j) - 1;
				distancia = Math.sqrt(Math.pow(grafo.get(nodo1).get(0) - grafo.get(nodo2).get(0), 2)
						+ Math.pow(grafo.get(nodo1).get(1) - grafo.get(nodo2).get(1), 2));
				// System.out.println("Distancia del nodo " + (nodo1+1) + " al nodo " +
				// (nodo2+1) + ": " + distancia);
				distancia_total += distancia;
				j++;
			}
		}
		distancia = Math.sqrt(Math.pow(grafo.get(nodo2).get(0) - grafo.get(0).get(0), 2)
				+ Math.pow(grafo.get(nodo2).get(1) - grafo.get(0).get(1), 2));
		// System.out.println("Distancia del nodo " + (nodo2+1) + " al nodo " + (1) + ":
		// " + distancia);
		distancia_total += distancia;
		// System.out.println("----------------------------------------------");
		// System.out.println("Distancia total: " + distancia_total);
		if (distancia_total > max_d) {
			// System.out.println("----------------------------------------------");
			max_d = distancia_total;
			max_p = permutacion;
			// System.out.println("Distancia maxima actual: " + max_d);
			// System.out.println("Permutacion: " + max_p);
		} else {
			if (distancia_total < min_d) {
				// System.out.println("----------------------------------------------");
				min_d = distancia_total;
				min_p = permutacion;
				// System.out.println("Distancia minima actual: " + min_d);
				// System.out.println("Permutacion: " + min_p);
			}
		}
		// System.out.println("==============================================");
	}

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
