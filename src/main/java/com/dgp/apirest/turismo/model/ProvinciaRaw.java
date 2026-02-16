package com.dgp.apirest.turismo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

/**
 * Entidad que representa un documento de la colección "Provincias" en MongoDB.
 * Mapea la información geográfica y de servicios de los distritos.
 */
@Getter
@Setter
@Document(collection = "Provincias")
public class ProvinciaRaw {

    @Id
    private ObjectId mongoId;

    @Field("distrito_n")
    private Integer distritoN;

    @Field("nombre")
    private String nombre;

    @Field("bus_paradas_clave")
    private List<String> busParadasClave;

    @Field("colegios")
    private List<String> colegios;

    @Field("centros_comerciales")
    private List<String> centrosComerciales;
}