package com.dgp.apirest.turismo.api;

import com.dgp.apirest.turismo.model.ProvinciaItemDto;
import com.dgp.apirest.turismo.service.TurismoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turismo")
@RequiredArgsConstructor
public class ProvinciasController {

    private final TurismoSearchService searchService;

    @GetMapping("/search")
    public List<ProvinciaItemDto> search(@RequestParam("q") String q,
                                         @RequestParam(defaultValue = "10") int limit) {
        return searchService.searchByNombre(q, limit);
    }
    @GetMapping("/{oid}")
    public ProvinciaItemDto getById(@PathVariable String oid) {
        return searchService.findByOid(oid);
    }
}