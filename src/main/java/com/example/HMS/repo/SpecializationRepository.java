package com.example.HMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HMS.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long>{

	@Query("select COUNT(specCode) from Specialization WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
}
