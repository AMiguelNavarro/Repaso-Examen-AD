package Utilidades;

import java.io.File;
import java.net.URL;

public class R {

    public static URL getURL(String nombre) {
        return Thread.currentThread().getContextClassLoader().getResource("interfaz" + File.separator + nombre);
    }

}
