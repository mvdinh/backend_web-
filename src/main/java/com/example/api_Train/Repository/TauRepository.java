package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.Tau;

public interface TauRepository extends JpaRepository<Tau, Integer> {
    // Custom query methods can be defined here if needed

}
