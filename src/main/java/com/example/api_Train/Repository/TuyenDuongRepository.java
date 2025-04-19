package com.example.api_Train.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.TuyenDuong;

public interface TuyenDuongRepository extends JpaRepository<TuyenDuong, Integer> {

    Optional<TuyenDuong> findByMaTuyen(Integer maTuyen);

    // Custom query methods can be defined here if needed
    // For example, to find TuyenDuong by MaTuyen or other attributes
    // List<TuyenDuong> findByMaTuyen(Integer maTuyen);

}
