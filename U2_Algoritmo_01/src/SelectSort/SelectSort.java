package SelectSort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectSort {

	// ==========================================================================
	// S E L E C T I O N S O R T
	// ============================================================================
	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[index]) {
					index = j;
				}
			}
			int smallerNumber = arr[index];
			arr[index] = arr[i];
			arr[i] = smallerNumber;
		}
		archivo_escritura(arr, arr.length, "Select.txt");
	}

	// ==========================================================================
	// R E S U L T A D O
	// ============================================================================
	public static void printArray(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i) {
			System.out.println(arr[i]);
		}
		System.out.println();
		archivo_escritura(arr, arr.length, "Select.txt");
	}

	// ==========================================================================
	// L E C T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_lectura() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("entrada.txt");
		try (Scanner entrada = new Scanner(archivo)) {
			int m = entrada.nextInt();
			int a[] = new int[m];

			for (int i = 0; i < m; i++) {
				int x = entrada.nextInt();
				a[i] = x;
			}
			System.out.println("================================================");
			System.out.println("Selection Sort:");
			selectionSort(a);
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
	// G E N E R A R V A L O R E S A L E A T O R I O S
	// ============================================================================
	public static int random() {
		// Generar numeros random de 1 <= 10
		int numero = (int) (Math.random() * 100) + 1;
		return numero;
	}

	// ============================================================================
	// E S C R I T U R A D E L A R C H I V O - ENTRADA
	// ============================================================================
	public static void archivo_escritura(int n) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("entrada.txt");
			pw = new PrintWriter(fichero);
			pw.println(n);
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
	public static void archivo_escritura(int a[], int n, String txt) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(txt);
			pw = new PrintWriter(fichero);
			for (int i = 0; i < a.length; i++) {
				pw.println(a[i]);
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
		archivo_lectura();
		// ========================================
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion = time_end - time_start;
		// -----------------------------------------
		System.out.println("Tiempos de ejecucion: " + (int) (tiempo_ejecucion / 1e6) + "ms");
	}

}