package com.dgp.apirest.turismo.service;

import com.dgp.apirest.turismo.model.ProvinciaItemDto;
import com.dgp.apirest.turismo.model.ProvinciaRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Servicio de búsqueda y gestión para el módulo de Turismo.
 * Implementa consultas paginadas y filtrado por nombre sobre la colección "Provincias".
 */
@Service
@RequiredArgsConstructor
public class TurismoSearchService {

    private final MongoTemplate mongoTemplate;
    private static final String COLLECTION = "Provincias";

    /**
     * Recupera todos los registros de la base de datos de forma paginada.
     * * @param pageable Configuración de paginación (página y tamaño).
     *
     * @return Página de resultados convertidos a DTO.
     */
    public Page<ProvinciaItemDto> findAllPaged(Pageable pageable) {
        // Contamos el total para que el objeto Page sepa cuántas páginas hay en total
        long total = mongoTemplate.count(new Query(), ProvinciaRaw.class, COLLECTION);

        // Ejecutamos la consulta con el límite y salto (skip) de la paginación
        Query query = new Query().with(pageable);
        List<ProvinciaRaw> results = mongoTemplate.find(query, ProvinciaRaw.class, COLLECTION);

        List<ProvinciaItemDto> dtoList = results.stream().map(this::mapToDto).toList();
        return new PageImpl<>(dtoList, pageable, total);
    }

    /**
     * Busca provincias por nombre con un límite máximo de resultados.
     * * @param term Texto a buscar (insensible a mayúsculas).
     *
     * @param limit Cantidad máxima de resultados.
     * @return Lista de DTOs encontrados.
     */
    public List<ProvinciaItemDto> searchByNombre(String term, int limit) {
        if (term == null || term.trim().isEmpty()) {
            return List.of();
        }

        Pattern p = Pattern.compile(".*" + Pattern.quote(term.trim()) + ".*", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("nombre").regex(p)).limit(limit);

        List<ProvinciaRaw> results = mongoTemplate.find(query, ProvinciaRaw.class, COLLECTION);
        return results.stream().map(this::mapToDto).toList();
    }

    /**
     * Método privado para convertir la entidad de MongoDB al formato de salida JSON.
     * * @param raw Entidad directa de la base de datos.
     * @return DTO con el formato solicitado (oid, distritoN, etc.).
     */
    /**
     * Convierte una entidad de base de datos ProvinciaRaw al formato DTO ProvinciaItemDto.
     * * @param raw La entidad original recuperada de MongoDB.
     *
     * @return El DTO con el formato de salida estandarizado.
     */
    private ProvinciaItemDto mapToDto(ProvinciaRaw raw) {
        return new ProvinciaItemDto(
                raw.getMongoId() != null ? raw.getMongoId().toHexString() : null,
                raw.getDistritoN(),
                raw.getNombre(),
                raw.getBusParadasClave() != null ? raw.getBusParadasClave() : new java.util.ArrayList<>(),
                raw.getColegios() != null ? raw.getColegios() : new java.util.ArrayList<>(),
                raw.getCentrosComerciales() != null ? raw.getCentrosComerciales() : new java.util.ArrayList<>()
        );
    }
    public ProvinciaItemDto findByOid(String oid) {
        com.dgp.apirest.turismo.model.ProvinciaRaw raw = mongoTemplate.findById(
                new org.bson.types.ObjectId(oid),
                com.dgp.apirest.turismo.model.ProvinciaRaw.class,
                "Provincias"
        );
        return raw != null ? mapToDto(raw) : null;

    }
}