package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.TinhTrangVe;

public interface TinhTrangVeRepository extends JpaRepository<TinhTrangVe, Integer> {
    // Custom query methods can be defined here if needed

}
