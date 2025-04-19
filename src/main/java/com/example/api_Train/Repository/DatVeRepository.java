package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.DatVe;
import com.example.api_Train.models.NguoiDatVe;

public interface DatVeRepository extends JpaRepository<DatVe, Integer> {

    // Custom query methods can be defined here if needed
    // For example, you can add methods to find tickets by user or train ID, etc.

}
