package Permutaciones_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Permutaciones {

	static int c=0;
	static ArrayList<String> permutaciones;
	// ==========================================================================
	// P E R M U T A C I O N
	// ============================================================================
	public static void Permutaciones(int m)
	{
		m = 10;
		System.out.println("m: "+m);
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		permutaciones = new ArrayList<String>();
		for(int i=0;i<m;i++)
		{
			numeros.add(i+1);
		}
		// Generamos las n permutaciones
		combinaciones("",numeros);
		archivo_escritura(permutaciones,m);
	}

	public static void combinaciones(String a, ArrayList<Integer> numeros)
	{
		if (numeros.size()==1)
        {
			permutaciones.add(a+numeros.get(0));
			c++;
        }
        for (int i=0;i<numeros.size();i++)
        {
            Integer b = numeros.remove(i);
            combinaciones(a+b, numeros);
            numeros.add(i,b);
        }
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
		//archivo_escritura(arr,arr.length,"QuickSort.txt");
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
			System.out.println("================================================");
			System.out.println("Permutaciones:");
			Permutaciones(m);
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
	public static int random_n() {
		// Generar numeros random de 5 <= n <= 10000
		int numero = (int) (Math.random() * 10000) + 5;
		return numero;
	}
	
	public static int random_m() { 
		// Generar numeros random de 5 <= m <= 100
		int numero = (int) (Math.random() * 100) + 5;
		return numero;
	}

	// ============================================================================
	// E S C R I T U R A D E L A R C H I V O - ENTRADA
	// ============================================================================
	public static void archivo_escritura() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("entrada.txt");
			pw = new PrintWriter(fichero);
			pw.println(random_m()); // m

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
	public static void archivo_escritura(ArrayList<String> lista,int m) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("salida_permutaciones_v2.txt");
			pw = new PrintWriter(fichero);
			pw.println("m ="+m);
			
			for (int i = 0; i < lista.size(); i++) {
				pw.println(lista.get(i));
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
		archivo_escritura();
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