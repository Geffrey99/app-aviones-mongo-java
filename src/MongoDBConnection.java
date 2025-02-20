import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoDBConnection {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> vuelosCollection;


    //MONGO PARA LA CONEXION // iniciar servidor mongo ---cd/bin/mongoD
    public MongoDBConnection() {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("aviones");
        vuelosCollection = database.getCollection("vuelos");
    }

    //----PARA EL RETORNO DE VUELOS
    public MongoCollection<Document> getVuelosCollection() {
        return vuelosCollection;
    }

    //......PARA CERRAR LA CONEXION
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

