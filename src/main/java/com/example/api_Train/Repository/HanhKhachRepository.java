package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_Train.models.HanhKhach;

public interface HanhKhachRepository extends JpaRepository<HanhKhach, Integer> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes

}
