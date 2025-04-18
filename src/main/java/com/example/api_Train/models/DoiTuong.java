package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DoiTuong")
public class DoiTuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLoaiKhach")
    private Integer maLoaiKhach;

    @Column(name = "TenLoaiKhach", nullable = false, length = 100)
    private String tenLoaiKhach;

    @Column(name = "PhanTramGiamGia", nullable = false, precision = 5, scale = 2)
    private BigDecimal phanTramGiamGia;

}
