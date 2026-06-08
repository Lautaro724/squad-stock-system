import java.util.ArrayList;
import java.util.Comparator;

public class SistemaGestionStock {

    private ArrayList<Producto> productos;
    private ArrayList<Categoria> categorias;
    private ArrayList<MovimientoStock> movimientos;

    private int contadorProductos;
    private int contadorMovimientos;

    public SistemaGestionStock() {
        this.productos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.movimientos = new ArrayList<>();

        this.contadorProductos = 1;
        this.contadorMovimientos = 1;

        cargarCategoriasIniciales();
    }

    private void cargarCategoriasIniciales() {
        categorias.add(new Categoria(1, "Remeras Oversize", "Remeras deportivas oversize SQUAD"));
        categorias.add(new Categoria(2, "Musculosas", "Musculosas deportivas para entrenamiento"));
        categorias.add(new Categoria(3, "Accesorios", "Accesorios deportivos de la marca"));
    }

    public void listarCategorias() {
        System.out.println("\nCategorías disponibles:");
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    public Categoria buscarCategoriaPorId(int idCategoria) {
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria() == idCategoria) {
                return categoria;
            }
        }
        return null;
    }

    public void registrarProducto(String nombre, int idCategoria, String talle,
                                  String color, double precio, int stockInicial) {

        Categoria categoria = buscarCategoriaPorId(idCategoria);

        if (categoria == null) {
            throw new IllegalArgumentException("La categoría seleccionada no existe.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        if (talle == null || talle.trim().isEmpty()) {
            throw new IllegalArgumentException("El talle es obligatorio.");
        }

        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("El color es obligatorio.");
        }

        Producto producto = new ProductoIndumentaria(
                contadorProductos,
                categoria,
                nombre,
                precio,
                stockInicial,
                talle,
                color
        );

        productos.add(producto);

        MovimientoStock movimientoInicial = new MovimientoStock(
                contadorMovimientos,
                producto,
                "INGRESO",
                stockInicial
        );

        movimientos.add(movimientoInicial);

        contadorProductos++;
        contadorMovimientos++;

        System.out.println("Producto registrado correctamente.");
    }

    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : productos) {
            producto.mostrarDetalle();
        }
    }

    public Producto buscarProductoPorId(int idProducto) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProducto) {
                return producto;
            }
        }
        return null;
    }

    public void buscarProductoPorNombre(String nombre) {
        boolean encontrado = false;

        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                producto.mostrarDetalle();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos con ese nombre.");
        }
    }

    public void ordenarProductosPorPrecio() {
        productos.sort(Comparator.comparingDouble(Producto::getPrecio));

        System.out.println("Productos ordenados por precio de menor a mayor:");
        listarProductos();
    }

    public void registrarIngresoStock(int idProducto, int cantidad) {
        Producto producto = buscarProductoPorId(idProducto);

        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        producto.ingresarStock(cantidad);

        MovimientoStock movimiento = new MovimientoStock(
                contadorMovimientos,
                producto,
                "INGRESO",
                cantidad
        );

        movimientos.add(movimiento);
        contadorMovimientos++;

        System.out.println("Ingreso de stock registrado correctamente.");
    }

    public void registrarEgresoStock(int idProducto, int cantidad) {
        Producto producto = buscarProductoPorId(idProducto);

        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        producto.egresarStock(cantidad);

        MovimientoStock movimiento = new MovimientoStock(
                contadorMovimientos,
                producto,
                "EGRESO",
                cantidad
        );

        movimientos.add(movimiento);
        contadorMovimientos++;

        System.out.println("Egreso de stock registrado correctamente.");
    }

    public void listarMovimientos() {
        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        for (MovimientoStock movimiento : movimientos) {
            movimiento.mostrarMovimiento();
        }
    }
}