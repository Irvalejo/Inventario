package model;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String departamento;
    private int almacenId;
    private String almacenNombre;
    private String fechaHoraCreacion;
    private String fechaHoraUltimaMod;
    private String ultimoUsuario;

    public Producto(int id, String nombre, double precio, int cantidad,
                    String departamento, int almacenId, String almacenNombre,
                    String fechaHoraCreacion, String fechaHoraUltimaMod,
                    String ultimoUsuario) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.departamento = departamento;
        this.almacenId = almacenId;
        this.almacenNombre = almacenNombre;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraUltimaMod = fechaHoraUltimaMod;
        this.ultimoUsuario = ultimoUsuario;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public String getDepartamento() { return departamento; }
    public int getAlmacenId() { return almacenId; }
    public String getAlmacenNombre() { return almacenNombre; }
    public String getFechaHoraCreacion() { return fechaHoraCreacion; }
    public String getFechaHoraUltimaMod() { return fechaHoraUltimaMod; }
    public String getUltimoUsuario() { return ultimoUsuario; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setAlmacenId(int almacenId) { this.almacenId = almacenId; }
    public void setAlmacenNombre(String almacenNombre) { this.almacenNombre = almacenNombre; }
    public void setFechaHoraUltimaMod(String fechaHoraUltimaMod) { this.fechaHoraUltimaMod = fechaHoraUltimaMod; }
    public void setUltimoUsuario(String ultimoUsuario) { this.ultimoUsuario = ultimoUsuario; }
}
