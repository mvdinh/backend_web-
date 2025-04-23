package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api_Train.models.HanhKhach;

public interface HanhKhachRepository extends JpaRepository<HanhKhach, Integer> {

    HanhKhach findBySoGiayTo(String soGiayTo);

    @Query("SELECT h FROM HanhKhach h WHERE (:maHanhKhach IS NULL OR h.maHanhKhach = :maHanhKhach) " +
            "AND (:hoTen IS NULL OR LOWER(h.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%')))")
    List<HanhKhach> searchByMaHanhKhachOrHoTen(@Param("maHanhKhach") Integer maHanhKhach,
            @Param("hoTen") String hoTen);

}
