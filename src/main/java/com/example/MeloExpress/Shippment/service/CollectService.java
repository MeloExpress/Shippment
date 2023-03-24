package com.example.MeloExpress.Shippment.service;


import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectAddress;
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



@Service
public class CollectService {

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private CollectAddressRepository collectAddressRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");



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
        collect.setCustomerCode(collectCreateDTO.customerCode());
        collect.setCollectAddress(savedCollectAddress);
        Collect savedCollect = collectRepository.save(collect);

        collectDetailsDTO collectDetailsDTO = savedCollect.toCollectRequestDTO();

        return new CollectResponseWithCustomerDTO(
                savedCollect.getCollectId(),
                savedCollect.getCollectAddress().getCollectAddressId(),
                savedCollect.getCustomerCode(),
                savedCollect.getCollectAddress().getAddressCode(),
                savedCollect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                savedCollect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
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

}




