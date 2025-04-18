package com.example.api_Train.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "GaTau")
public class GaTau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGa")
    private Integer maGa;

    @Column(name = "TenGa", nullable = false, length = 100)
    private String tenGa;

    @ManyToOne
    @JoinColumn(name = "MaTP", referencedColumnName = "MaTP")
    private TinhTP tinhTP;

    @Column(name = "DiaChi", nullable = false, length = 255)
    private String diaChi;

}
