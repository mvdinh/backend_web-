package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.TuyenDuong;

public interface TuyenDuongRepository extends JpaRepository<TuyenDuong, Integer> {
    // Custom query methods can be defined here if needed
    // For example, to find TuyenDuong by MaTuyen or other attributes
    // List<TuyenDuong> findByMaTuyen(Integer maTuyen);

}
