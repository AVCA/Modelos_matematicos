package TSP_Poblacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class TSP {

	// Variables:
	static int c = 0, p = 0;
	static double max_d = 0;
	static String max_p = "";
	static double min_d = 9999;
	static String min_p = "";
	static ArrayList<Ruta> poblacion;
	static ArrayList<Ruta> padres;
	static ArrayList<Ruta> hijos;
	static ArrayList<ArrayList<Double>> grafo;

	// Algoritmo:
	// 0) Inicio
	// 1) Lectura del archivo tsp
	// 2) Generar (aleatoriamente) una poblacion inicial.
	// 3) Calcular aptitud de cada individuo.
	// 4) Seleccionar (probabilısticamente) en base a aptitud.
	// 5) Aplicar operadores geneticos (cruza y mutacion)

	// ============================================================================
	// 1) Lectura del archivo tsp
	// ============================================================================
	public static void archivo_lectura() {
		// Variables necesarias para la lectura del TSP:
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		// Lista que contendra las coordenadas de los nodos
		ArrayList<Double> nodo;
		// Lista que contendra los nodos con sus respectivas nodo
		grafo = new ArrayList<ArrayList<Double>>();
		// Archivo TSP que se leera
		archivo = new File("ulysses16.tsp");

		try (Scanner entrada = new Scanner(archivo)) {
			String linea = entrada.next();
			while (!(linea.matches("NODE_COORD_SECTION.*"))) {
				linea = entrada.next();
			}
			linea = entrada.next();
			while (!(linea.matches("EOF"))) {
				// Obtenemos las coordenadas por nodo
				nodo = new ArrayList<Double>();
				linea = entrada.next();
				nodo.add(Double.parseDouble(linea));
				linea = entrada.next();
				nodo.add(Double.parseDouble(linea));
				linea = entrada.next();
				// Se almacena el nodo
				grafo.add(nodo);
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
	// 2) Generar aleatoriamente poblacion inicial
	// ============================================================================
	public static void Poblacion(int m, int n) {
		System.out.println("============================");
		System.out.println("Generacion de la poblacion: " + p);
		// Generamos una lista de numeros que
		// seran los indices de los nodos
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < m; i++) {
			numeros.add(i + 1);
		}
		// Generamos la poblacion inicial o padres
		poblacion = new ArrayList<Ruta>();
		for (int i = 0; i < n; i++) {
			permutaciones("", numeros, 1);
			c = 0;
		}
		// Calcular aptitud de cada individuo.
		for (int i = 0; i < poblacion.size(); i++) {
			distancia_euclidiana(poblacion.get(i).getPermutacion(), i,poblacion);
			// System.out.println((i+1) + " ["+poblacion.get(i) + " ]");
		}
		//
		System.out.println("========================================================");
		padres = new ArrayList<Ruta>();
		seleccion_Torneo();
		hijos = new ArrayList<Ruta>();
		int x=0,y=x+1;
		int j = (padres.size() / 2) - 1;
		for (int i = 0; i < padres.size(); i++) {
			if (i >= (padres.size() / 2))
				break;
			System.out.println("Padres: "+i);
			cruza(i,j,x,y);
			x=x+2;y=x+1;
			j++;
		}
		System.out.println("========================================================");
		p++;
	}

	public static void permutaciones(String a, ArrayList<Integer> numeros, int n) {
		// Revuelve de manera aleatoria los nodos
		Collections.shuffle(numeros);
		if (numeros.size() == 1) {
			poblacion.add(new Ruta(a + " " + numeros.get(0), 0));
			c++;
		}
		for (int i = 0; i < numeros.size(); i++) {
			Integer b = numeros.remove(i);
			if (c < n)
				permutaciones(a + " " + b, numeros, n);
			numeros.add(i, b);
		}
	}

	// ============================================================================
	// 3) Calcular aptitud de cada individuo. DISTANCIA EUCLIDIANDA
	// ============================================================================
	public static void nodos(String permutacion, ArrayList<Integer> nodos) {
		String[] nodo = permutacion.split(" ");
		for (int i = 1; i < nodo.length; i++) {
			nodos.add(Integer.parseInt(nodo[i]));
		}
	}

	public static void distancia_euclidiana(String permutacion, int k, ArrayList<Ruta> poblacion) {
		ArrayList<Integer> nodos = new ArrayList<Integer>();
		nodos(permutacion, nodos);
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
				distancia_total += distancia;
				j++;
			}
		}
		distancia = Math.sqrt(Math.pow(grafo.get(nodo2).get(0) - grafo.get(0).get(0), 2)
				+ Math.pow(grafo.get(nodo2).get(1) - grafo.get(0).get(1), 2));
		distancia_total += distancia;
		poblacion.get(k).setDistancia(distancia_total);
		if (distancia_total > max_d) {
			max_d = distancia_total;
			max_p = permutacion;
		} else {
			if (distancia_total < min_d) {
				min_d = distancia_total;
				min_p = permutacion;
			}
		}
	}

	// ============================================================================
	// 4) Seleccionar (probabilısticamente) en base a aptitud. TORNEO
	// ============================================================================
	public static void seleccion_Torneo() {
		// Seleccion del primer nodo:
		// 1. Barajea los nodos:
		// 2. Toma una pareja de nodos y compara su distancia.
		comparacion_pareja();
		// Seleccion del segundo nodo:
		// 4. Repite el paso 1.
		comparacion_pareja();
		// 5. Resultado: Lista con los nodos con menor distancia
	}

	public static void comparacion_pareja() {
		Collections.shuffle(poblacion);
		int j = 0;
		for (int i = 0; i < poblacion.size(); i++) {
			j++;
			// 3. Almacena el nodo con menor distancia.
			if (poblacion.get(i).getDistancia() < poblacion.get(j).getDistancia()) {
				padres.add(poblacion.get(i));
			} else {
				padres.add(poblacion.get(j));
			}
			i++;
			j++;
		}
	}

	// ============================================================================
	// 5) Aplicar operadores geneticos (cruza y mutacion). CRUZA: OX | MUTACION:
	// ============================================================================
	public static void cruza(int i, int j, int x, int y) {

		System.out.println("P1:    " + " [" + padres.get(i) + " ]");
		System.out.println("P2:    " + " [" + padres.get(j) + " ]");

		ArrayList<Integer> P1 = new ArrayList<Integer>();
		nodos(padres.get(i).getPermutacion(), P1);
		ArrayList<Integer> P2 = new ArrayList<Integer>();
		nodos(padres.get(j).getPermutacion(), P2);
		ArrayList<Integer> Hijo = new ArrayList<Integer>();

		// Generacion del Hijo 1:
		// 1. Seleccionar (aleatoriamente) una sub-cadena P1.
		ArrayList<Integer> subcadena1 = subcadena(P1, 4, i);
		// System.out.println("Subcadena de P1: " + subcadena);
		// 2. Producir un hijo copiando la sub-cadena en las posiciones correspondientes
		// a P1. Las posiciones restantes se dejan en blanco.
		hijo_P1(subcadena1, P1);
		// 3. Borrar los valores que ya se encuentren en la sub-cadena de P2. La
		// secuencia
		// resultante contiene los valores faltantes.
		vaciar_P2(subcadena1, P2);
		// 4. Colocar los valores en posiciones no conocidas del hijo de izquierda a
		// derecha.
		completar_hijo(subcadena1, P1, P2, Hijo);
		//System.out.println("Hijo1: " + Hijo);
		String permutacion = " ";
		for (int k = 0; k < Hijo.size(); k++) {
			permutacion = permutacion + Hijo.get(k) + " ";
		}
		hijos.add(new Ruta(permutacion, 0));
		// 5. Para obtener el segundo hijo, se repiten los pasos del 1 al 4, pero
		// tomando
		// ahora la sub-cadena de P2.
		P1.clear();
		P2.clear();
		Hijo.clear();
		nodos(padres.get(i).getPermutacion(), P1);
		nodos(padres.get(j).getPermutacion(), P2);
		ArrayList<Integer> subcadena2 = subcadena(P2, 4, i);
		hijo_P1(subcadena2, P2);
		vaciar_P2(subcadena2, P1);
		completar_hijo(subcadena2, P2, P1, Hijo);
		//System.out.println("Hijo2: " + Hijo);
		permutacion = " ";
		for (int k = 0; k < Hijo.size(); k++) {
			permutacion = permutacion + Hijo.get(k) + " ";
		}
		hijos.add(new Ruta(permutacion, 0));
		//System.out.println(x+"-"+y);
		distancia_euclidiana(hijos.get(x).getPermutacion(), x, hijos);
		distancia_euclidiana(hijos.get(y).getPermutacion(), y, hijos);
		System.out.println("Hijo1: " + " [" + hijos.get(x) + " ]");
		System.out.println("Hijo2: " + " [" + hijos.get(y) + " ]");
		System.out.println("---------------------------------------------");

	}

	public static void completar_hijo(ArrayList<Integer> subcadena, ArrayList<Integer> P1, ArrayList<Integer> P2,
			ArrayList<Integer> Hijo) {
		int j = 0, s = 0;
		for (int i = 0; i < P1.size(); i++) {
			if (!subcadena.contains(P1.get(i))) {
				Hijo.add(P2.get(j));
				j++;
			} else {
				Hijo.add(subcadena.get(s));
				s++;
			}
		}
	}

	public static void vaciar_P2(ArrayList<Integer> subcadena, ArrayList<Integer> P2) {
		// Eliminamos de P2 todos los nodos que coincidan con la sub-cadena
		for (int i = 0; i < subcadena.size(); i++) {
			if (P2.contains(subcadena.get(i))) {
				P2.remove(subcadena.get(i));
			}
		}
		// System.out.println("P2 sin sub-cadena: "+P2);
	}

	public static void hijo_P1(ArrayList<Integer> subcadena, ArrayList<Integer> P1) {
		ArrayList<Integer> aux = new ArrayList<Integer>();
		// System.out.print("Hijo [ ");
		for (int k = 0; k < P1.size(); k++) {
			if (!(subcadena.contains(P1.get(k)))) {
				// Se deja "vacio" el espacion que no ocupamos aun
				// System.out.print("x ");
			} else {
				// Conservamos el nodo que coincide con la sub-cadena
				// System.out.print(P1.get(k) + " ");
				aux.add(P1.get(k));
			}
		}
		// System.out.print("]");
		// System.out.println();
		subcadena.clear();
		for (int i = 0; i < aux.size(); i++) {
			subcadena.add(aux.get(i));
		}
	}

	public static ArrayList<Integer> subcadena(ArrayList<Integer> Padre, int n, int i) {
		ArrayList<Integer> subcadena = new ArrayList<Integer>();
		// Obtenemos n indices aleatorios que deseemos para formar la subcadena
		for (int j = 0; j < n; j++) {
			int r = random();
			// Verificamos el que indice obtenido no
			// ha sido seleccionando anteriormente
			if (subcadena.contains(Padre.get(r))) {
				while (subcadena.contains(Padre.get(r)))
					// Generamos un nuevo indice
					r = random();
			}
			// Almacenamos el nodo a la subcadena formada
			subcadena.add(Padre.get(r));
		}
		return subcadena;
	}

	public static int random() {
		// Generar numeros random de 1 <= n <= 10
		int numero = (int) (Math.random() * 9) + 1;
		return numero;
	}

	// ============================================================================
	// 0) M A I N
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
		Poblacion(grafo.size(), 100);
		// Calcular distancias de las padres
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
