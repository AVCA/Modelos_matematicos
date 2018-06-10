package Busqueda_secuencial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class BusquedaSec {

	// V A R I A B L E S:

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
				int k = entrada.nextInt();
				
				int a[] = new int[m];

				for (int i = 0; i < m; i++) {
						int x = entrada.nextInt();
						a[i] = x;
				}
				busqueda(a,0,m,k);
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

		// Modificacion 
		
		// ============================================================================
		// RUTINA GENERALIZADA DE BUSQUEDA
		// ============================================================================
		public static void busqueda(int[] a,int posicion, int n, int k) {
			if(posicion>n)
				System.out.println(-1);
			else
				if(a[posicion]==k) {
					System.out.println("Valor buscado:"+k);
					System.out.println("Posicion encontrada: "+posicion);}
				else
					busqueda(a, ++posicion, n, k);
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
		// M A I N
		// ============================================================================
		public static void main(String[] args) {

			// Arreglo para almacenar los tiempos de ejecucion
			long tiempo_ejecucion;

			// Variables para el reloj
			long time_start, time_end;

			// Inicio del programa:
			// -----------------------------------------
			// Inicio del conteo del tiempo de ejecucion:
			time_start = System.nanoTime();
			
			// Ejecuta los metodos:
			// ========================================
			archivo_escritura(15);
			archivo_lectura();
			// ========================================
			// Fin del conteo del tiempo de ejecucion:
			time_end = System.nanoTime();
			// Almacena el tiempo de ejecucion:
			tiempo_ejecucion = time_end-time_start;
			// -----------------------------------------
			System.out.println("=====================");
			System.out.println("Tiempos de ejecucion: " +(int)(tiempo_ejecucion/1e6)+"ms");
		}

	}