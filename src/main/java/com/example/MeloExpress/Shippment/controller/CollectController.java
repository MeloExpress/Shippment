package com.example.MeloExpress.Shippment.controller;


import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
import com.example.MeloExpress.Shippment.dto.CollectResponseDTO;
import com.example.MeloExpress.Shippment.dto.CollectResponseWithCustomerDTO;
import com.example.MeloExpress.Shippment.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}