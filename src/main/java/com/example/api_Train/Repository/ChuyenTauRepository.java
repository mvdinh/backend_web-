package com.example.api_Train.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.api_Train.models.ChuyenTau;

@Repository
public interface ChuyenTauRepository extends JpaRepository<ChuyenTau, Integer> {
    List<ChuyenTau> findAllByTuyenDuong_MaTuyen(Integer maTuyen);

    List<ChuyenTau> findAllByNgayGioKhoiHanhAfter(LocalDateTime start);

    List<ChuyenTau> findAllByNgayGioKhoiHanhBetween(LocalDateTime start, LocalDateTime end);

    // Phương thức tìm chuyến tàu theo ga đi, ga đến và khoảng thời gian (từ ngày
    // hiện tại trở đi)
    @Query("SELECT DISTINCT ct FROM ChuyenTau ct " +
            "JOIN FETCH ct.tuyenDuong td " +
            "JOIN FETCH td.gaDi " +
            "JOIN FETCH td.gaDen " +
            "WHERE LOWER(td.gaDi.tenGa) LIKE LOWER(CONCAT('%', :tenGaDi, '%')) " +
            "AND LOWER(td.gaDen.tenGa) LIKE LOWER(CONCAT('%', :tenGaDen, '%')) " +
            "AND ct.ngayGioKhoiHanh BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY ct.ngayGioKhoiHanh ASC")
    List<ChuyenTau> findChuyenTauInDay(
            @Param("tenGaDi") String tenGaDi,
            @Param("tenGaDen") String tenGaDen,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

}