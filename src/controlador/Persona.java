package controlador;

import java.util.ArrayList;
import java.util.Random;

//Clase que contiene las personas y la ejecución que realiza cada hilo.
public class Persona extends Thread {
	Random r = new Random();
	EspacioCoworking control; //Se le pasa la clase EspacioCoworking para usar los sentencias sincronizados.
	ArrayList<Tarjeta> tarjetasOcupadas = new ArrayList<>(); //Lista de las tarjetas que almacena la persona.
	private int idPersona; //Numero que identifica la persona.
	
	public Persona (int id, EspacioCoworking con) {
		idPersona = id;
		control = con;
	}
	
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	
	//Metodo para agregar una tarjeta encontrada en caso de encontrarse.
	public void addTarjetasOcupadas(Tarjeta tar) {
		if(tar != null) {
			this.tarjetasOcupadas.add(tar);
		}
	}
	

	@Override //Metodo que ejecuta la clase como hilo al iniciarse.
	public void run() {
		while(true) { //Bucle que nunca se cierra ya que las personas cuando termina su ejecucion, vuelve a empezar de nuevo.
			try {
				//Inicio de la ejecucion, muestra un mensaje y duerme un tiempo aletorio de 1 a 10 segundos.
				 control.mostrarMensaje("[Persona F"+idPersona+"] se sienta en la mesa y se pone a pensar...");
				 Thread.sleep(r.nextInt(10) * 1_000L);
				 
				 //La persona se dispone a coger las tarjetas que les corresponden
				 control.mostrarMensaje("[Persona F"+idPersona+"] se dispone a coger las tarjetas...");
				 control.cogerTarjetas(this); //Una vez coge las tarjetas usa el ordenador
				 Thread.sleep(r.nextInt(10) * 2_000L); //Espera un tiempo de 2 a 20 segundos
				 
				 control.soltarOrdenador(this); //Finaliza soltando el ordenador y dejando al resto de las personas sus tarjetas libres.
				 Thread.sleep(r.nextInt(10) * 5_00L); //Duerme un tiempo antes de volver a empezar la ejecucion.
			} catch (InterruptedException e) {
				e.printStackTrace();
				// Restore interrupted state...
			    Thread.currentThread().interrupt();
			}
		}
	}
}
