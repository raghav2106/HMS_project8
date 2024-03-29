package com.example.HMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HMS.entity.Specialization;
import com.example.HMS.exception.SpecializationNotFoundException;
import com.example.HMS.service.ISpecializationService;
import com.example.HMS.view.SpecializationExcelView;
import com.example.HMS.view.SpecializationPdfView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {

	@Autowired
	private ISpecializationService service;

	/***
	 * 1. Show Register page
	 */
	@GetMapping("/register")
	public String displayRegister() {
		return "SpecializationRegister";
	}

	/**
	 * 2. On Submit Form save data
	 */
	@PostMapping("/save")
	public String saveForm(@ModelAttribute Specialization specialization, Model model) {
		Long id = service.saveSpecialization(specialization);
		String message = " Record (" + id + ") is created";
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}

	/**
	 * 3. display all Specializations
	 */
	@GetMapping("/all")
	public String viewAll(Model model, @RequestParam(value = "message", required = false) String message) {
		List<Specialization> list = service.getAllSpecializations();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "SpecializationData";
	}

	/**
	 * 4. Delete by id
	 */
	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);
			attributes.addAttribute("message", "Record " + id + " is removed");
		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}

		return "redirect:all";
	}

	/**
	 * 5. Fetch Data into Edit page End user clicks on Link, may enter ID manually.
	 * If entered id is present in DB > Load Row as Object > Send to Edit Page >
	 * Fill in Form Else > Redirect to all (Data Page) > Show Error message (Not
	 * found)
	 */

	@GetMapping("/edit")
	public String showEditPage(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page = null;
		try {
			Specialization spec = service.getOneSpecialization(id);
			model.addAttribute("specialization", spec);
			page = "SpecializationEdit";
		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	/***
	 * 6. Update Form data and redirect to all
	 */
	@PostMapping("/update")
	public String updateData(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record (" + specialization.getId() + ") is updated");
		return "redirect:all";
	}

	/**
	 * 7. Read code and check with service Return message back to UI
	 */

	@GetMapping("/checkCode")
	@ResponseBody
	public String vaidateSpecCode(@RequestParam String code, @RequestParam Long id) {
		String message = "";
		if (id == 0 && service.isSpecCodeExist(code)) { // check register
			message = code + ", already exists";
		} else if (id != 0 && service.isSPecCodeExistForEdit(code, id)) {
			message = code + " , already exist";
		}
		return message;
	}
	/**
	 * 8. Read name and check with service Return message back to UI
	 *//*
		 * 
		 * @GetMapping("/checkName")
		 * 
		 * @ResponseBody 
		 * public String validateSpecName(@RequestParam String name, @RequestParam Long id) {
		 *  String message = ""; 
		 *  if(id==0 && service.isSpecNameExist(name)) { // check register
		 *   message = name + ", already exists";
		 *    }else if(id!=0 && service.isSpecNameExistForEdit(name, id)){
		 *     message = name + ", already exist"; 
		 *     } return message; 
		 *     }
		 *     
	  **/
	
	
	/***
	 * 9. export data to excel file
	 */
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new SpecializationExcelView());
		// read data from DB
		List<Specialization> list = service.getAllSpecializations();
		// send to Excel Impl class
		m.addObject("list", list);
		return m;
	}

	/***
	 * 9. export data to PDF file
	 */
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new SpecializationPdfView());
		//read data from DB
				List<Specialization> list = service.getAllSpecializations();
				//send to Excel Impl class
				m.addObject("list", list);
				return m;
	}
	
}
