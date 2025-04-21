package com.example.api_Train.DTO.RequestDTO.DatVe;

import java.util.Date;

import com.example.api_Train.models.DoiTuong;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HanhKhachDTO {

    private String hoTen;

    private String soGiayTo;

    private Date ngaySinh;

    private Integer maloaiKhach;
}
