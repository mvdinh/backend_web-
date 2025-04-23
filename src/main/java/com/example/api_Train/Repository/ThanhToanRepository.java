package com.example.api_Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.NguoiDatVe;
import com.example.api_Train.models.ThanhToan;
import com.example.api_Train.models.VeTau;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {

}
