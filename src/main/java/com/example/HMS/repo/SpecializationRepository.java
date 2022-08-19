package com.example.HMS.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HMS.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long>{

	@Query("select COUNT(specCode) from Specialization WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
	

	@Query("SELECT COUNT(specCode) FROM Specialization  WHERE specCode=:specCode AND id!=:id")
	Integer getSpecCodeCountForEdit(String specCode,Long id);

	@Query("SELECT id,specName FROM Specialization")
	List<Object[]> getSpecIdAndName();
	
	/*
	 * @Query("select COUNT(specName) from Specialization WHERE specName=:specName")
	 * Integer getSpecNameCount(String specName);
	 * 
	 * @Query("SELECT COUNT(specName) FROM Specialization  WHERE specName=:specName AND id!=:id"
	 * ) Integer getSpecNameCountForEdit(String specName, Long id);
	 */
}
