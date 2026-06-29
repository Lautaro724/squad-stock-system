import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimientoDAO {

    public void registrarIngreso(int productoId, int cantidad) {

        String actualizarStock = "UPDATE productos SET stock_actual = stock_actual + ? WHERE producto_id = ?";
        String insertarMovimiento = "INSERT INTO movimientos_stock (producto_id, tipo_movimiento, cantidad) VALUES (?, 'INGRESO', ?)";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            PreparedStatement stmtStock = conexion.prepareStatement(actualizarStock);
            stmtStock.setInt(1, cantidad);
            stmtStock.setInt(2, productoId);
            stmtStock.executeUpdate();

            PreparedStatement stmtMovimiento = conexion.prepareStatement(insertarMovimiento);
            stmtMovimiento.setInt(1, productoId);
            stmtMovimiento.setInt(2, cantidad);
            stmtMovimiento.executeUpdate();

            System.out.println("Ingreso de stock registrado correctamente en MySQL.");

        } catch (SQLException e) {
            System.out.println("Error al registrar ingreso de stock: " + e.getMessage());
        }
    }

    public void registrarEgreso(int productoId, int cantidad) {

        String consultarStock = "SELECT stock_actual FROM productos WHERE producto_id = ?";
        String actualizarStock = "UPDATE productos SET stock_actual = stock_actual - ? WHERE producto_id = ?";
        String insertarMovimiento = "INSERT INTO movimientos_stock (producto_id, tipo_movimiento, cantidad) VALUES (?, 'EGRESO', ?)";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            PreparedStatement stmtConsulta = conexion.prepareStatement(consultarStock);
            stmtConsulta.setInt(1, productoId);

            ResultSet resultado = stmtConsulta.executeQuery();

            if (resultado.next()) {
                int stockActual = resultado.getInt("stock_actual");

                if (cantidad > stockActual) {
                    throw new IllegalArgumentException("Stock insuficiente.");
                }

                PreparedStatement stmtStock = conexion.prepareStatement(actualizarStock);
                stmtStock.setInt(1, cantidad);
                stmtStock.setInt(2, productoId);
                stmtStock.executeUpdate();

                PreparedStatement stmtMovimiento = conexion.prepareStatement(insertarMovimiento);
                stmtMovimiento.setInt(1, productoId);
                stmtMovimiento.setInt(2, cantidad);
                stmtMovimiento.executeUpdate();

                System.out.println("Egreso de stock registrado correctamente en MySQL.");

            } else {
                System.out.println("El producto no existe.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("Error al registrar egreso de stock: " + e.getMessage());
        }
    }

    public void listarMovimientos() {

        String sql = "SELECT m.movimiento_id, p.nombre AS producto, m.tipo_movimiento, "
                   + "m.cantidad, m.fecha_movimiento "
                   + "FROM movimientos_stock m "
                   + "INNER JOIN productos p ON m.producto_id = p.producto_id "
                   + "ORDER BY m.movimiento_id";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            System.out.println("\n--- Movimientos registrados en MySQL ---");

            while (resultado.next()) {
                System.out.println("ID Movimiento: " + resultado.getInt("movimiento_id"));
                System.out.println("Producto: " + resultado.getString("producto"));
                System.out.println("Tipo: " + resultado.getString("tipo_movimiento"));
                System.out.println("Cantidad: " + resultado.getInt("cantidad"));
                System.out.println("Fecha: " + resultado.getTimestamp("fecha_movimiento"));
                System.out.println("----------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error al listar movimientos: " + e.getMessage());
        }
    }
}