package com.example.api_Train.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.Tau;

public interface ChuyenTauRepository extends JpaRepository<ChuyenTau, Integer> {

    List<ChuyenTau> findAllByTuyenDuong_MaTuyen(Integer maTuyen);

    List<ChuyenTau> findAllByNgayGioKhoiHanhBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
