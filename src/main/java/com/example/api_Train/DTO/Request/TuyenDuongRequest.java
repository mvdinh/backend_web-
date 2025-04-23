package com.example.api_Train.DTO.Request;

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

public class TuyenDuongRequest {

    @JsonProperty("MaGaDi")
    private GaTau gaDi;

    @JsonProperty("MaGaDen")
    private GaTau gaDen;

    @JsonProperty("KhoangCach")
    private Integer khoangCach;

    @JsonProperty("ThoiGianDuKien")
    private String thoiGianDuKien;
}
