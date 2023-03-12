package com.example.MeloExpress.Shippment.domain;

import jakarta.persistence.*;
import lombok.*;

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

}
