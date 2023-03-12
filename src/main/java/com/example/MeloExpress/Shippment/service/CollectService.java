package com.example.MeloExpress.Shippment.service;


import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectAddress;
import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
import com.example.MeloExpress.Shippment.dto.collectDetailsDTO;
import com.example.MeloExpress.Shippment.dto.CollectResponseDTO;
import com.example.MeloExpress.Shippment.repository.CollectAddressRepository;
import com.example.MeloExpress.Shippment.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;


@Service
public class CollectService {

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private CollectAddressRepository collectAddressRepository;

    @Autowired
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Transactional
    public CollectResponseDTO createCollectAndAddress(CollectCreateDTO collectCreateDTO) {
        CollectAddress collectAddress = new CollectAddress();
        CollectAddress savedCollectAddress = collectAddressRepository.save(collectAddress);

        Collect collect = new Collect(collectCreateDTO, collectAddress);
        collect.setCollectAddress(savedCollectAddress);
        Collect savedCollect = collectRepository.save(collect);

        collectDetailsDTO collectDetailsDTO = savedCollect.toCollectRequestDTO();

        CollectResponseDTO collectResponseDTO = new CollectResponseDTO(
                savedCollect.getCollectId(),
                savedCollect.getCustomerCode(),
                savedCollect.getCollectAddress().getCollectAddressId(),
                savedCollect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                savedCollect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        return collectResponseDTO;
    }

}




