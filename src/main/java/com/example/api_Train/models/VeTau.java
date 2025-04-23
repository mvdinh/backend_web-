package com.example.api_Train.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VeTau")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeTau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaVe")
    private Integer maVe;

    @ManyToOne
    @JoinColumn(name = "MaChuyenTau")
    private ChuyenTau chuyenTau;

    @ManyToOne
    @JoinColumn(name = "MaHanhKhach")
    private HanhKhach hanhKhach;

    @ManyToOne
    @JoinColumn(name = "MaTau")
    private Tau tau;

    @ManyToOne
    @JoinColumn(name = "MaGhe")
    private Ghe ghe;

    @ManyToOne
    @JoinColumn(name = "MaGia")
    private BangGia bangGia;

    @ManyToOne
    @JoinColumn(name = "MaLoaiKhach")
    private DoiTuong doiTuong;

    @ManyToOne
    @JoinColumn(name = "MaTinhTrangVe")
    private TinhTrangVe tinhTrangVe;

    @OneToOne(mappedBy = "veTau", cascade = CascadeType.ALL, orphanRemoval = true)
    private ThanhToan thanhToan;
}
