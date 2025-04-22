package com.example.api_Train.DTO.Request.DatVe;

import java.math.BigDecimal;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NguoiDatVeDTO {

    private String hoTen;

    private String email;

    private String soDienThoai;

    private String cccd;
}
