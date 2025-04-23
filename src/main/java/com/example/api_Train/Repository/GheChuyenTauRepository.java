package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.api_Train.models.GheChuyenTau;

public interface GheChuyenTauRepository extends JpaRepository<GheChuyenTau, Integer> {
    List<GheChuyenTau> findByChuyenTau_MaChuyenTau(Integer maChuyenTau);

    GheChuyenTau findByChuyenTau_MaChuyenTauAndGhe_MaGhe(Integer maChuyenTau, Integer maGhe);

    @Query("SELECT gc FROM GheChuyenTau gc WHERE gc.chuyenTau.maChuyenTau = :maChuyenTau AND gc.trangThai = 'TRỐNG'")
    List<GheChuyenTau> findAvailableSeatsByChuyenTau(Integer maChuyenTau);
}
