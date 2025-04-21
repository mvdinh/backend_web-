package com.example.api_Train.models;

import java.math.BigDecimal;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ID tự động tăng

    @ManyToOne
    @JoinColumn(name = "MaDatVe") // Khớp với tên cột trong DB
    private DatVe datVe;

    @ManyToOne
    @JoinColumn(name = "MaVe") // Khớp với tên cột trong DB
    private VeTau veTau;

    // Các trường khác nếu có trong DB
}