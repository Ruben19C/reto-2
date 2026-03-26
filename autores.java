public class Autor {

    private int cod_autor;

    public Autor(int cod_autor) {
        this.cod_autor = cod_autor;
    }

    public int getCodAutor() {
        return cod_autor;
    }

    public void setCodAutor(int cod_autor) {
        this.cod_autor = cod_autor;
    }

    public void agregarLibro(Libro l) {
        System.out.println("Libro agregado: " + l);
    }
}º