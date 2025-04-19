package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.ChiTietDatVe;
import com.example.api_Train.models.ChiTietDatVeKey;
import com.example.api_Train.models.DatVe;

public interface ChiTietDatVeRepository extends JpaRepository<ChiTietDatVe, ChiTietDatVeKey> {

    List<ChiTietDatVe> findByDatVe(DatVe datVe);
    // Custom query methods can be defined here if needed

}
