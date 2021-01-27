package DAO;

import Beans.Usuario;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOptions;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;


public class UsuariosDAO {

    private final String NOMBRE_DB = "repasoExamen";
    private final String COLLECTION = "prueba";

    private MongoClient cliente;
    private MongoDatabase db;
    private MongoCollection<Usuario> collection;

//
//    public void conectar() {
//        mongoClient = new MongoClient();
//        db = mongoClient.getDatabase(NOMBRE_DB);
//    }

    public void conectarCodeRegistry() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        cliente = new MongoClient("localhost",
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        db = cliente.getDatabase(NOMBRE_DB);
    }

    public void desconectar() {
        cliente.close();
    }

    public ArrayList<Usuario> getListaUsuarios() {

        MongoCollection<Usuario> collection = db.getCollection(COLLECTION , Usuario.class);
        ArrayList<Usuario> usuarios = collection.find().into(new ArrayList<>());

        return usuarios;

    }

    public void insertarUsuario(Usuario usuario) {
        collection = db.getCollection(COLLECTION, Usuario.class);
        collection.insertOne(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        collection = db.getCollection(COLLECTION, Usuario.class);
        collection.deleteOne(eq("_id" , usuario.getId()));
    }

    public void modificarUsuario(Usuario usuarioAntiguo, Usuario usuarioNuevo) {
        collection = db.getCollection(COLLECTION, Usuario.class);
        collection.replaceOne(eq("_id", usuarioAntiguo.getId()), usuarioNuevo);
    }
}
