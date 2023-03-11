package com.example.MeloExpress.Shippment.domain;


import com.example.MeloExpress.Shippment.dto.CollectCreateDTO;
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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Collect(CollectCreateDTO collectCreateDTO) {
        this.customerCode = collectCreateDTO.customerCode();
        this.startTime = LocalDateTime.parse(collectCreateDTO.startTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.endTime = LocalDateTime.parse(collectCreateDTO.endTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


}
