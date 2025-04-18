package com.example.api_Train.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
@Table(name = "NguoiDung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Column(name = "TenDangNhap", unique = true)
    private String tenDangNhap;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "Role", nullable = false)
    private String role;

    @Column(name = "HoTen", nullable = false)
    private String hoTen;

    @Column(name = "SoDienThoai", nullable = false)
    private String soDienThoai;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "CCCD", nullable = false, unique = true)
    private String cccd;

}