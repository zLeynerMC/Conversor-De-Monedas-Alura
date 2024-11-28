import java.util.List;
import java.util.Scanner;

public class GestorDeMonedas {

    private final List<String> monedas;

    public GestorDeMonedas (List<String> monedas){
        this.monedas = monedas;
    }

    public String seleccionarMonedas(String tipoMoneda){
        Scanner lectura = new Scanner(System.in);
        String seleccion = null;

        System.out.println("Monedas Disponibles:");
        for (int i = 0; i < monedas.size(); i++) {
            System.out.println((i + 1) + ". " + monedas.get(i));
        }

        while (seleccion == null){

            System.out.println("Por Favor, Seleccione La Moneda Que Desea Convertir: ");
            var usuario = lectura.nextLine();

            try {
                int index = Integer.parseInt(usuario) - 1;
                if (index >= 0 && index < monedas.size()) {
                    seleccion = monedas.get(index);
                } else {
                    System.out.println("Opción fuera del límite. Inténtelo nuevamente.");
                }
            } catch (NumberFormatException e) {
                if (monedas.contains(usuario.toUpperCase())) {
                    seleccion = usuario.toUpperCase();
                } else {
                    System.out.println("Código de moneda inválido. Inténtelo nuevamente.");
                }
            }
        }
        return seleccion;
    }

    public double ingresarValor() {
        Scanner lectura = new Scanner(System.in);
        double valor = 0;

        while (valor <= 0) {
            System.out.print("Ingrese el valor que desea convertir: ");
            try {
                valor = Double.parseDouble(lectura.nextLine());
                if (valor <= 0) {
                    System.out.println("El valor debe ser positivo. Inténtelo nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Inténtelo nuevamente.");
            }
        }

        return valor;
    }
}


