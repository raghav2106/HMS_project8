package com.example.HMS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HMS.entity.Specialization;
import com.example.HMS.repo.SpecializationRepository;
import com.example.HMS.service.ISpecializationService;

@Service
public class SpecializationServiceImpl implements ISpecializationService{

	@Autowired
	private SpecializationRepository repo; 
	
	@Override
	public long saveSpecialization(Specialization spec) {
		return repo.save(spec).getId();
		
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		return repo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		Optional<Specialization> optional =   repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
				return null;
			}
		}
	

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);
		
	}

}
