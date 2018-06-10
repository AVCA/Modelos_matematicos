package Busqueda_generalizada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BusquedaSec {

	// V A R I A B L E S:

	// ==========================================================================
	// L E C T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_lectura_g() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("entrada.txt");
		try (Scanner entrada = new Scanner(archivo)) {
			int m = entrada.nextInt();
			int k = entrada.nextInt();
			int a[] = new int[m];

			for (int i = 0; i < m; i++) {
				int x = entrada.nextInt();
				a[i] = x;
			}
			System.out.println("================================================");
			System.out.println("Busqueda generalizada:");
			busqueda_g(a, m, k);
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

	public static void archivo_lectura_s() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("entrada.txt");
		try (Scanner entrada = new Scanner(archivo)) {
			int m = entrada.nextInt();
			int k = entrada.nextInt();
			int a[] = new int[m];

			for (int i = 0; i < m; i++) {
				int x = entrada.nextInt();
				a[i] = x;
			}
			System.out.println("================================================");
			System.out.println("Busqueda secuencial:");
			ArrayList<Integer> p = new ArrayList<Integer>();
			busqueda_s(a, p, m, k, 0);
			//System.out.println("Valor buscado:" + k);
			//System.out.println("Posicion encontrada: " + p);
			archivo_escritura(p, k,"posiciones_s.txt");
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
	// RUTINA GENERALIZADA DE BUSQUEDA
	// ============================================================================
	public static void busqueda_g(int a[], int n, int k) {
		ArrayList<Integer> posicion = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (a[i] == k) {
				posicion.add(i);
			}
		}
		archivo_escritura(posicion, k,"posiciones_g.txt");
	}

	// ============================================================================
	// RUTINA BUSQUEDA SECUENCIAL
	// ============================================================================
	public static void busqueda_s(int[] a, ArrayList<Integer> posicion, int n, int k, int i) {
		if (i >= n)
			System.out.println(-1);
		else if (a[i] == k) {
			posicion.add(i);
			i = i + 1;
			busqueda_s(a, posicion, n, k, i);
		} else {
			i = i + 1;
			busqueda_s(a, posicion, n, k, i);
		}
	}

	// ============================================================================
	// G E N E R A R V A L O R E S A L E A T O R I O S
	// ============================================================================
	public static int random() {
		// Generar numeros random de 1 <= 10
		int numero = (int) (Math.random() * 10) + 1;
		return numero;
	}

	// ============================================================================
	// E S C R I T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_escritura(int n) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("entrada.txt");
			pw = new PrintWriter(fichero);
			pw.println(n);
			pw.println(random());
			for (int i = 0; i < n; i++) {
				pw.println(random());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// ============================================================================
	// E S C R I T U R A D E L A R C H I V O - SALIDA
	// ============================================================================
	public static void archivo_escritura(ArrayList<Integer> r, int k) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("salida_g.txt");
			pw = new PrintWriter(fichero);
			pw.println("Valor buscado: " + k);
			pw.println("Posiciones: ");
			for (int i = 0; i < r.size(); i++) {
				pw.println(r.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void archivo_escritura(ArrayList<Integer> r, int k, String txt) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(txt);
			pw = new PrintWriter(fichero);
			pw.println("Valor buscado: " + k);
			pw.println("Posiciones: ");
			for (int i = 0; i < r.size(); i++) {
				pw.println(r.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// ============================================================================
	// M A I N
	// ============================================================================
	public static void main(String[] args) {

		// Arreglo para almacenar los tiempos de ejecucion
		long tiempo_ejecucion;

		// Variables para el reloj
		long time_start, time_end;

		// Inicio del programa:
		archivo_escritura(1000000);
		// -----------------------------------------
		// Inicio del conteo del tiempo de ejecucion:
		time_start = System.nanoTime();

		// Ejecuta los metodos:
		// ========================================
		archivo_lectura_g();
		// ========================================
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion = time_end - time_start;
		// -----------------------------------------
		System.out.println("Tiempos de ejecucion: " + (int) (tiempo_ejecucion / 1e6) + "ms");
		// Ejecuta los metodos:
		// ========================================
		archivo_lectura_s();
		// ========================================
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion = time_end - time_start;
		System.out.println("Tiempos de ejecucion: " + (int) (tiempo_ejecucion / 1e6) + "ms");
	}

}