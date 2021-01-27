package Utilidades;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static URL getUI(String name){
        return Thread.currentThread().getContextClassLoader().getResource("interfaz" + File.separator+ name + ".fxml");
    }


    public static InputStream getProperties(String name){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("configuracion" + File.separator+ name + ".properties");
    }

}
