package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.ToaTau;

public interface ToaTauRepository extends JpaRepository<ToaTau, Integer> {
    // Custom query methods can be defined here if needed

}
