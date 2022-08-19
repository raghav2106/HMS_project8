package com.example.HMS.service;

import java.util.List;
import java.util.Map;

import com.example.HMS.entity.Specialization;

public interface ISpecializationService {

	public long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String SpecCode); 
	public boolean isSPecCodeExistForEdit(String specCode, Long id);
	
	Map<Long, String> getSpecIdAndName();
	
	/*public boolean isSpecNameExist(String SpecName);
	public boolean isSpecNameExistForEdit(String specName, Long id);
*/
}
