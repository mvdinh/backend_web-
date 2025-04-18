// Lớp ToaTau cập nhật với quan hệ @OneToMany đến Ghe
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
@Table(name = "ToaTau")
public class ToaTau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maToa;

    @ManyToOne
    @JoinColumn(name = "MaTau")
    private Tau tau;

    @Column(name = "TenToa", nullable = false, length = 100)
    private String tenToa;

    @Column(name = "SLGhe", nullable = false)
    private Integer soLuongGhe;

    @ManyToOne
    @JoinColumn(name = "MaLoaiCho")
    private LoaiCho loaiCho;

    @OneToMany(mappedBy = "toaTau", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ghe> danhSachGhe;
}