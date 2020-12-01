package controlador;
import java.util.ArrayList;

//Clase principal encargada de gestionar y crear las personas (hilos) y las tarjetas.
public class Lanzador {
	static ArrayList<Tarjeta> tarjetas = new ArrayList<>();

	public static void main(String[] args) {
		EspacioCoworking controlador = new EspacioCoworking();
		ArrayList<Persona> personas = new ArrayList<>();
		
		for(int i=0; i < 5; i++) {
			tarjetas.add(new Tarjeta(i));
		}
		
		for(int i=0; i < 5; i++) {
			personas.add(new Persona(i,controlador));
			personas.get(i).start();
		}

	}
	
	//Metodo encargado de buscar en el ArrayList si hay una tarjeta libre que corresponda a la persona que la solicita.
	public static synchronized Tarjeta buscarTarjeta(Persona per) {
		for (int i=0; i<tarjetas.size(); i++) {
			if (!tarjetas.get(i).isOcupado()) { //Recorre todas las tarjetas no ocupadas.
				if(i == 0 && (per.getIdPersona() == 0 || per.getIdPersona() == 4)) {
					return obtenerTarjeta(i, per);
				} else if (i == 1 && (per.getIdPersona() == 0 || per.getIdPersona() == 1)) {
					return obtenerTarjeta(i, per);		
				} else if (i == 2 && (per.getIdPersona() == 1 || per.getIdPersona() == 2)) {
					return obtenerTarjeta(i, per);
				} else if (i == 3 && (per.getIdPersona() == 2 || per.getIdPersona() == 3)) {
					return obtenerTarjeta(i, per);
				} else if (i == 4 && (per.getIdPersona() == 3 || per.getIdPersona() == 4)) {
					return obtenerTarjeta(i, per);
				}
			}
		}
		
		return null;
		
	}
	
	//Metodo refactorizado para el metodo anterior...
	private synchronized static Tarjeta obtenerTarjeta(int i, Persona per) {
		System.out.println("[TARJETA "+i+" OBTENIDA POR F"+per.getIdPersona()+"]");
		tarjetas.get(i).ocupado = true;
		return tarjetas.get(i);
	}
}