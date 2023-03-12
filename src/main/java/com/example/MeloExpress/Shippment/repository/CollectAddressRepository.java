package com.example.MeloExpress.Shippment.repository;


import com.example.MeloExpress.Shippment.domain.CollectAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectAddressRepository extends JpaRepository <CollectAddress, Long> {
}
