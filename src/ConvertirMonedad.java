import java.util.InputMismatchException;
import java.util.Scanner;

public class ConvertirMonedad {

    public  static void convertirMoneda(String desde, String hasta, ConsultaMoneda consulta, Scanner scanner) {
        double cantidad = 0;
        double cantidadConvertida;
        Moneda moneda = null;

        try {
            moneda = consulta.busquedaDeMoneda(desde,hasta);
            if ( moneda == null) {
                System.out.print("Error, no se pudo encotrar información para la conversion de " + desde + " a " + hasta);
                return;
            }

            System.out.print("Ingrese la cantidad de " + hasta);
            try {
                cantidad = scanner.nextDouble();
                if (cantidad <= 0) {
                    System.out.print("Error, la cantidad debe de ser mayor a cero 0");
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.print("Error, la cantidad ingresada no es un número valido");
                scanner.next();
                return;
            }
            scanner.nextLine();

            cantidadConvertida = cantidad * moneda.conversion_tasa();
            System.out.println("La tasa de conversio de "  + desde + " a " + hasta + " = " + moneda.conversion_tasa());
            System.out.println(cantidad + " " + desde + " = " + cantidadConvertida + hasta);
        } catch (RuntimeException e) {
            System.out.println("Error, ocurrió un problema al obtener la información de la moneda, por favor, intentalo de nuevo mas tarde");
            System.out.println("Detalles del error: Ingresa una moneda valida");

        }
    }

    public static void ConvertirMoneda( ConvertirMonedad consulta, Scanner scanner) {

    }
}




