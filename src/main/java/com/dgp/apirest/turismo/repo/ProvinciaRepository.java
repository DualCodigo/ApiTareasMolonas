package com.dgp.apirest.turismo.repo;


import com.dgp.apirest.turismo.model.ProvinciaRaw;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends MongoRepository<ProvinciaRaw, String> {

    // El método que usaba tu OpenRtaController original:
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Page<ProvinciaRaw> searchByNombreRegex(String regex, Pageable pageable);
}