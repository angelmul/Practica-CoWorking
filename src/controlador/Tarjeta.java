package controlador;

//Clase que contiene las tarjetas de acceso, su numero y su estado.
public class Tarjeta {
	int nTarjeta = 0; //Numero de la tarjeta.
	boolean ocupado = false; //Estado de la tarjeta por defecto es no ocupado.
	
	public Tarjeta(int nPlaza) {
		this.nTarjeta = nPlaza;
	}
	
	public int getnPlaza() {
		return nTarjeta;
	}
	public void setnPlaza(int nPlaza) {
		this.nTarjeta = nPlaza;
	}
	public boolean isOcupado() {
		return ocupado;
	}
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
}
