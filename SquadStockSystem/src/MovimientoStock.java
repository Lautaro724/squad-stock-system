import java.time.LocalDateTime;

public class MovimientoStock {

    private int idMovimiento;
    private Producto producto;
    private String tipoMovimiento;
    private int cantidad;
    private LocalDateTime fechaMovimiento;

    public MovimientoStock(int idMovimiento, Producto producto, String tipoMovimiento, int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad del movimiento debe ser mayor a cero.");
        }

        if (!tipoMovimiento.equalsIgnoreCase("INGRESO") &&
            !tipoMovimiento.equalsIgnoreCase("EGRESO")) {
            throw new IllegalArgumentException("El tipo de movimiento debe ser INGRESO o EGRESO.");
        }

        this.idMovimiento = idMovimiento;
        this.producto = producto;
        this.tipoMovimiento = tipoMovimiento.toUpperCase();
        this.cantidad = cantidad;
        this.fechaMovimiento = LocalDateTime.now();
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void mostrarMovimiento() {
        System.out.println("ID Movimiento: " + idMovimiento);
        System.out.println("Producto: " + producto.getNombre());
        System.out.println("Tipo: " + tipoMovimiento);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Fecha: " + fechaMovimiento);
        System.out.println("---------------------------------");
    }
}