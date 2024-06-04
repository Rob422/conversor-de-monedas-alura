package principal;

import modulos.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {
    public static void main(String[] args) {
        String apiKey;
        String dirApi = "https://v6.exchangerate-api.com/v6/*colocar API_KEY*/USD";
        String dirPath = "apiKey.txt";
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"USDARS");
        map.put(2,"ARSUSD");
        map.put(3,"USDBRL");
        map.put(4,"BRLUSD");
        map.put(5,"USDCOP");
        map.put(6,"COPUSD");
        map.put(7,"USDMXN");
        map.put(8,"MXNUSD");
        map.put(9,"JPYMXN");
        map.put(10,"MXNJPY");

        String menu = """
                ****************************************
                Bienvenidos al Conversor de Moneda
                
                1) Dólar -----------> Peso Argentino
                2) Peso Argentino --> Dólar
                3) Dólar -----------> Real Brasileño
                4) Real Brasileño --> Dólar
                5) Dólar -----------> Peso Colombiano
                6) Peso Colombiano -> Dólar
                7) Dólar -----------> Peso Mexicano
                8) Peso Mexicano ---> Dólar
                9) Yen Japonés -----> Peso Mexicano
                10) Peso Mexicano --> Yen Japonés
                11) Salir!! ----------------------------
                
                Elija una opción:
                ****************************************""";

        String ejemploJson;

        ConectApi conectApi = new ConectApi();
        JsonParsing jsonParsing = new JsonParsing();
        Divisa divisa;
        Monedas monedas;
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[^(a-z0-9)]", Pattern.LITERAL);
        Matcher matcher;

        try {

            try {
                ArchivoKey archivoKey = new ArchivoKey (dirPath);
                apiKey = archivoKey.getApikey();
                matcher = pattern.matcher(apiKey);
                if (!matcher.find()){
                    System.out.println("...");
                }else {throw new NullPointerException();}
                ejemploJson = conectApi.getRespuesta(crearPeticion(dirApi, apiKey, "USD"));
            } catch (FileNotFoundException e){
                throw new FileNotFoundException();
            } catch (ConnectException e){
                throw new ConnectException();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

            monedas = jsonParsing.getMonedas(ejemploJson);
            divisa = new Divisa(monedas);
            double entrada;
            int opcion;

            do {

                System.out.println(menu);

                try {

                    opcion = scanner.nextInt();

                    if (opcion < 1 || opcion > 10){
                        if (opcion == 11){break;}
                        System.out.println("Opción no válida. Intente de nuevo.");
                        continue;
                    }
                    System.out.println("Ingrese el valor que desea convertir");
                    entrada = scanner.nextDouble();
                    divisa.setBase_code(map.get(opcion), entrada);
                    System.out.println("________________________________________");
                    System.out.println(divisa);

                }catch (InputMismatchException e){
                    System.out.println("Error con el valor ingresado. Intente de nuevo.");
                }
                scanner.nextLine();

            } while (true);

        }catch (IllegalArgumentException e){
            System.out.println("Error: Verifique la dirección de la API." + e.getMessage());
        }catch (NullPointerException e){
            System.out.println("Error: Verifique su API KEY.");
        }catch (FileNotFoundException e){
            System.out.println("Error: Verifique el archivo apiKey");
        }catch (ConnectException e){
            System.out.println("Error de conexion.");
        }

        scanner.close();
        System.out.println("El programa ha finalizado. Que tenga buen día!!");

    }

    private static String crearPeticion(String dir, String... args){
        for (String temp : args){
            dir = dir.replaceFirst("\\*", temp);
        }
        return dir;
    }
}

