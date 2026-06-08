public abstract class Producto {

    private int idProducto;
    private Categoria categoria;
    private String nombre;
    private double precio;
    private int stockActual;

    public Producto(int idProducto, Categoria categoria, String nombre,
                    double precio, int stockActual) {

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (stockActual < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        this.idProducto = idProducto;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.stockActual = stockActual;
    }

    // getters

    public int getIdProducto() {
        return idProducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    // setters

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        this.precio = precio;
    }

    public void setStockActual(int stockActual) {
        if (stockActual < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        this.stockActual = stockActual;
    }

    // metodos de negocio

    public void ingresarStock(int cantidad) {
        stockActual += cantidad;
    }

    public void egresarStock(int cantidad) {

        if (cantidad > stockActual) {
            throw new IllegalArgumentException("Stock insuficiente.");
        }

        stockActual -= cantidad;
    }

    // metodo abstracto (abstracción + Polimorfismo)

    public abstract void mostrarDetalle();
}