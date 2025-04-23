package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ChuyenTau")
public class ChuyenTau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChuyenTau")
    private Integer maChuyenTau;

    @ManyToOne
    @JoinColumn(name = "MaTau", referencedColumnName = "MaTau")
    private Tau tau;

    @ManyToOne
    @JoinColumn(name = "MaTuyen", referencedColumnName = "MaTuyen")
    private TuyenDuong tuyenDuong;

    @Column(name = "NgayGioKhoiHanh", nullable = false)
    private LocalDateTime ngayGioKhoiHanh;

    @OneToMany(mappedBy = "chuyenTau", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BangGia> danhSachBangGia;

    @OneToMany(mappedBy = "chuyenTau", cascade = CascadeType.ALL)
    private List<GheChuyenTau> gheChuyenTaus;
}
