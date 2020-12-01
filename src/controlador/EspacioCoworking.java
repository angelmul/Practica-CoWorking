package controlador;

import java.util.ArrayList;
import java.util.Random;

//Clase que contiene las sentencias sincronizadas y monitorea la clase Persona.
public class EspacioCoworking {
	Object controlPersonas = new Object(); //Monitor para la clase Persona.
	Ordenador sobremesa = new Ordenador(); //Ordenador (recurso a compartir por las personas).
	Random r = new Random(); //Clase Random para generar tiempos de espera aleatorios.

	//Metodo usado por las personas para buscar las tarjetas de acceso al ordenador.
	public void cogerTarjetas(Persona per) {
		int contador = 0; //Contador para medir si lleva con la tarjeta mucho tiempo.
		try {
			while (per.tarjetasOcupadas.size() < 2) { //Mientras que la persona no tenga 2 tarjetas en su lista no saldra de este bucle.
				Thread.sleep(r.nextInt(10) * 1_000L); //Duerme en cada intento un tiempo para que no se obtengan las 2 tarjetas de acceso inmediatamente.
				synchronized (controlPersonas) { //Sentencia sincronizada para evitar accesos a la misma tarjeta, se encargada de agregar la tarjeta que encuentra a la lista de la persona.
					if (!sobremesa.ocupado) { //Agrega la tarjeta siempre y cuando el ordenador este disponible, cuando se este usando espera a que se notifique que se termina de usar
						per.addTarjetasOcupadas(Lanzador.buscarTarjeta(per)); //Agrega la tarjeta a la lista de la persona
						if (contador > 10) { //En el caso de que lleve más de 10 iteraciones con tarjeta las suelta para que no haya bloqueo de inalicion entre las personas.
							soltarTarjeta(per);
							contador = -1;
						}
						contador++;
					} else {
						controlPersonas.wait();
					}
				}
			}
			synchronized (controlPersonas) { //Sentencia sincronizada del uso del ordenador
				if (!sobremesa.ocupado) { //Siempre que este libre, asigna el ordenador a la persona que ha llegado
					sobremesa.usarOrdenador(per);
					mostrarMensaje("[ORDENADOR] Ocupado por la persona F" + per.getIdPersona());
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	//Metodo para dejar de usar el ordenador.
	public void soltarOrdenador(Persona per) {
		synchronized (controlPersonas) {
			for (int i = 0; i < per.tarjetasOcupadas.size(); i++) {
				per.tarjetasOcupadas.get(i).ocupado = false; //Suelta las tarjetas que tiene la persona y las deja no ocupadas.
			}
			per.tarjetasOcupadas = new ArrayList<>(); //Obtiene una nueva lista para almacenar nuevamente tarjetas
			mostrarMensaje("[ORDENADOR] Liberado por la persona F" + per.getIdPersona());
			sobremesa.per = null; 
			sobremesa.ocupado = false;
			controlPersonas.notifyAll(); //Despierta a todas las personas que estaban esperando a que la persona dejara de usar el ordenador.
		}
	}

	//Metodo para dejar las tarjetas 
	public void soltarTarjeta(Persona per) {
		synchronized (controlPersonas) {
			for (int i = 0; i < per.tarjetasOcupadas.size(); i++) {
				per.tarjetasOcupadas.get(i).ocupado = false; //Suelta las tarjetas que tiene la persona y las deja no ocupadas.
				mostrarMensaje("[TARJETA " + i + " LIBERADA POR F" + per.getIdPersona() + "]");
			}
			per.tarjetasOcupadas = new ArrayList<>(); //Obtiene una nueva lista para almacenar nuevamente tarjetas
		}
	}

	//Metodo sincronizado para mostrar mensajes de los hilos ordenadamente.
	public void mostrarMensaje(String mensaje) {
		synchronized (controlPersonas) {
			System.out.println(mensaje);
		}
	}
}
