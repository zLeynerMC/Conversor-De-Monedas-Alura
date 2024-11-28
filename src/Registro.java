import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registro {
    private static final String Archivo_Registro = "registro_conversacion.txt";

            public static void registrarConversacion(String mensaje){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo_Registro, true))){
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String horaFecha = LocalDateTime.now().format(formatoFecha);

            writer.write("Fecha y Hora: " + horaFecha + " | Conversión: " + mensaje);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Error Al Escribir En El Archivo: " + e.getMessage());
        }
    }
}
