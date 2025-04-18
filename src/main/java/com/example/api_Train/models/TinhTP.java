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
@Table(name = "TinhTP")
public class TinhTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTP")
    private Integer maTP;

    @Column(name = "TenTP", nullable = false, length = 100)
    private String tenTP;

}
