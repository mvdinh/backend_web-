package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.NguoiDatVe;

public interface NguoiDatVeRepository extends JpaRepository<NguoiDatVe, Integer> {
    // Custom query methods can be defined here if needed

}
