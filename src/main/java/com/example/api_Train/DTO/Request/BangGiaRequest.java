package com.example.api_Train.DTO.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BangGiaRequest {

    @JsonProperty("MaChuyenTau")
    private Integer maChuyenTau;

    @JsonProperty("MaLoaiCho")
    private Integer maLoaiCho;

    @JsonProperty("MaTau")
    private Integer maTau;

    @JsonProperty("GiaTien")
    private BigDecimal giaTien;

}
