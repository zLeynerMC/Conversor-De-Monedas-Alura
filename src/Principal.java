import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        List<String> monedas = new ArrayList<>(List.of("USD", "EUR", "ARG", "COP", "MXN", "BRL", "PEN"));
        GestorDeMonedas gestor = new GestorDeMonedas(monedas);
        ApiHandler apiHandler = new ApiHandler("https://v6.exchangerate-api.com/v6/e6b35f50853c81d679afa651/latest/");

        boolean continuar = true;
        while (continuar) {
            Scanner lectura = new Scanner(System.in);

            System.out.println("""
                
                
                   ____                                           ____         __  __                      _          \s
                  / ___|___  _ ____   _____ _ __ ___  ___  _ __  |  _ \\  ___  |  \\/  | ___  _ __   ___  __| | __ _ ___\s
                 | |   / _ \\| '_ \\ \\ / / _ \\ '__/ __|/ _ \\| '__| | | | |/ _ \\ | |\\/| |/ _ \\| '_ \\ / _ \\/ _` |/ _` / __|
                 | |__| (_) | | | \\ V /  __/ |  \\__ \\ (_) | |    | |_| |  __/ | |  | | (_) | | | |  __/ (_| | (_| \\__ \\
                  \\____\\___/|_| |_|\\_/ \\___|_|  |___/\\___/|_|    |____/ \\___| |_|  |_|\\___/|_| |_|\\___|\\__,_|\\__,_|___/
                                                                                                                      \s
                *******************************************************************************************************
                """);
            System.out.println("Opciones:");
            System.out.println("1. Convertir Moneda");
            System.out.println("2. Conversión Personalizada");
            System.out.println("3. Salir");
            System.out.println("************************");
            System.out.println("\nSeleccione una opción: ");

            String opcion = lectura.nextLine();

            switch (opcion) {
                case "1":
                    realizarConversion(gestor, apiHandler);
                    break;
                case "2":
                    realizarConversionPersonalizada(gestor, apiHandler);
                    break;
                case "3":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo nuevamente.");
            }

            if (continuar) {
                System.out.println("¿Deseas continuar? (si/no)");
                String opcion2 = lectura.nextLine();
                if (!opcion2.equalsIgnoreCase("si")) {
                    continuar = false;
                    System.out.println("♡Gracias Por Usar Nuestro Servicio, Esperamos Que Regrese Pronto♡");
                }
            }
        }
    }

    public static void realizarConversion(GestorDeMonedas gestor, ApiHandler apiHandler) {
        String monedaOrigen = gestor.seleccionarMonedas("Primera");
        String monedaDestino = gestor.seleccionarMonedas("Segunda");
        double monto = gestor.ingresarValor();
        String respuestaApi = apiHandler.BuscarTasaDeCambio(monedaOrigen);

        if (respuestaApi != null) {
            Gson gson = new Gson();
            Map<String, Object> jsonResponsive = gson.fromJson(respuestaApi, Map.class);
            Gson gson1 = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();

            var jsonreponse = gson1.fromJson(respuestaApi, Map.class);
            double tasaDeCambio = obtenerTasaDesdeJson(jsonreponse, monedaDestino);
            double resultado = monto * tasaDeCambio;

            System.out.printf("Se convirtió " + monto + " " + monedaOrigen + " a " + resultado + " " + monedaDestino);
            Registro.registrarConversacion("Se convirtió " + monto + " " + monedaOrigen + " a " + resultado + " " + monedaDestino);
        }
    }

    private static void realizarConversionPersonalizada(GestorDeMonedas gestor, ApiHandler apiHandler) {
        Scanner lectura = new Scanner(System.in);
        System.out.println("""
                ******************************
                Monedas Disponibles:
                AED   Dírham de los Emiratos Árabes Unidos |  BIF   Franco burundiano              |  BMD   Dólar bermudiano
                AFN   Afgani afgano                        |  BND   Dólar bruneano                 |  BND   Dólar bruneano
                ALL   Lek Albanés                          |  BOB   Boliviano boliviano            |  BOB   Boliviano boliviano
                AMD   Dram Armenio                         |  BWP   Pula botsuano                  |  BZD   Dólar de Belice
                ANG   Florín antillano neerlandés          |  BYN   Rublo bielorruso               |  CAD   Dólar canadiense
                AOA   Kwanza angoleño                      |  BZD   Dólar de Belice                |  CDF   Franco congoleño
                ARS   Peso Argentino                       |  CAD   Dólar canadiense               |  CHF   Franco suizo
                AUD   Dólar Australiano                    |  CDF   Franco congoleño               |  CLP   Peso chileno
                AWG   Florín arubeño                       |  CHF   Franco suizo                  |  CNY   Yuan chino
                AZN   Manat azerbaiyano                    |  CLP   Peso chileno                   |  COP   Peso colombiano
                BAM   Marco bosnioherzegovino              |  CNY   Yuan chino                     |  CRC   Colón costarricense
                BBD   Dólar barbadeño                      |  COP   Peso colombiano                |  CVE   Escudo caboverdiano
                BDT   Taka bangladeshiano                   |  CRC   Colón costarricense            |  CZK   Corona checa
                BGN   Lev búlgaro                          |  CVE   Escudo caboverdiano            |  DJF   Franco yibutiano
                BHD   Dinar bahreiní                        |  CZK   Corona checa                   |  DKK   Corona danesa
                BIF   Franco burundiano                    |  DJF   Franco yibutiano               |  DOP   Peso dominicano
                BMD   Dólar bermudiano                     |  DKK   Corona danesa                  |  DZD   Dinar argelino
                BND   Dólar bruneano                       |  DOP   Peso dominicano                |  EGP   Libra egipcia
                BOB   Boliviano boliviano                  |  DZD   Dinar argelino                 |  ERN   Nakfa eritreo
                BRL   Real brasileño                       |  EGP   Libra egipcia                  |  ETB   Birr etíope
                BSD   Dólar de las Bahamas                 |  ERN   Nakfa eritreo                  |  EUR   Euro
                BTN   Ngultrum butanés                     |  ETB   Birr etíope                    |  FJD   Dólar fiyiano
                BWP   Pula botsuano                        |  EUR   Euro                          |  FKP   Libra de las Malvinas
                BYN   Rublo bielorruso                     |  FJD   Dólar fiyiano                  |  FOK   Corona de las Islas Feroe
                BZD   Dólar de Belice                      |  FKP   Libra de las Malvinas          |  GBP   Libra esterlina
                CAD   Dólar canadiense                     |  FOK   Corona de las Islas Feroe      |  GEL   Lari georgiano
                CDF   Franco congoleño                     |  GBP   Libra esterlina                |  GGP   Libra de Guernsey
                CHF   Franco suizo                         |  GEL   Lari georgiano                 |  GHS   Cedi ghanés
                CLP   Peso chileno                         |  GGP   Libra de Guernsey              |  GIP   Libra de Gibraltar
                CNY   Yuan chino                           |  GHS   Cedi ghanés                    |  GMD   Dalasi gambiano
                COP   Peso colombiano                      |  GIP   Libra de Gibraltar             |  GNF   Franco guineano
                CRC   Colón costarricense                  |  GMD   Dalasi gambiano                |  GTQ   Quetzal guatemalteco
                CVE   Escudo caboverdiano                  |  GNF   Franco guineano                |  GYD   Dólar guyanés
                CZK   Corona checa                         |  GTQ   Quetzal guatemalteco           |  HKD   Dólar de Hong Kong
                DJF   Franco yibutiano                     |  GYD   Dólar guyanés                  |  HNL   Lempira hondureño
                DKK   Corona danesa                        |  HKD   Dólar de Hong Kong             |  HRK   Kuna croata
                DOP   Peso dominicano                      |  HNL   Lempira hondureño              |  HTG   Gourde haitiano
                DZD   Dinar argelino                       |  HRK   Kuna croata                    |  HUF   Forint húngaro
                EGP   Libra egipcia                        |  HTG   Gourde haitiano                |  IDR   Rupia indonesia
                ERN   Nakfa eritreo                         |  HUF   Forint húngaro                 |  ILS   Shekel israelí
                ETB   Birr etíope                          |  IDR   Rupia indonesia                |  IMP   Libra de la Isla de Man
                EUR   Euro                                 |  ILS   Shekel israelí                 |  INR   Rupia india
                FJD   Dólar fiyiano                        |  IMP   Libra de la Isla de Man        |  IQD   Dinar iraquí
                FKP   Libra de las Malvinas                |  INR   Rupia india                    |  IRR   Rial iraní
                FOK   Corona de las Islas Feroe            |  IQD   Dinar iraquí                   |  ISK   Corona islandesa
                GBP   Libra esterlina                      |  IRR   Rial iraní                     |  JEP   Libra de Jersey
                GEL   Lari georgiano                       |  ISK   Corona islandesa               |  JMD   Dólar jamaicano
                
                """);
        System.out.println("Ingrese El Nombre De La Moneda Que Desea Convertir: ");
        String monedaOrigen = lectura.nextLine().toUpperCase();
        System.out.println("Ingrese El Nombre De La Moneda Que Desea Hacer La Conversion: ");
        String monedaDestino = lectura.nextLine().toUpperCase();
        double monto = gestor.ingresarValor();
        String respuestaApi = apiHandler.BuscarTasaDeCambio(monedaOrigen);


        if (respuestaApi != null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();

            var jsonreponse = gson.fromJson(respuestaApi, Map.class);
            double tasaDeCambio = obtenerTasaDesdeJson(jsonreponse, monedaDestino);
            double resultado = monto * tasaDeCambio;
            double montoConvertido = convertir(monedaOrigen, monedaDestino, monto, jsonreponse);

            System.out.printf("Se convirtió " + monto + " " + monedaOrigen + " a " + montoConvertido + " " + monedaDestino);
            System.out.println("la taza de cambio fue de: " + tasaDeCambio);
            Registro.registrarConversacion("Se convirtió " + monto + " " + monedaOrigen + " a " + montoConvertido + " " + monedaDestino);
        }
    }

    private static double obtenerTasaDesdeJson(Map<String, Object> jsonResponse, String monedaDestino) {
        if (jsonResponse.containsKey("conversion_rates")) {
            Map<String, Double> conversationRates = (Map<String, Double>) jsonResponse.get("conversion_rates");
            if (conversationRates != null) {
                return conversationRates.getOrDefault(monedaDestino, 1.0);
            }
        }
        System.out.println("No Se Encontro La Tasa De Cambio Para " + monedaDestino);
        return 1.0;
    }

    private static double convertir(String monedaOrigen, String monedaDestino, Double monto, Map<String, Object> jsonResponse){
        if (monedaOrigen != null && monedaDestino != null && monto != null && jsonResponse != null){
            double tasaDeCambio = obtenerTasaDesdeJson(jsonResponse, monedaDestino);
            return monto * tasaDeCambio;
        }
        return 0.0;
    }

}
