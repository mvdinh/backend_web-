package com.example.api_Train.DTO.RequestDTO.DatVe;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDatVeDTO {
    private HanhKhachDTO hanhKhach;
    private VeTauDTO veTau;
}
