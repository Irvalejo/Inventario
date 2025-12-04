package model;

public class Almacen {

    private int id;
    private String nombre;
    private String fechaHoraCreacion;
    private String fechaHoraUltimaMod;
    private String ultimoUsuario;


    public Almacen(int id, String nombre,
                   String fechaHoraCreacion,
                   String fechaHoraUltimaMod,
                   String ultimoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraUltimaMod = fechaHoraUltimaMod;
        this.ultimoUsuario = ultimoUsuario;
    }


    public Almacen(int id, String nombre) {
        this(id, nombre, null, null, null);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public String getFechaHoraUltimaMod() {
        return fechaHoraUltimaMod;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaHoraUltimaMod(String fechaHoraUltimaMod) {
        this.fechaHoraUltimaMod = fechaHoraUltimaMod;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
