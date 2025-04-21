package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NguoiDatVe")
public class NguoiDatVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiDat")
    private Integer maNguoiDat;

    @Column(nullable = false, length = 100)
    private String hoTen;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String soDienThoai;

    @Column(nullable = false, unique = true, length = 50)
    private String cccd;

    @OneToMany(mappedBy = "nguoiDatVe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThanhToan> danhSachThanhToan;

}