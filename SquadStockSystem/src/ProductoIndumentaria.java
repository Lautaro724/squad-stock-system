public class ProductoIndumentaria extends Producto {

    private String talle;
    private String color;

    public ProductoIndumentaria(int idProducto,
                                Categoria categoria,
                                String nombre,
                                double precio,
                                int stockActual,
                                String talle,
                                String color) {

        super(idProducto, categoria, nombre, precio, stockActual);

        this.talle = talle;
        this.color = color;
    }

    public String getTalle() {
        return talle;
    }

    public String getColor() {
        return color;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void mostrarDetalle() {

        System.out.println("=================================");
        System.out.println("ID: " + getIdProducto());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Categoría: " + getCategoria().getNombre());
        System.out.println("Talle: " + talle);
        System.out.println("Color: " + color);
        System.out.println("Precio: $" + getPrecio());
        System.out.println("Stock: " + getStockActual());
        System.out.println("=================================");
    }
}