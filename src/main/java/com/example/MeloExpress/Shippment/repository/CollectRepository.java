package com.example.MeloExpress.Shippment.repository;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectRepository extends JpaRepository <Collect, Long> {

    List<Collect> findByCollectState(CollectStates states);

    Optional<Collect> findByCollectCode(UUID collectCode);
}
