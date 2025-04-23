package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.api_Train.models.NguoiDatVe;
import com.example.api_Train.models.VeTau;

@Repository
public interface VeTauRepository extends JpaRepository<VeTau, Integer> {

    @Query(value = """
            SELECT v.*
            FROM ve_tau v
            JOIN thanh_toan t ON v.ma_ve = t.ma_ve
            JOIN dat_ve d ON t.ma_ve = d.ma_dat_ve
            WHERE d.ma_nguoi_dat = :maNguoiDat
            """, nativeQuery = true)
    List<VeTau> findVeTauByNguoiDatVeId(@Param("maNguoiDat") Integer maNguoiDat);

}
