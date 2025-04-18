package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TuyenDuong")
public class TuyenDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTuyen;

    @ManyToOne
    @JoinColumn(name = "MaGaDi", nullable = false)
    private GaTau gaDi;

    @ManyToOne
    @JoinColumn(name = "MaGaDen", nullable = false)
    private GaTau gaDen;

    @Column(name = "KhoangCach")
    private Integer khoangCach;

    @Column(name = "ThoiGianDuKien")
    private String thoiGianDuKien;

}