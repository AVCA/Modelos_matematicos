package Matriz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Matriz {

	// V A R I A B L E S:

	// ==========================================================================
	// L E C T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_lectura() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("matriz.txt");
		try (Scanner entrada = new Scanner(archivo)) {
			int m = entrada.nextInt();
			int n = entrada.nextInt();
			int p = entrada.nextInt();
			int a[][] = new int[m][n];
			// Matriz A
			System.out.println("==============");
			System.out.println("Matriz A:");
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					int x = entrada.nextInt();
					a[i][j] = x;
					System.out.print(a[i][j] + " ");
				}
				System.out.println("");
			}
			// Matriz B
			System.out.println("==============");
			System.out.println("Matriz B:");
			int b[][] = new int[n][p];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < p; j++) {
					int x = entrada.nextInt();
					b[i][j] = x;
					System.out.print(b[i][j] + " ");
				}
				System.out.println("");
			}
			multiplicacion(a, b);
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
	// M U L T I P L I C A C I O N
	// ============================================================================
	public static void multiplicacion(int a[][], int b[][]) {
		// Para multiplicar matrices ocupo que A tenga la misma cantidad
		// de columnas que B tiene de filas
		if ((a[0].length) == (b.length)) {
			int aux[][] = new int[a.length][b[0].length];
			for (int i = 0; i < aux.length; i++) {
				for (int j = 0; j < aux[0].length; j++) {
					int suma = 0;
					for (int k = 0; k < b.length; k++) {
						suma += a[i][k] * b[k][j];
					}
					aux[i][j] = suma;
				}
			}
			// Imprimir matriz de resultados:
			System.out.println("==============");
			System.out.println("Matriz C:");
			for (int i = 0; i < aux.length; i++) {
				for (int j = 0; j < aux[0].length; j++)
					System.out.print(aux[i][j] + " ");
				System.out.print("\n");
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
	// E S C R I T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_escritura(int m, int n, int p) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("matriz.txt");
			pw = new PrintWriter(fichero);
			pw.println(m);
			pw.println(n);
			pw.println(p);
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					pw.print(random() + " ");
				}
				pw.println(" ");
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < p; j++) {
					pw.print(random() + " ");
				}
				pw.println(" ");
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
		long tiempo_ejecucion[] = new long[3];

		// Variables para el reloj
		long time_start, time_end;

		// Inicio del programa:
		// -----------------------------------------
		// Inicio del conteo del tiempo de ejecucion:
		time_start = System.nanoTime();
		
		// Ejecuta los metodos:
		// ========================================
		// Creacion de las matrices:
		// Matriz A: m*n
		// Matriz B: n*p
		archivo_escritura(4, 3, 4);
		archivo_lectura();
		// ========================================
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion[0] = time_end-time_start;
		// -----------------------------------------
		System.out.println("=====================");
		System.out.println("    n   | Tiempos de ejecucion: ");
		System.out.println(" 1,000  | "+(int)(tiempo_ejecucion[0]/1e6)+"ms");
	}

}
