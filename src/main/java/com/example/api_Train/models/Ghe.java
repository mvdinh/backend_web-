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

    // Relationship with GheChuyenTau
    @OneToMany(mappedBy = "ghe", cascade = CascadeType.ALL)
    private List<GheChuyenTau> gheChuyenTaus;

}
