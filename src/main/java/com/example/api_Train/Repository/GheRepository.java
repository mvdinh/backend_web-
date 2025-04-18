package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.Ghe;

public interface GheRepository extends JpaRepository<Ghe, Integer> {
    // Custom query methods can be defined here if needed

}