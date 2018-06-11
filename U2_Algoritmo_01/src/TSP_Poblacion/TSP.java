package TSP_Poblacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
	static ArrayList<Ruta> mejores;
	static Ruta mejor;
	static ArrayList<ArrayList<Double>> grafo;

	static int generaciones = 50;

	// Algoritmo:
	// 0) Inicio
	// 1) Lectura del archivo tsp
	// 2) Generar (aleatoriamente) una poblacion inicial.
	// 3) Calcular aptitud de cada individuo.
	// 4) Seleccionar (probabilısticamente) en base a aptitud.
	// 5) Aplicar operadores geneticos (cruza y mutacion)
	// 6) Ordenamos la poblacion total segun su aptitud
	// 7) Seleccionamos los 100 menores
	// 8) Almacenamos el "mejor" de la generacion
	// 9) Obtenemos el "mejor" de los "mejores"
	// 10) Repetimos el proceso 50 veces
	// 11) Fin

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
		// archivo = new File("ch130.tsp");
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
			informacion();
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
	public static void informacion() {
		System.out.println("============================");
		System.out.println("Grafo:");
		for (int i = 0; i < grafo.size(); i++) {
			System.out.println("Nodo " + (i + 1) + ": " + grafo.get(i));
		}
	}
	// ============================================================================
	// 2) Generar aleatoriamente poblacion inicial
	// ============================================================================
	public static void Poblacion(int m, int n) {
		System.out.println("========================================================");
		System.out.println("Generacion de la poblacion inicial: " + p);
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
		// 3) Calcular aptitud de cada individuo.
		for (int i = 0; i < poblacion.size(); i++) {
			distancia_euclidiana(poblacion.get(i).getPermutacion(), i, poblacion);
			System.out.println((i+1) +" "+ poblacion.get(i));
		}
		// 4) Seleccionar (probabilısticamente) en base a aptitud.
		padres = new ArrayList<Ruta>();
		seleccion_Torneo();
		hijos = new ArrayList<Ruta>();
		cruza();
		// 6) Ordenamos la poblacion total segun su aptitud
		ordenamiento(poblacion);
		// 7) Seleccionamos los 100 menores
		mejores = new ArrayList<Ruta>();
		nueva_Poblacion(100);
		// 9) Obtenemos el "mejor" de los "mejores"
		mejor = mejores.get(0);
		// System.out.println("Mejor actualmente: ");
		// System.out.println(mejor);

		// 10) Repetimos el proceso 50 veces
		p++;
		for (int i = 1; i < generaciones; i++) {
		    System.out.println("========================================================");
			System.out.println("Generacion de la poblacion: " + p);
			System.out.println("========================================================");
			padres.clear();
			hijos.clear();
			Collections.shuffle(poblacion);
			seleccion_Torneo();
			cruza();
			ordenamiento(poblacion);
			nueva_Poblacion(100);
			if (poblacion.get(0).getDistancia() < mejor.getDistancia()) {
				mejor = poblacion.get(0);
			}
			// System.out.println("Mejor actualmente: ");
			// System.out.println(mejor);
			p++;
		}
		informacion();
		System.out.println("========================================================");
		System.out.println("Mejores Rutas: ");
		// ordenamiento(mejores);
		for (int i = 0; i < mejores.size(); i++) {
			System.out.println(i + " " + mejores.get(i));
		}
		System.out.println("============================================================");
		System.out.println("Mejor Ruta: ");
		System.out.println(mejor);
		System.out.println("============================================================");
		// 11) Fin

	}

	public static void nueva_Poblacion(int max) {
		// resumen();
		ArrayList<Ruta> aux = new ArrayList<Ruta>();
		for (int i = 0; i < poblacion.size(); i++) {
			aux.add(new Ruta(poblacion.get(i).getPermutacion(), poblacion.get(i).getDistancia()));
		}
		poblacion.clear();
		for (int i = 0; i < max; i++) {
			poblacion.add(new Ruta(aux.get(i).getPermutacion(), aux.get(i).getDistancia()));
		}
		// resumen();
		// 8) Almacenamos el "mejor" de la generacion
		System.out.println("/////////////////////////////////////////////////////////");
		System.out.println("Mejor por generacion: ");
		int i = 0;
		while (mejores.contains(poblacion.get(i))) {
			i++;
		}
		System.out.println(poblacion.get(i));
		System.out.println("/////////////////////////////////////////////////////////");
		mejores.add(poblacion.get(i));
	}

	public static void resumen() {
		System.out.println("Poblacion generada ya ordenada:");
		System.out.println(poblacion.get(0));
		System.out.println(poblacion.get(1));
		System.out.println(poblacion.get(2));
		System.out.println("---");
		System.out.println(poblacion.get(97));
		System.out.println(poblacion.get(98));
		System.out.println(poblacion.get(99));
	}

	public static void ordenamiento(ArrayList<Ruta> poblacion) {
		Collections.sort(poblacion, new Comparator<Ruta>() {
			@Override
			public int compare(Ruta c1, Ruta c2) {
				return Double.compare(c1.getDistancia(), c2.getDistancia());
			}
		});
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
	public static void cruza() {
		System.out.println("---------------------------------------------");
		int x = 0, y = x + 1;
		int j = (padres.size() / 2) - 1;
		for (int i = 0; i < padres.size(); i++) {
			if (i >= (padres.size() / 2))
				break;
			// System.out.println("Padres: "+i);
			// 5) Aplicar operadores geneticos (cruza y mutacion)
			cruza_OX(i, j, x, y);
			x = x + 2;
			y = x + 1;
			j++;
		}
	}

	public static void cruza_OX(int i, int j, int x, int y) {

		System.out.println("P1: " + " [" + padres.get(i) + " ]");
		System.out.println("P2: " + " [" + padres.get(j) + " ]");

		ArrayList<Integer> P1 = new ArrayList<Integer>();
		nodos(padres.get(i).getPermutacion(), P1);
		ArrayList<Integer> P2 = new ArrayList<Integer>();
		nodos(padres.get(j).getPermutacion(), P2);
		ArrayList<Integer> Hijo = new ArrayList<Integer>();

		// Generacion del Hijo 1:
		// 1. Seleccionar (aleatoriamente) una sub-cadena P1.
		ArrayList<Integer> subcadena1 = subcadena(P1, 4, i);
		// System.out.println("Subcadena de P1: " + subcadena1);
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
		// System.out.println("Hijo1: " + Hijo);
		String permutacion = " ";
		for (int k = 0; k < Hijo.size() - 1; k++) {
			permutacion = permutacion + Hijo.get(k) + " ";
		}
		permutacion = permutacion + Hijo.get(Hijo.size() - 1);
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
		// System.out.println("Hijo2: " + Hijo);
		permutacion = " ";
		for (int k = 0; k < Hijo.size() - 1; k++) {
			permutacion = permutacion + Hijo.get(k) + " ";
		}
		permutacion = permutacion + Hijo.get(Hijo.size() - 1);
		hijos.add(new Ruta(permutacion, 0));
		// Extra: Mutacion - Mutacion por Intercambio Reciproco
		mutacion(x);
		mutacion(y);
		distancia_euclidiana(hijos.get(x).getPermutacion(), x, hijos);
		distancia_euclidiana(hijos.get(y).getPermutacion(), y, hijos);
		System.out.println("Hijo1: " + " [" + hijos.get(x) + " ]");
		System.out.println("Hijo2: " + " [" + hijos.get(y) + " ]");
		poblacion.add(hijos.get(x));
		poblacion.add(hijos.get(y));
		System.out.println("---------------------------------------------");
	}

	public static void mutacion(int i) {
		// Probabilidad de que mute: 10%
		int r = random_probabilidad();
		// 1 es igual a 10%
		if (r <= 1) {
			// Mutacion por intercambio reciproco
			System.out.println("/////////////////////////////////////");
			System.out.println("Mutacion: ");
			// 1. Seleccionamos dos puntos al azar
			int x = random();
			int y = random();
			while (y == x) {
				y = random();
			}
			System.out.println("Posiciones a intercambiar: "+x+"-"+y);
			ArrayList<Integer> ruta = new ArrayList<Integer>();
			nodos(hijos.get(i).getPermutacion(), ruta);
			// 2. Intercambiamos sus valores
			// System.out.println("Ruta original: "+ruta);
			int aux1 = ruta.get(x);
			int aux2 = ruta.get(y);
			ruta.add(x, aux2);
			ruta.add(y, aux1);
			System.out.println("Ruta mutada: "+ruta);
			System.out.println("/////////////////////////////////////");
		}
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
		int superior = random();
		int inferior = random();
		int r = superior - inferior;
		while(r<=3)
		{
			superior = random();
			inferior = random();
			r = superior - inferior;
		}
		for (int j = inferior; j < superior; j++) {
			// Verificamos el que indice obtenido no
			// ha sido seleccionando anteriormente
			// if (subcadena.contains(Padre.get(j))) {
			// while (subcadena.contains(Padre.get(r)))
			// // Generamos un nuevo indice
			// r = random();
			// }
			// Almacenamos el nodo a la subcadena formada
			subcadena.add(Padre.get(j));
		}
		return subcadena;
	}

	public static int random() {
		// Generar numeros random de 1 <= n <= 16 (grafo.size)
		int numero = (int) (Math.random() * grafo.size() - 1) + 1;
		return numero;
	}

	public static int random_probabilidad() {
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
