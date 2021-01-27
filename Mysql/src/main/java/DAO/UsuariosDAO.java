package DAO;

import Beans.Usuario;
import Utilidades.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class UsuariosDAO {

    private final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";

    private Connection conexion;

    public void conectar() throws IOException, ClassNotFoundException, SQLException {
        Properties configuracion = new Properties();
        configuracion.load(R.getProperties("bd"));

        String host = configuracion.getProperty("host");
        String port = configuracion.getProperty("port");
        String namedb = configuracion.getProperty("namedb");
        String user = configuracion.getProperty("user");
        String password = configuracion.getProperty("password");

        Class.forName(CONTROLADOR);
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + namedb + "?serverTimezone=UTC", user , password);
    }


    public ArrayList<Usuario> getUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        final String SQL_FINDALL = "SELECT * FROM usuarios WHERE 1=1";

        PreparedStatement ps = conexion.prepareStatement(SQL_FINDALL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();

            usuario.setNombre(rs.getString(1));
            usuario.setApellidos(rs.getString(2));

            usuarios.add(usuario);
        }

        return usuarios;
    }


    public void insertarUsuario(Usuario usuario) throws SQLException {

        String sql_insert = "INSERT INTO usuarios (nombre, apellidos) VALUES (?,?)";

        PreparedStatement ps = conexion.prepareStatement(sql_insert);
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getApellidos());

        ps.execute();

    }

    public void eliminarUsuario(Usuario usuario) throws SQLException {

        String sql_delete = "DELETE FROM usuarios WHERE nombre = ? AND apellidos = ?";

        PreparedStatement ps = conexion.prepareStatement(sql_delete);
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getApellidos());

        ps.execute();

    }

    public void modificarUsuario(Usuario usuarioNuevo, Usuario usuarioViejo) throws SQLException {

        String sql_update = "UPDATE usuarios SET nombre = ?, apellidos = ? WHERE nombre = ? AND apellidos = ?";

        PreparedStatement ps = conexion.prepareStatement(sql_update);
        ps.setString(1, usuarioNuevo.getNombre());
        ps.setString(2, usuarioNuevo.getApellidos());
        ps.setString(3, usuarioViejo.getNombre());
        ps.setString(4, usuarioViejo.getApellidos());

        ps.execute();

    }

}
