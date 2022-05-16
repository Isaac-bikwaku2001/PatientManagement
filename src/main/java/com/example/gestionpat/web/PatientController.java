package com.example.gestionpat.web;

import com.example.gestionpat.entities.Patient;
import com.example.gestionpat.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+ page + "&keyword=" + keyword;
    }

    @GetMapping("/user/patients")
    public List<Patient> ListPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/admin/create")
    public String create(Model model){
        model.addAttribute("patient", new Patient());
        return "create";
    }

    @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "create";
        patientRepository.save(patient);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model, Long id){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        return "edit";
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
