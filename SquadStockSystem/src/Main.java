import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ProductoDAO productoDAO = new ProductoDAO();
        MovimientoDAO movimientoDAO = new MovimientoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        int opcion = 0;

        do {
            try {
                System.out.println("\n===== SISTEMA DE GESTIÓN DE STOCK SQUAD =====");
                System.out.println("1. Registrar producto");
                System.out.println("2. Listar productos");
                System.out.println("3. Buscar producto por nombre");
                System.out.println("4. Ordenar productos por precio");
                System.out.println("5. Registrar ingreso de stock");
                System.out.println("6. Registrar egreso de stock");
                System.out.println("7. Listar movimientos de stock");
                System.out.println("8. Salir");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarProducto(scanner, productoDAO, categoriaDAO);
                        break;
                    case 2:
                        productoDAO.listarProductos();
                        break;
                    case 3:
                        System.out.print("Ingrese nombre o parte del nombre del producto: ");
                        String nombreBusqueda = scanner.nextLine();
                        productoDAO.buscarProductoPorNombre(nombreBusqueda);
                        break;
                    case 4:
                        productoDAO.ordenarProductosPorPrecio();
                        break;
                    case 5:
                        registrarIngreso(scanner, movimientoDAO);
                        break;
                    case 6:
                        registrarEgreso(scanner, movimientoDAO);
                        break;
                    case 7:
                        movimientoDAO.listarMovimientos();
                        break;
                    case 8:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un valor numérico.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }

        } while (opcion != 8);

        scanner.close();
    }

    private static void registrarProducto(Scanner scanner, ProductoDAO productoDAO, CategoriaDAO categoriaDAO) {
        System.out.println("\n--- Registrar producto ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        categoriaDAO.listarCategorias();

        System.out.print("ID categoría: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Talle: ");
        String talle = scanner.nextLine();

        System.out.print("Color: ");
        String color = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock inicial: ");
        int stockInicial = scanner.nextInt();
        scanner.nextLine();

        productoDAO.registrarProducto(nombre, idCategoria, talle, color, precio, stockInicial);
    }

    private static void registrarIngreso(Scanner scanner, MovimientoDAO movimientoDAO) {
        System.out.println("\n--- Registrar ingreso de stock ---");
        System.out.print("ID producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad a ingresar: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        movimientoDAO.registrarIngreso(idProducto, cantidad);
    }

    private static void registrarEgreso(Scanner scanner, MovimientoDAO movimientoDAO) {
        System.out.println("\n--- Registrar egreso de stock ---");
        System.out.print("ID producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad a egresar: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        movimientoDAO.registrarEgreso(idProducto, cantidad);
    }
}
