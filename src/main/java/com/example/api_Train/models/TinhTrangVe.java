package com.example.api_Train.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TinhTrangVe")
public class TinhTrangVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTinhTrangVe")
    private Integer maTinhTrangVe;

    @Column(name = "TinhTrangVe")
    private String tinhTrangVe;

}
