package com.example.api_Train.DTO.Response.DatVeTau;

import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatVeResponse {
    private Integer maDatVe;
    private Date ngayDatVe;
    private String trangThai;

}
