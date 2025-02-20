
import java.util.Scanner;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        VuelosOperations operaciones = new VuelosOperations();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---------------Menú de Operaciones------------------------------:");
            System.out.println("1. Obtener destino del vuelo");
            System.out.println("2. Obtener billetes vendidos del vuelo");
            System.out.println("3. Obtener nombre del pasajero por asiento");
            System.out.println("4. Vender un billete");
            System.out.println("5. Cambiar número de plazas disponibles");
            System.out.println("6. Cancelar compra de un asiento");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo1 = scanner.nextLine();
                    operaciones.getDestinoVuelo(codigoVuelo1);
                    break;
                case 2:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo2 = scanner.nextLine();
                    operaciones.getBilletesVendidos(codigoVuelo2);
                    break;
                case 3:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo3 = scanner.nextLine();
                    System.out.print("Ingrese el número del asiento: ");
                    int asiento = scanner.nextInt();
                    operaciones.getNombrePasajeroAsiento(codigoVuelo3, asiento);
                    break;
                case 4:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo4 = scanner.nextLine();
                    System.out.print("Ingrese el número del asiento: ");
                    int asientoVenta = scanner.nextInt();
                    scanner.nextLine();  // Consumir la nueva línea
                    System.out.print("Ingrese el DNI: ");
                    String dni = scanner.nextLine();
                    System.out.print("Ingrese el apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    Document nuevaCompra = new Document("asiento", asientoVenta)
                            .append("dni", dni)
                            .append("apellido", apellido)
                            .append("nombre", nombre);
                    operaciones.venderBillete(codigoVuelo4, nuevaCompra);
                    break;
                case 5:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo5 = scanner.nextLine();
                    System.out.print("Ingrese el nuevo número de plazas disponibles: ");
                    int nuevoNumero = scanner.nextInt();
                    operaciones.cambiarNumeroDisponibles(codigoVuelo5, nuevoNumero);
                    break;
                case 6:
                    System.out.print("Ingrese el código del vuelo: ");
                    String codigoVuelo6 = scanner.nextLine();
                    System.out.print("Ingrese el número del asiento a cancelar: ");
                    int asientoCancelar = scanner.nextInt();
                    operaciones.cancelarCompra(codigoVuelo6, asientoCancelar);
                    break;
                case 7:
                    operaciones.close();
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
}