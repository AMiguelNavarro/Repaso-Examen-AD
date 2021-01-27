package Controlador;

import Beans.Usuario;
import DAO.UsuariosDAO;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsuariosControlador implements Initializable {

    public TextField tfNombre, tfApellido;
    public Button btInsertar, btEliminar, btModificar, btExportar;
    public Label lbTexto;
    public ListView lvDatos;

    private UsuariosDAO usuariosDAO;
    private Usuario usuarioSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = new UsuariosDAO();
        try {
            usuariosDAO.conectar();
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void insertar(Event event) {

        String nombre = tfNombre.getText();
        String apellidos = tfApellido.getText();

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);

        try {
            usuariosDAO.insertarUsuario(usuario);
            cargarDatos();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    public void eliminar(Event event) {

        Usuario usuario = usuarioTextFields();

        try {
            usuariosDAO.eliminarUsuario(usuario);
            cargarDatos();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    public void modificar(Event event) {

        Usuario usuario = usuarioTextFields();

        try {
            if (usuarioSeleccionado == null) {
                System.out.println("Debes seleccionar un usuario del list view para modificarlo");
                return;
            }
            usuariosDAO.modificarUsuario(usuario, usuarioSeleccionado);
            cargarDatos();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    public void exportar(Event event) {

        final String NOMBRE = "Nombre";
        final String APELLIDOS = "Apellidos";

        try {

            FileChooser fileChooser = new FileChooser();
            File fichero = fileChooser.showSaveDialog(null);
            FileWriter fileWriter = new FileWriter(fichero);
            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.EXCEL.withHeader(NOMBRE, APELLIDOS));

            ArrayList<Usuario> usuarios = usuariosDAO.getUsuarios();

            for(Usuario usuario : usuarios) {
                printer.printRecord(usuario.getNombre(), usuario.getApellidos());
            }

            printer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void getUsuarioSeleccionado(Event event) {
        usuarioSeleccionado = (Usuario) lvDatos.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado == null) {
            System.out.println("No has seleccionado nada");
        }

        cargarUsuario(usuarioSeleccionado);
    }

    public void cargarDatos() {

        lvDatos.getItems().clear();

        try {
            ArrayList<Usuario> usuarios = usuariosDAO.getUsuarios();
            lvDatos.setItems(FXCollections.observableList(usuarios));

            limpiarCajas();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void limpiarCajas() {
        tfNombre.setText("");
        tfApellido.setText("");

        tfNombre.requestFocus();
    }

    public void cargarUsuario(Usuario usuario) {
        tfNombre.setText(usuario.getNombre());
        tfApellido.setText(usuario.getApellidos());
    }

    public Usuario usuarioTextFields() {
        Usuario usuario = new Usuario();

        if (tfNombre == null || tfApellido == null) {
            System.out.println("Debes seleccionar un usuario");
            return null;
        }

        usuario.setNombre(tfNombre.getText());
        usuario.setApellidos(tfApellido.getText());

        return usuario;
    }

}
