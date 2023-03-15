package com.example.MeloExpress.Shippment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "collectAddressId")
@Table(name = "collect_address")
@Entity(name = "collectAddress")
public class CollectAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectAddressId;
    @Column(name = "address_code")
    private UUID addressCode;

    public CollectAddress(UUID addressCode) {
        this.addressCode = addressCode;
    }
}