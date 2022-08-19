package com.example.HMS.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HMS.constants.SlotStatus;
import com.example.HMS.entity.SlotRequest;
import com.example.HMS.repo.SlotRequestRepository;
import com.example.HMS.service.ISlotRequestService;

@Service
public class SlotRequestServiceImpl implements ISlotRequestService {

	@Autowired
	private SlotRequestRepository repo;

	public Long saveSlotRequest(SlotRequest sr) {
		return repo.save(sr).getId();
	}

	public SlotRequest getOneSlotRequest(Long id) {
		Optional<SlotRequest> opt = repo.findById(id);
		if(opt!=null) {
			return opt.get();
		}
		return null;
	}
	
	public List<SlotRequest> getAllSlotRequests() {
		return repo.findAll();
	}

	@Transactional
	public void updateSlotRequestStatus(Long id, String status) {
		repo.updateSlotRequestStatus(id, status);
	}

	public List<SlotRequest> viewSlotsByPatientMail(String patientMail) {
		return repo.getAllPatientSlots(patientMail);
	}

	@Override
	public List<SlotRequest> viewSlotsByDoctorMail(String doctorMail) {
		return repo.getAllDoctorSlots(doctorMail,SlotStatus.ACCEPTED.name());
	}
	
}
