package com.example.HMS.service;

import java.util.List;
import java.util.Map;

import com.example.HMS.entity.Doctor;


public interface IDoctorService {

	public Long saveDoctor(Doctor doc);
	public List<Doctor> getAllDoctors();
	public void removeDoctor(Long id);
	public Doctor getOneDoctor(Long id);
	public void updateDoctor(Doctor doc);
	
	public Map<Long,String> getDoctorIdAndNames();
	public List<Doctor> findDoctorBySpecName(Long specId);
	
}
