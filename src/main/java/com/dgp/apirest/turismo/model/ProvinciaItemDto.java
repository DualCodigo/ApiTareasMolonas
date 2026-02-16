package com.dgp.apirest.turismo.model;

import java.util.List;

/**
 * Objeto de transferencia de datos (DTO) para la representación de distritos turísticos.
 * Este record define la estructura JSON de salida para las consultas de la API.
 * * @param oid                Identificador único de MongoDB en formato String hexadecimal.
 * @param distritoN          Número identificador del distrito (campo distrito_n en DB).
 * @param nombre             Nombre descriptivo del distrito (ej: Centro, Este).
 * @param busParadasClave    Lista de paradas de autobús principales en la zona.
 * @param colegios           Listado de centros educativos disponibles en el distrito.
 * @param centrosComerciales Listado de áreas y centros comerciales.
 */
public record ProvinciaItemDto(
        String oid,
        Integer distritoN,
        String nombre,
        List<String> busParadasClave,
        List<String> colegios,
        List<String> centrosComerciales
) {}
