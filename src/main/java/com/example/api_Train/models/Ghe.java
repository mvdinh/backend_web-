package com.example.api_Train.models;

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
@Table(name = "Ghe")
public class Ghe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGhe")
    private Integer maGhe;

    @ManyToOne
    @JoinColumn(name = "MaToa")
    private ToaTau toaTau;

    @Column(name = "TenGhe", nullable = false, length = 10)
    private String tenGhe;

    @Column(name = "TrangThai", nullable = false, length = 50)
    private String trangThai;

}
