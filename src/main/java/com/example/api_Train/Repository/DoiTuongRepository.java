package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.DoiTuong;

public interface DoiTuongRepository extends JpaRepository<DoiTuong, Integer> {
    // Custom query methods can be defined here if needed

}
