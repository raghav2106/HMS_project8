package com.example.HMS.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Doctor;
import com.example.HMS.exception.AppointmentNotFoundException;
import com.example.HMS.service.IAppointmentService;
import com.example.HMS.service.IDoctorService;
import com.example.HMS.service.ISpecializationService;


@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private IAppointmentService service;
	
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private ISpecializationService specializationService;
	
	private void commonUi(Model model) {
		model.addAttribute("doctors", doctorService.getDoctorIdAndNames());
	}

	@GetMapping("/register")
	public String registerAppointment(Model model) {
		model.addAttribute("appointment",new Appointment());
		commonUi(model);
		return "AppointmentRegister";
	}

	@PostMapping("/save")
	public String saveAppointment(@ModelAttribute Appointment appointment, Model model) {
		java.lang.Long id=service.saveAppointment(appointment);
		model.addAttribute("message","Appointment created with Id:"+id);
		model.addAttribute("appointment",new Appointment()) ;
		commonUi(model);
		return "AppointmentRegister";
	}

	@GetMapping("/all")
	public String getAllAppointments(Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Appointment> list=service.getAllAppointments();
		model.addAttribute("list",list);
		model.addAttribute("message",message);
		return "AppointmentData";
	}

	@GetMapping("/delete")
	public String deleteAppointment(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.deleteAppointment(id);
			attributes.addAttribute("message","Appointment deleted with Id:"+id);
		} catch(AppointmentNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}

	@GetMapping("/edit")
	public String editAppointment(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Appointment ob=service.getOneAppointment(id);
			model.addAttribute("appointment",ob);
			commonUi(model);
			page="AppointmentEdit";
		} catch(AppointmentNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
			page="redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateAppointment(@ModelAttribute Appointment appointment,
			RedirectAttributes attributes) {
		service.updateAppointment(appointment);
		attributes.addAttribute("message","Appointment updated");
		return "redirect:all";
	}
	
	//.. view appointments page..
	@GetMapping("/view")
	public String viewSlots(
			@RequestParam(required = false, defaultValue = "0") Long specId,
			Model model
			) 
	{
		//fetch data for Spec DropDown
		Map<Long,String> specMap =  specializationService.getSpecIdAndName();
		model.addAttribute("specializations", specMap);
		
		List<Doctor> docList =  null;
		String message = null;
		if(specId<=0) { //if they did not select any spec
			 docList = doctorService.getAllDoctors();
			 message = "Result : All Doctors";
		} else {
			 docList = doctorService.findDoctorBySpecName(specId);
			 message = "Result : "+specializationService.getOneSpecialization(specId).getSpecName()+" Doctors";
		}
		model.addAttribute("docList", docList);
		
		model.addAttribute("message", message);
		
		return "AppointmentSearch";
	}
	
	//.. view slots...
		@GetMapping("/viewSlot")
		public String showSlots(
				@RequestParam Long id,
				Model model
				) 
		{
			//fetch apps based on doctor id
			List<Object[]> list = service.getAppoinmentsByDoctor(id);
			model.addAttribute("list", list);
			Doctor doc = doctorService.getOneDoctor(id);
			model.addAttribute("message", "RESULTS SHOWING FOR : " + doc.getFirstName()+" "+doc.getLastName());
			return "AppointmentSlots";
		}
		
		@GetMapping("/currentDoc")
		public String getCurrentDocAppointments(
				Model model,
				Principal p) 
		{
			List<Object[]> list=service.getAppoinmentsByDoctorEmail(p.getName());
			model.addAttribute("list",list);
			return "AppointmentForDoctor";
		}
}
