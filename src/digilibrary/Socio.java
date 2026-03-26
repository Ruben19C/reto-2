package digilibrary;

import java.sql.Date;

public class Socio {
	private String nombre;
    private String DNI;
    private String email;
    private String usuario;
    private String password;
    private String telefono;
    
    public Socio(String nombre,String DNI,String email,String usuario,String password,String telefoni) {
    	this.nombre=nombre;
    	this.DNI=DNI;
    	this.email=email;
    	this.password=password;
    	this.usuario=usuario;
    	this.telefono=telefono;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
    
	public void alquilarEjemplar(ejemplar e) {
        Date hoy = new Date(System.currentTimeMillis());
        Date entrega = Date.valueOf("2026-04-10");
        
        alquiler reg = new alquiler(hoy, entrega, 15);
        reg.registrarAlquiler(e, this); // "this" envía a este socio actual
    }
	
	public void devolverEjemplar(ejemplar e) {
        e.cambiarEstado("Disponible");
    }
}
