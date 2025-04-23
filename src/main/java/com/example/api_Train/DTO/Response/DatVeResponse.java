package com.example.api_Train.DTO.Response;

import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatVeResponse {
    private Integer maDatVe;
    private Date ngayDatVe;
    private String trangThai;
    private NguoiDatVeResponse nguoiDatVe;
    private List<VeTauResponse> veTaus;
    private BigDecimal tongTien;

}
