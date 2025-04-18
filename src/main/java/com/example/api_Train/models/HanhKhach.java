package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HanhKhach")
public class HanhKhach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHanhKhach")
    private Integer maHanhKhach;

    @Column(name = "HoTen", length = 100)
    private String hoTen;

    @Column(name = "SoGiayTo", length = 50)
    private String soGiayTo;

    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;

    @ManyToOne
    @JoinColumn(name = "MaLoaiKhach")
    private DoiTuong loaiKhach;

}