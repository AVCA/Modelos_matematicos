package QuickSort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class QuickSort {

	// ==========================================================================
	// Q U I C K S O R T
	// ============================================================================
	public static void quicksort(int a[], int n) {
		// Valores aleatorias a[]
		sort(a, 0, n - 1);
		archivo_escritura(a,a.length,"QuickSort.txt");
	}

	// ==========================================================================
	// O R D E N A M I E N T O
	// ============================================================================
	public static void sort(int a[], int low, int high) {
		if (low < high) {
			/*
			 * pi is partitioning index, arr[pi] is now at right place
			 */
			int pi = partition(a, low, high);

			// Recursively sort elements before
			// partition and after partition
			sort(a, low, pi - 1);
			sort(a, pi + 1, high);
		}
	}

	// ==========================================================================
	// O R D E N A M I E N T O
	// ============================================================================
	public static int partition(int arr[], int low, int high) {
		int pivot = arr[high];
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than or
			// equal to pivot
			if (arr[j] <= pivot) {
				i++;

				// swap arr[i] and arr[j]
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
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
		archivo_escritura(arr,arr.length,"QuickSort.txt");
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
			System.out.println("QuickSort:");
			quicksort(a, a.length);
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
		// Generar numeros random de 1 <= 100
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