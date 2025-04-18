package com.example.api_Train.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.BangGia;
import com.example.api_Train.models.Ghe;

public interface BangGiaRepository extends JpaRepository<BangGia, Integer> {

    Optional<Ghe> findByMaChuyenTauAndMaLoaiCho(Integer maChuyenTau, Integer maLoaiCho);

}
