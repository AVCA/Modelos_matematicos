package TSP_Poblacion;

import java.util.ArrayList;

public class Ruta {
	
	String permutacion;
	double distancia;
	
	public Ruta(String p, double d) {
		this.permutacion=p;
		this.distancia=d;
	}

	
	public String getPermutacion() {
		return permutacion;
	}


	public void setPermutacion(String permutacion) {
		this.permutacion = permutacion;
	}


	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	
	@Override
	public String toString() {
		return permutacion + " , " + distancia;
	}
	
	
}
