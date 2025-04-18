package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ChiTietDatVe")
public class ChiTietDatVe {

    @EmbeddedId
    private ChiTietDatVeKey id;

    @ManyToOne
    @MapsId("maDatVe")
    @JoinColumn(name = "MaDatVe")
    private DatVe datVe;

    @ManyToOne
    @MapsId("maVe")
    @JoinColumn(name = "MaVe")
    private VeTau veTau;
}
