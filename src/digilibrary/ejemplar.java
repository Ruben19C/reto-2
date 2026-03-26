package digilibrary;

public class ejemplar {
	private int id_ejemplar;
	private String estado_devolucion;
	
	public ejemplar(int id,String estado) {
		this.id_ejemplar=id;
		this.estado_devolucion=estado;
	}

	public int getId_ejemplar() {
		return id_ejemplar;
	}

	public void setId_ejemplar(int id_ejemplar) {
		this.id_ejemplar = id_ejemplar;
	}

	public String getEstado_devolucion() {
		return estado_devolucion;
	}

	public void setEstado_devolucion(String estado_devolucion) {
		this.estado_devolucion = estado_devolucion;
	}
	
	
}
