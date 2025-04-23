package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api_Train.models.ChiTietDatVe;
import com.example.api_Train.models.VeTau;

public interface ChiTietDatVeRepository extends JpaRepository<ChiTietDatVe, Integer> {

    @Query("SELECT v FROM VeTau v JOIN ChiTietDatVe c ON v.id = c.veTau.id WHERE c.datVe.id = :maDatVe")
    List<VeTau> findVeTauByMaDatVe(@Param("maDatVe") Integer maDatVe);

}
