package com.example.MeloExpress.Shippment.domain;


import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
import com.example.MeloExpress.Shippment.dto.CollectDetailsDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    private UUID collectCode;

    private UUID customerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collect_address_id")
    private CollectAddress collectAddress;

    @Enumerated(EnumType.STRING)
    private CollectStates collectState;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Collect(CollectCreateDTO collectCreateDTO, CollectAddress collectAddress) {
        this.collectCode = collectCreateDTO.collectCode();
        this.customerCode = collectCreateDTO.customerCode();
        this.collectAddress = collectAddress;
        this.collectState = CollectStates.AGENDADA;
        this.startTime = LocalDateTime.parse(collectCreateDTO.startTime(), formatter);
        this.endTime = LocalDateTime.parse(collectCreateDTO.endTime(), formatter);
    }

    public CollectDetailsDTO toCollectRequestDTO() {
        return new CollectDetailsDTO(
                collectId,
                collectCode,
                customerCode,
                collectAddress.getCollectAddressId(),
                collectState,
                startTime.format(formatter),
                endTime.format(formatter)
        );
    }
}
