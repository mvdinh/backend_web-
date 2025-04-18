// Lớp Tau hiện tại với mối quan hệ @OneToMany
package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tau")
public class Tau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTau")
    private Integer maTau;

    @Column(name = "TenTau", nullable = false, length = 100)
    private String tenTau;

    @OneToMany(mappedBy = "tau", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToaTau> danhSachToaTau;

    @OneToMany(mappedBy = "tau", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChuyenTau> danhSachChuyenTau;

}