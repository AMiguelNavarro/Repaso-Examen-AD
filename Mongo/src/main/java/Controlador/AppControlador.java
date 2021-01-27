package Controlador;

import Beans.Usuario;
import DAO.UsuariosDAO;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppControlador implements Initializable {

    public TextField tfNombre, tfApellido;
    public Button btInsertar, btEliminar, btModificar;
    public ListView lvDatos;

    private UsuariosDAO usuariosDAO;
    private Usuario usuarioSeleccionado;

    public AppControlador() {
    }

    @FXML
    public void insertar(Event event){
        Usuario usuario = new Usuario();
        usuario.setNombre(tfNombre.getText());
        usuario.setApellido(tfApellido.getText());

        usuariosDAO.insertarUsuario(usuario);
        cargarDatos();
    }

    @FXML
    public void eliminar(Event event){
        Usuario usuario = usuarioSeleccionado;
        if (usuario == null) {
            System.out.println("El usuario no puede ser nulo");
        }
        usuariosDAO.eliminarUsuario(usuario);
        cargarDatos();
    }

    @FXML
    public void modificar(Event event){
        Usuario usuarioAntiguo = usuarioSeleccionado;
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre(tfNombre.getText());
        usuarioNuevo.setApellido(tfApellido.getText());
        usuarioNuevo.setId(usuarioSeleccionado.getId());

        usuariosDAO.modificarUsuario(usuarioAntiguo, usuarioNuevo);
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = new UsuariosDAO();
        usuariosDAO.conectarCodeRegistry();
        cargarDatos();
    }

    @FXML
    public void getUsuarioSeleccionado() {
        usuarioSeleccionado = (Usuario) lvDatos.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado == null) {
            System.out.println("No has seleccionado nada");
        }
        cargarUsuario(usuarioSeleccionado);
    }

    public void cargarDatos() {
        lvDatos.getItems().clear();
        ArrayList<Usuario> usuarios = usuariosDAO.getListaUsuarios();
        lvDatos.setItems(FXCollections.observableList(usuarios));
    }

    public void cargarUsuario(Usuario usuario) {
        tfNombre.setText(usuario.getNombre());
        tfApellido.setText(usuario.getApellido());
    }

    public Usuario usuarioTextField() {
         Usuario usuario = new Usuario();

         if (tfNombre == null || tfApellido == null) {
             System.out.println("No puedes dejar los campos vacios");
             return null;
         }

         usuario.setNombre(tfNombre.getText());
         usuario.setApellido(tfApellido.getText());

         return  usuario;

    }

}
