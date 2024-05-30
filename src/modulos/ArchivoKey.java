package modulos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArchivoKey {
    private final String apikey;
    public ArchivoKey(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        this.apikey = scanner.nextLine();
        scanner.close();
    }

    public String getApikey() {
        return apikey;
    }
}
