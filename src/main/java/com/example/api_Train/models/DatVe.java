package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DatVe")
public class DatVe {

    @Id
    @Column(name = "MaDatVe")
    private Integer maDatVe;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDat")
    private NguoiDatVe maNguoiDat;

    @Column(name = "NgayDat")
    private LocalDateTime ngayDat;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    private BigDecimal tongTien;

    // Quan hệ nhiều-nhiều thông qua bảng ChiTietDatVe
    @OneToMany(mappedBy = "datVe", cascade = CascadeType.ALL)
    private List<ChiTietDatVe> chiTietDatVeList;
}
