package model;

public class Usuario {

    private int id;
    private String nombre;
    private String rol;
    private String fechaHoraUltimoInicio;

    public Usuario(int id, String nombre, String rol, String fechaHoraUltimoInicio) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.fechaHoraUltimoInicio = fechaHoraUltimoInicio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public String getFechaHoraUltimoInicio() {
        return fechaHoraUltimoInicio;
    }

    public void setFechaHoraUltimoInicio(String fechaHoraUltimoInicio) {
        this.fechaHoraUltimoInicio = fechaHoraUltimoInicio;
    }
}
