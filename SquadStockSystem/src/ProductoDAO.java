import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {

    public void registrarProducto(String nombre, int categoriaId, String talle,
                                  String color, double precio, int stockActual) {

        String sql = "INSERT INTO productos (categoria_id, nombre, talle, color, precio, stock_actual) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, categoriaId);
            statement.setString(2, nombre);
            statement.setString(3, talle);
            statement.setString(4, color);
            statement.setDouble(5, precio);
            statement.setInt(6, stockActual);
            statement.executeUpdate();

            System.out.println("Producto registrado correctamente en MySQL.");

        } catch (SQLException e) {
            System.out.println("Error al registrar producto en MySQL: " + e.getMessage());
        }
    }

    public void listarProductos() {
        String sql = "SELECT p.producto_id, p.nombre, c.nombre AS categoria, "
                   + "p.talle, p.color, p.precio, p.stock_actual "
                   + "FROM productos p "
                   + "INNER JOIN categorias c ON p.categoria_id = c.categoria_id "
                   + "ORDER BY p.producto_id";

        ejecutarConsultaProductos(sql, null);
    }

    public void buscarProductoPorNombre(String nombre) {
        String sql = "SELECT p.producto_id, p.nombre, c.nombre AS categoria, "
                   + "p.talle, p.color, p.precio, p.stock_actual "
                   + "FROM productos p "
                   + "INNER JOIN categorias c ON p.categoria_id = c.categoria_id "
                   + "WHERE LOWER(p.nombre) LIKE LOWER(?) "
                   + "ORDER BY p.producto_id";

        ejecutarConsultaProductos(sql, "%" + nombre + "%");
    }

    public void ordenarProductosPorPrecio() {
        String sql = "SELECT p.producto_id, p.nombre, c.nombre AS categoria, "
                   + "p.talle, p.color, p.precio, p.stock_actual "
                   + "FROM productos p "
                   + "INNER JOIN categorias c ON p.categoria_id = c.categoria_id "
                   + "ORDER BY p.precio ASC";

        ejecutarConsultaProductos(sql, null);
    }

    private void ejecutarConsultaProductos(String sql, String parametroBusqueda) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            if (parametroBusqueda != null) {
                statement.setString(1, parametroBusqueda);
            }

            try (ResultSet resultado = statement.executeQuery()) {
                boolean hayResultados = false;
                System.out.println("\n--- Productos registrados en MySQL ---");

                while (resultado.next()) {
                    hayResultados = true;
                    System.out.println("ID: " + resultado.getInt("producto_id"));
                    System.out.println("Nombre: " + resultado.getString("nombre"));
                    System.out.println("Categoría: " + resultado.getString("categoria"));
                    System.out.println("Talle: " + resultado.getString("talle"));
                    System.out.println("Color: " + resultado.getString("color"));
                    System.out.println("Precio: $" + resultado.getDouble("precio"));
                    System.out.println("Stock: " + resultado.getInt("stock_actual"));
                    System.out.println("----------------------------------");
                }

                if (!hayResultados) {
                    System.out.println("No se encontraron productos.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar productos desde MySQL: " + e.getMessage());
        }
    }
}
