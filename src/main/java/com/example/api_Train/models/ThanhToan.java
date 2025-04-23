package com.example.api_Train.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ThanhToan")
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maThanhToan;

    @ManyToOne
    @JoinColumn(name = "MaVe")
    private VeTau veTau;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDat")
    private NguoiDatVe nguoiDatVe;

    @Column(name = "NgayThanhToan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "SoTien", precision = 10, scale = 2, nullable = false)
    private BigDecimal soTien;

    @Column(name = "PhuongThucThanhToan", nullable = false)
    private String phuongThucThanhToan;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

}