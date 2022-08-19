package com.example.HMS.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HMS.constants.UserRoles;
import com.example.HMS.entity.Patient;
import com.example.HMS.entity.User;
import com.example.HMS.repo.PatientRepository;
import com.example.HMS.service.IPatientService;
import com.example.HMS.service.IUserService;
import com.example.HMS.util.MyMailUtil;
import com.example.HMS.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService{

	

	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil ;

	@Override
	@Transactional
	public Long savePatient(Patient patient) {
		Long id= repo.save(patient).getId(); 
		if(id!=null) {
			String pwd = userUtil.genPwd();
			User user= new User();
			user.setDisplayName(patient.getFirstName() +" " + patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.PATIENT.name());
			Long genId  = userService.saveUser(user);
			if(genId!=null)
				new Thread(new Runnable() {
					public void run() {
						String text = "Your name is " + patient.getEmail() +", password is "+ pwd;
						mailUtil.send(patient.getEmail(), "PATIENT ADDED", text);
					}
				}).start();
		}
		return id;
	}

	@Override
	@Transactional
	public void updatePatient(Patient patient) {
		repo.save(patient);
	}

	@Override
	@Transactional
	public void deletePatient(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(
			readOnly  = true
			)
	public Patient getOnePatient(Long id) {
		return repo.findById(id).get();
	}

	@Override
	@Transactional(
			readOnly = true
			)
	public List<Patient> getAllPatients() {
		return repo.findAll();
	}
	
	@Override
	public Patient getOneByEmail(String email) {
		return repo.findByEmail(email).get();
	}
	
}
