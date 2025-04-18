package com.example.api_Train.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TenDangNhap", unique = true, length = 100)
    private String tenDangNhap;

    @Column(name = "MatKhau", length = 100)
    private String matKhau;

    @Column(name = "Role", nullable = false, length = 100)
    private String role;

    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "SoDienThoai", nullable = false, length = 20)
    private String soDienThoai;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "CCCD", nullable = false, unique = true, length = 20)
    private String cccd;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
