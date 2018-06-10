package Generador_Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.math.BigInteger;
import java.text.DecimalFormatSymbols;
import java.text.*;

public class main {

	// V A R I A B L E S:
	int n = 0;

	// ============================================================================
	// O P E R A C I O N
	// ============================================================================
	public static void algoritmo(int a[], int b[], int n) {
		
		System.out.println("-------------");
		System.out.println("Resultado: ");
		
		BigInteger r2=new BigInteger("1");
		BigInteger r1=new BigInteger("1");
		
		BigInteger resultado[] = new BigInteger[n];
		
		BigInteger y= new BigInteger("1");
		
		for (int i = 0; i < n; i++) {
			r1 = new BigInteger(""+a[i]);
			r2 = new BigInteger(""+b[i]);
			resultado[i] = r1.multiply(r2);
		}
		y = resultado[0];
		for (int i = 1; i < n; i++) {
			y=y.multiply(resultado[i]);
		}
		NumberFormat formatter= 
				new DecimalFormat("0.######E0",DecimalFormatSymbols.getInstance(Locale.ROOT));
		String str = formatter.format(y);
		System.out.println(str);
	
	}

	// ============================================================================
	// L E C T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_lectura() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("numeros_random.txt");
		try (Scanner entrada = new Scanner(archivo)) {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String l_n = br.readLine();
			int n = Integer.parseInt(l_n);
			int a[] = new int[n];
			int b[] = new int[n];
			int x = entrada.nextInt();
			int y = 0;
			for (int i = 0; i < n; i++) {
				x = entrada.nextInt();
				a[i] = x;
				y = entrada.nextInt();
				b[i] = y;
				// System.out.println(x+" - "+y);
			}
			algoritmo(a, b, n);
		} catch (Exception e) {
			//  
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
	// E S C R I T U R A D E L A R C H I V O
	// ============================================================================
	public static void archivo_escritura(int n) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("numeros_random.txt");
			pw = new PrintWriter(fichero);
			pw.println(n);
			for (int i = 0; i < n; i++) {
				pw.println(random() + " " + random());
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
		System.out.println("=====================");
		System.out.println("Ejercicio: n=1,000");
		archivo_escritura(1000);
		archivo_lectura();
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion[0] = time_end-time_start;
		// -----------------------------------------
		// Inicio del conteo del tiempo de ejecucion:
		time_start = System.nanoTime();
		// Ejecuta los metodos:
		System.out.println("=====================");
		System.out.println("Ejercicio: n=10,000");
		archivo_escritura(10000);
		archivo_lectura();
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion[1] = time_end-time_start;
		// -----------------------------------------
		// Inicio del conteo del tiempo de ejecucion:
		time_start = System.nanoTime();
		// Ejecuta los metodos:
		System.out.println("=====================");
		System.out.println("Ejercicio: n=100,000");
		archivo_escritura(100000);
		archivo_lectura();
		// Fin del conteo del tiempo de ejecucion:
		time_end = System.nanoTime();
		// Almacena el tiempo de ejecucion:
		tiempo_ejecucion[2] = time_end-time_start;
		System.out.println("=====================");
		System.out.println("    n   | Tiempos de ejecucion: ");
		System.out.println(" 1,000  | "+(int)(tiempo_ejecucion[0]/1e6)+"ms");
		System.out.println("10,000  | "+(int)(tiempo_ejecucion[1]/1e6)+"ms");
		System.out.println("100,000 | "+(int)(tiempo_ejecucion[2]/1e6)+"ms");
	}

}
