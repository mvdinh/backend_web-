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
@Table(name = "LoaiCho")
public class LoaiCho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLoaiCho")
    private Integer maLoaiCho;

    @Column(name = "TenLoaiCho", nullable = false, length = 100)
    private String tenLoaiCho;

}