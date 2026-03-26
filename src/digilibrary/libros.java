package digilibrary;

public class libros {
	private int cod_libros;
	private String ISBN;
	private String nombre_libro;
	private String descripcion;
	private int num_ejemplares;
	
	public libros(int cod_libros,String ISBN,String nombre_libro,String descripcion,int num_ejemplares) {
		this.cod_libros = cod_libros;
		this.ISBN = ISBN;
		this.nombre_libro = nombre_libro;
		this.descripcion = descripcion;
		this.num_ejemplares = num_ejemplares;
	}

	public int getCod_libros() {
		return cod_libros;
	}

	public void setCod_libros(int cod_libros) {
		this.cod_libros = cod_libros;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getNombre_libro() {
		return nombre_libro;
	}

	public void setNombre_libro(String nombre_libro) {
		this.nombre_libro = nombre_libro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNum_ejemplares() {
		return num_ejemplares;
	}

	public void setNum_ejemplares(int num_ejemplares) {
		this.num_ejemplares = num_ejemplares;
	}
	
	public void agregarEjemplar() {
        this.num_ejemplares++;
        System.out.println("Se ha añadido un nuevo ejemplar físico del libro: " + this.nombre_libro);
    }
}
