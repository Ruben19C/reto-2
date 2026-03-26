package digilibrary;

import java.sql.Date;

public class alquiler {
	private Date fecha_alquiler; 
	private Date fecha_devolucion;
	private int max_renta;
	
	public alquiler(Date fecha_alquiler,Date fecha_devolucion,int max_renta) {
		this.fecha_alquiler = fecha_alquiler;
		this.fecha_devolucion = fecha_devolucion;
		this.max_renta = max_renta;
	}
	public Date getFecha_alquiler() {
		return fecha_alquiler;
	}

	public void setFecha_alquiler(Date fecha_alquiler) {
		this.fecha_alquiler = fecha_alquiler;
	}

	public Date getFecha_devolucion() {
		return fecha_devolucion;
	}

	public void setFecha_devolucion(Date fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}

	public int getMax_renta() {
		return max_renta;
	}

	public void setMax_renta(int max_renta) {
		this.max_renta = max_renta;
	}
	
	public void registrarAlquiler(ejemplar e, Socio s) {
        // Aquí conectamos las piezas
        System.out.println("Alquiler registrado: El socio " + s.getNombre() + 
                           " se lleva el ejemplar ID " + e.getId_ejemplar());
    }
	
	public Date calcularFechaDevolucion() {
        return this.fecha_devolucion;
    }
	
}
