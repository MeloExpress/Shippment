package com.example.MeloExpress.Shippment.repository;

import com.example.MeloExpress.Shippment.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends JpaRepository <Collect, Long> {
}
