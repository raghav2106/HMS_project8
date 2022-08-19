package com.example.HMS.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HMS.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Optional<Patient> findByEmail(String email);
	
}
