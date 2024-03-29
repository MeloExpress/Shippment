package com.example.MeloExpress.Shippment.controller;


import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
import com.example.MeloExpress.Shippment.dto.CollectResponseWithCustomerDTO;
import com.example.MeloExpress.Shippment.service.CollectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("collect")
public class CollectController {

    private final CollectService collectService;

    @Autowired
    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @PostMapping("/collects")
    public ResponseEntity<CollectResponseWithCustomerDTO> createCollectAndAddress(
            @RequestBody CollectCreateDTO collectCreateDTO) {
        CollectResponseWithCustomerDTO collectResponseDTO = collectService.createCollectAndAddress(collectCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(collectResponseDTO);
    }

    @GetMapping("/code/{collectCode}")
    public ResponseEntity<CollectResponseWithCustomerDTO> findCollectByCode(@PathVariable UUID collectCode) {
        CollectResponseWithCustomerDTO collectResponse = collectService.findCollectByCode(collectCode);
        return ResponseEntity.ok(collectResponse);
    }


    @PostMapping("/collects/{id}/cancel")
    public ResponseEntity<String> cancelCollect(@PathVariable("id") Long collectId) {
        try {
            collectService.cancelCollect(collectId);
            return ResponseEntity.ok("Coleta cancelada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/collects/{id}/completed")
    public ResponseEntity<String> okCollect(@PathVariable("id") Long collectId) {
        try {
            collectService.okCollect(collectId);
            return ResponseEntity.ok("Coleta Realizada.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/{collectCode}/route")
    public void routeCollectByCode(@PathVariable UUID collectCode) {
        collectService.routeCollect(collectCode);
    }


}