package com.example.api_Train.DTO.RequestDTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ChuyenTauDTO {
    @JsonProperty("MaTau")
    private Integer maTau;

    @JsonProperty("MaTuyen")
    private Integer maTuyenDuong;

    @JsonProperty("NgayGioKhoiHanh")
    private LocalDateTime ngayGioKhoiHanh;
}
