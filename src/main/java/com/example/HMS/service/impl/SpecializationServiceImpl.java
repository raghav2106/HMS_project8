package com.example.HMS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HMS.entity.Specialization;
import com.example.HMS.exception.SpecializationNotFoundException;
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
		repo.delete(getOneSpecialization(id));
		
	}

	@Override
	public Specialization getOneSpecialization(Long id) {
	/***
	  	Optional<Specialization> optional =   repo.findById(id);
	  	if(optional.isPresent()) {
			return optional.get();
		}else {
				throw new SpecializationNotFoundException(id + "Not Found");
			}
		}
	 ***/
		return repo.findById(id).orElseThrow(
				()-> new SpecializationNotFoundException(id + " Not Found"));
		
	}
		
	

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);
		
	}

	@Override
	public boolean isSpecCodeExist(String SpecCode) {
		/*Integer count = repo.getSpecCodeCount(specCode);
		boolean exist = count>0 ? true : false;
		return exist;*/
		return repo.getSpecCodeCount(SpecCode)>0;
	}

}
