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
@Table(name = "BangGia")
public class BangGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maGia;

    @ManyToOne
    @JoinColumn(name = "MaChuyenTau")
    private ChuyenTau chuyenTau;

    @ManyToOne
    @JoinColumn(name = "MaLoaiCho")
    private LoaiCho loaiCho;

    @ManyToOne
    @JoinColumn(name = "MaTau")
    private Tau tau;

    @Column(name = "GiaTien", nullable = false)
    private BigDecimal giaTien;

}
