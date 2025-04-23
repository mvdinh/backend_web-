package com.example.api_Train.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ghe_chuyen_tau")
@Data
public class GheChuyenTau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ma_ghe")
    private Ghe ghe;

    @ManyToOne
    @JoinColumn(name = "ma_chuyen_tau")
    private ChuyenTau chuyenTau;

    @Column(name = "trang_thai")
    private String trangThai; // "TRỐNG", "ĐÃ ĐẶT", "TẠM KHÓA"

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat;
}
