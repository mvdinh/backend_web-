package com.example.api_Train.DTO.RequestDTO;

import java.time.LocalTime;

import com.example.api_Train.models.GaTau;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TuyenDuongDTO {

    @JsonProperty("MaGaDi")
    private GaTau gaDi;

    @JsonProperty("MaGaDen")
    private GaTau gaDen;

    @JsonProperty("KhoangCach")
    private Integer khoangCach;

    @JsonProperty("ThoiGianDuKien")
    private String thoiGianDuKien;
}
