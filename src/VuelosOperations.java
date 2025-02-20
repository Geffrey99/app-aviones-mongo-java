import java.util.Scanner;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

public class VuelosOperations {
    //mongooooDB
    private final MongoDBConnection dbConnection;
    private final MongoCollection<Document> vuelosCollection;

    public VuelosOperations() {
        this.dbConnection = new MongoDBConnection();
        this.vuelosCollection = dbConnection.getVuelosCollection();
    }

    // Obtener destino del vuelo IB706
    /*
    > db.vuelos.find({ "codigo": "IB706" }, { "destino": 1 })
{ "_id" : ObjectId("67ae091b5976b5b61361e05e"), "destino" : "BARCELONA" }
>
     */

    public void getDestinoVuelo(String codigoVuelo) {
        Document vuelo = vuelosCollection.find(eq("codigo", codigoVuelo)) // utilizo un filtro (eq("codigo", codigoVuelo)) para encontrar documentos donde el campo codigo sea igual a codigoVuelo
                .projection(fields(include("destino"), excludeId())) //proyección para incluir solo el campo destino en los resultados y excluir el campo _id
                .first(); //Obtiene el primer documento que coincide con la consulta
        if (vuelo != null) {
            System.out.println("Destino del vuelo " + codigoVuelo + ": " + vuelo.getString("destino"));
        } else {
            System.out.println("Vuelo no encontrado.");
        }
    }

    // Obtener billetes vendidos del vuelo IB706
    public void getBilletesVendidos(String codigoVuelo) {
        Document vuelo = vuelosCollection.find(eq("codigo", codigoVuelo)).first();
        if (vuelo != null) {
            System.out.println("Billetes vendidos del vuelo " + codigoVuelo + ": " + vuelo.get("vendidos").toString());
        } else {
            System.out.println("Vuelo no encontrado.");
        }
    }

    // Obtener nombre del pasajero del asiento 2 del vuelo IB706 (MADRID-BARCELONA)----------------------------------
    public void getNombrePasajeroAsiento(String codigoVuelo, int asiento) {
        Document vuelo = vuelosCollection.find(eq("codigo", codigoVuelo)).first();
        if (vuelo != null) {
            for (Document vendido : (List<Document>) vuelo.get("vendidos")) {
                if (vendido.getInteger("asiento") == asiento) {
                    System.out.println("Nombre del pasajero del asiento " + asiento + " del vuelo " + codigoVuelo + ": " + vendido.getString("nombre"));
                    return;
                }
            }
            System.out.println("Asiento no vendido.");
        } else {
            System.out.println("Vuelo no encontrado.");
        }
    }

    // Vender un billete del vuelo a Murcia--------------------------------------------------------------------
    public void venderBillete(String codigoVuelo, Document compra) {
        Document vuelo = vuelosCollection.find(eq("codigo", codigoVuelo)).first();
        if (vuelo != null) {
            vuelosCollection.updateOne(eq("codigo", codigoVuelo), push("vendidos", compra));
            vuelosCollection.updateOne(eq("codigo", codigoVuelo), inc("plazas_disponibles", -1));
            vuelosCollection.updateOne(eq("codigo", codigoVuelo), pull("asientos_disponibles", compra.getInteger("asiento")));
            System.out.println("Billete vendido con éxito.");
        } else {
            System.out.println("Vuelo no encontrado.");
        }
    }

    // Cambiar número de disponibles-----------------------------------------------------------------------------
    public void cambiarNumeroDisponibles(String codigoVuelo, int nuevoNumero) {
        vuelosCollection.updateOne(eq("codigo", codigoVuelo), set("plazas_disponibles", nuevoNumero));
        System.out.println("Número de plazas disponibles actualizado.");
    }

    // Cancelar la compra del asiento 2---------------------------------------------------------------------------
    public void cancelarCompra(String codigoVuelo, int asiento) {
        Document vuelo = vuelosCollection.find(eq("codigo", codigoVuelo)).first();
        if (vuelo != null) {
            for (Document vendido : (List<Document>) vuelo.get("vendidos")) {
                if (vendido.getInteger("asiento") == asiento) {
                    vuelosCollection.updateOne(eq("codigo", codigoVuelo), pull("vendidos", vendido));
                    vuelosCollection.updateOne(eq("codigo", codigoVuelo), inc("plazas_disponibles", 1));
                    vuelosCollection.updateOne(eq("codigo", codigoVuelo), push("asientos_disponibles", asiento));
                    System.out.println("Compra cancelada con éxito.");
                    return;
                }
            }
            System.out.println("Asiento no encontrado en los vendidos.");
        } else {
            System.out.println("Vuelo no encontrado.");
        }
    }

    public void close() {
        dbConnection.close();
    }


}