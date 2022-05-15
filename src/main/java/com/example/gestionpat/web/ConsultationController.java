package com.example.gestionpat.web;

import com.example.gestionpat.entities.Consultation;
import com.example.gestionpat.repositories.ConsultantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class ConsultationController {
    ConsultantRepository consultantRepository;

    @GetMapping("/user/consultations/index")
    public String consultations(Model model){
        List<Consultation> consultation = consultantRepository.findAll();
        model.addAttribute("Consultations", consultation);
        return "consultations";
    }

    @GetMapping("/admin/consultations/delete")
    public String delete(Long id){
        consultantRepository.deleteById(id);
        return "redirect:/user/consultations/index";
    }

    @GetMapping("/admin/consultations/create")
    public String create(Model model){
        model.addAttribute("consultation", new Consultation());
        return "createConsultation";
    }

    @PostMapping("/admin/consultations/save")
    public String save(Model model, @Valid Consultation consultation, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "createConsultation";
        consultantRepository.save(consultation);
        return "redirect:/user/consultations/index";
    }
}
