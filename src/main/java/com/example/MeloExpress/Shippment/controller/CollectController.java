package com.example.MeloExpress.Shippment.controller;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.dto.CollectRequestDTO;
import com.example.MeloExpress.Shippment.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("collect")
public class CollectController {

    private CollectService collectService;

    @Autowired
    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @PostMapping("/collects")
    public ResponseEntity<CollectRequestDTO> createCollect(@RequestBody CollectRequestDTO collectRequestDto) {
        String customerCode = collectRequestDto.customerCode();
        LocalDateTime startTime = LocalDateTime.parse(collectRequestDto.startTime());
        LocalDateTime endTime = LocalDateTime.parse(collectRequestDto.endTime());

        Collect collect = collectService.createCollect(customerCode, startTime, endTime);

        CollectRequestDTO createdCollectDto = new CollectRequestDTO(collect);

        return new ResponseEntity<>(createdCollectDto, HttpStatus.CREATED);
    }
}