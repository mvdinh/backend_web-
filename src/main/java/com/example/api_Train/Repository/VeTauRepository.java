package com.example.api_Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_Train.models.VeTau;

@Repository
public interface VeTauRepository extends JpaRepository<VeTau, Integer> {

}
