import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDAO {

    public void listarCategorias() {
        String sql = "SELECT categoria_id, nombre, descripcion FROM categorias ORDER BY categoria_id";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            System.out.println("\nCategorías disponibles:");

            while (resultado.next()) {
                System.out.println(
                        resultado.getInt("categoria_id") + " - " +
                        resultado.getString("nombre") + " (" +
                        resultado.getString("descripcion") + ")"
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorías desde MySQL: " + e.getMessage());
        }
    }
}
