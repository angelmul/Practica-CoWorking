package controlador;

//Clase que contiene el ordenador que es usado por la persona cuando tiene sus dos tarjetas de acceso.
public class Ordenador {
	
	boolean ocupado = false; //Estado del ordenador por defecto en no ocupado.
	Persona per = null; //Contiene la persona que se dispone a usarlo cuando obtiene el acceso.
	
	//Metodo para obtener la persona y poner el ordenador ocupado.
	public synchronized void usarOrdenador(Persona p) {
		this.ocupado = true;
		this.per = p;
	}

}
