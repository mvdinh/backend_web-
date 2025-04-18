package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

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
    private HanhKhach maNguoiDat;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayDat")
    private Date ngayDat;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    // Quan hệ nhiều-nhiều thông qua bảng ChiTietDatVe
    @OneToMany(mappedBy = "datVe", cascade = CascadeType.ALL)
    private List<ChiTietDatVe> chiTietDatVeList;
}
