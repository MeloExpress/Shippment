package com.example.MeloExpress.Shippment.domain;


import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
import com.example.MeloExpress.Shippment.dto.collectDetailsDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "collectId")
@Table(name = "collect")
@Entity(name = "collect")
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectId;

    private String customerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collect_address_id")
    private CollectAddress collectAddress;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Collect(CollectCreateDTO collectCreateDTO, CollectAddress collectAddress) {
        this.customerCode = collectCreateDTO.customerCode();
        this.collectAddress = collectAddress;
        this.startTime = LocalDateTime.parse(collectCreateDTO.startTime(), formatter);
        this.endTime = LocalDateTime.parse(collectCreateDTO.endTime(), formatter);
    }

    public collectDetailsDTO toCollectRequestDTO() {
        return new collectDetailsDTO(
                collectId,
                customerCode,
                collectAddress.getCollectAddressId(),
                startTime.format(formatter),
                endTime.format(formatter)
        );
    }
}
