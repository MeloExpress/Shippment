package com.example.MeloExpress.Shippment.service;


import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectAddress;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import com.example.MeloExpress.Shippment.dto.*;
import com.example.MeloExpress.Shippment.repository.CollectAddressRepository;
import com.example.MeloExpress.Shippment.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CollectService {

    @Autowired
    private CollectStates collectStates;

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private CollectAddressRepository collectAddressRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final CollectStateService stateMachineService;

    @Autowired
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");




    public CollectService(RestTemplate restTemplate,
                          CollectRepository collectRepository,
                          CollectAddressRepository collectAddressRepository,
                          CollectStateService collectStateService) {
        this.restTemplate = restTemplate;
        this.collectRepository = collectRepository;
        this.collectAddressRepository = collectAddressRepository;
        this.stateMachineService = collectStateService;
    }

    public List<Collect> findCollectsByStatus(CollectStates status) {
        return collectRepository.findByCollectState(status);
    }


    @Transactional
    public Collect save(Collect collect) {
        return collectRepository.save(collect);
    }



    @Transactional
    public CollectResponseWithCustomerDTO createCollectAndAddress(CollectCreateDTO collectCreateDTO) {
        ResponseEntity<CustomerDetailsFindDTO> customerResponse = restTemplate.getForEntity(
                "http://localhost:8080/customers/code/" + collectCreateDTO.customerCode(),
                CustomerDetailsFindDTO.class);
        if (customerResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("Cliente não encontrado");
        }
        CustomerDetailsFindDTO customerDetails = customerResponse.getBody();

        ResponseEntity<AddressDetailsFindDTO> addressResponse = restTemplate.getForEntity(
                "http://localhost:8080/customers/" + customerDetails.customerId() + "/addresses/code/" + collectCreateDTO.addressCode(),
                AddressDetailsFindDTO.class);
        if (addressResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("Endereço não encontrado");
        }
        AddressDetailsFindDTO addressDetails = addressResponse.getBody();

        CollectAddress collectAddress = new CollectAddress();
        collectAddress.setAddressCode(collectCreateDTO.addressCode());
        CollectAddress savedCollectAddress = collectAddressRepository.save(collectAddress);

        Collect collect = new Collect(collectCreateDTO, collectAddress);
        collect.setCollectCode(UUID.randomUUID());
        collect.setCustomerCode(collectCreateDTO.customerCode());
        collect.setCollectState(CollectStates.AGENDADA);
        collect.setCollectAddress(savedCollectAddress);
        Collect savedCollect = collectRepository.save(collect);

        CollectDetailsDTO collectDetailsDTO = savedCollect.toCollectRequestDTO();

        return new CollectResponseWithCustomerDTO(
                savedCollect.getCollectId(),
                savedCollect.getCollectCode(),
                savedCollect.getCollectAddress().getCollectAddressId(),
                savedCollect.getCustomerCode(),
                savedCollect.getCollectAddress().getAddressCode(),
                savedCollect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                savedCollect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                savedCollect.getCollectState().toString(),
                new CustomerDetailsFindDTO(
                        customerDetails.customerId(),
                        customerDetails.customerCode(),
                        customerDetails.companyName().toUpperCase(),
                        customerDetails.cnpj(),
                        customerDetails.stateRegistration(),
                        customerDetails.email(),
                        customerDetails.phone(),
                        customerDetails.responsible().toUpperCase()),
                new AddressDetailsFindDTO(
                        addressDetails.addressId(),
                        addressDetails.addressCode(),
                        addressDetails.zipCode(),
                        addressDetails.street().toUpperCase(),
                        addressDetails.number(),
                        addressDetails.complements().toUpperCase(),
                        addressDetails.district().toUpperCase(),
                        addressDetails.city().toUpperCase(),
                        addressDetails.state().toUpperCase(),
                        addressDetails.pointReference().toUpperCase()
                        )
        );

    }

    @Transactional(readOnly = true)
    public CollectResponseWithCustomerDTO findCollectByCode(UUID collectCode) {
        Collect collect = (Collect) collectRepository.findByCollectCode(collectCode)
                .orElseThrow(() -> new RuntimeException("Collect não encontrado"));
        ResponseEntity<CustomerDetailsFindDTO> customerResponse = restTemplate.getForEntity(
                "http://localhost:8080/customers/code/" + collect.getCustomerCode(),
                CustomerDetailsFindDTO.class);
        if (customerResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("Cliente não encontrado");
        }
        CustomerDetailsFindDTO customerDetails = customerResponse.getBody();

        ResponseEntity<AddressDetailsFindDTO> addressResponse = restTemplate.getForEntity(
                "http://localhost:8080/customers/" + customerDetails.customerId() + "/addresses/code/" + collect.getCollectAddress().getAddressCode(),
                AddressDetailsFindDTO.class);
        if (addressResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("Endereço não encontrado");
        }
        AddressDetailsFindDTO addressDetails = addressResponse.getBody();

        return new CollectResponseWithCustomerDTO(
                collect.getCollectId(),
                collect.getCollectCode(),
                collect.getCollectAddress().getCollectAddressId(),
                collect.getCustomerCode(),
                collect.getCollectAddress().getAddressCode(),
                collect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                collect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                collect.getCollectState().toString(),
                new CustomerDetailsFindDTO(
                        customerDetails.customerId(),
                        customerDetails.customerCode(),
                        customerDetails.companyName().toUpperCase(),
                        customerDetails.cnpj(),
                        customerDetails.stateRegistration(),
                        customerDetails.email(),
                        customerDetails.phone(),
                        customerDetails.responsible().toUpperCase()),
                new AddressDetailsFindDTO(
                        addressDetails.addressId(),
                        addressDetails.addressCode(),
                        addressDetails.zipCode(),
                        addressDetails.street().toUpperCase(),
                        addressDetails.number(),
                        addressDetails.complements().toUpperCase(),
                        addressDetails.district().toUpperCase(),
                        addressDetails.city().toUpperCase(),
                        addressDetails.state().toUpperCase(),
                        addressDetails.pointReference().toUpperCase()
                )
        );
    }


    @Transactional
    public void cancelCollect(Long collectId) {
        Optional<Collect> collectOptional = collectRepository.findById(collectId);
        if (collectOptional.isEmpty()) {
            throw new RuntimeException("Coleta não encontrada");
        }
        Collect collect = collectOptional.get();
        collect.setCollectState(CollectStates.CANCELADA);
        collectRepository.save(collect);
    }

    @Transactional
    public void okCollect(Long collectId) {
        Optional<Collect> collectOptional = collectRepository.findById(collectId);
        if (collectOptional.isEmpty()) {
            throw new RuntimeException("Coleta não encontrada");
        }
        Collect collect = collectOptional.get();
        collect.setCollectState(CollectStates.REALIZADA);
        collectRepository.save(collect);
    }

    @Transactional
    public void routeCollect(UUID collectCode) {
        Optional<Collect> collectOptional = collectRepository.findByCollectCode(collectCode);
        if (collectOptional.isEmpty()) {
            throw new RuntimeException("Coleta não encontrada");
        }

        Collect collect = collectOptional.get();
        collect.setCollectState(CollectStates.ROTEIRIZADA);
        collectRepository.save(collect);
    }


    @Transactional
    public void updateCollectStatus(String collectCode) {
        try {
            UUID collectUUID = UUID.fromString(collectCode);
            Optional<Collect> collectOptional = collectRepository.findByCollectCode(collectUUID);

            if (collectOptional.isPresent()) {
                Collect collect = collectOptional.get();
                collect.setCollectState(CollectStates.ROTEIRIZADA);
                collectRepository.save(collect);
            } else {
                throw new RuntimeException("Coleta não encontrada");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Código de coleta inválido");
        }
    }

}




