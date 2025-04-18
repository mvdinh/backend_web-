package com.example.api_Train.DTO.RequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.LoaiCho;
import com.example.api_Train.models.Tau;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BangGiaDTO {

    @JsonProperty("MaChuyenTau")
    private Integer maChuyenTau;

    @JsonProperty("MaLoaiCho")
    private Integer maLoaiCho;

    @JsonProperty("MaTau")
    private Integer maTau;

    @JsonProperty("GiaTien")
    private BigDecimal giaTien;

}
