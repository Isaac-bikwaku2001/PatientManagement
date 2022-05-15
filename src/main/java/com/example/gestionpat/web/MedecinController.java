package com.example.gestionpat.web;

import com.example.gestionpat.entities.Medecin;
import com.example.gestionpat.repositories.MedecinRepository;
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

@Controller
@AllArgsConstructor
public class MedecinController {
    MedecinRepository medecinRepository;

    @GetMapping("/user/medecin/index")
    public String medecins(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Medecin> pageMedecins = medecinRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("ListMedecins", pageMedecins.getContent());
        model.addAttribute("pages", new int[pageMedecins.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "medecins";
    }

    @GetMapping("/admin/medecin/delete")
    public String delete(Long id, String keyword, int page){
        medecinRepository.deleteById(id);
        return "redirect:/user/medecin/index?page="+ page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/medecin/create")
    public String create(Model model){
        model.addAttribute("medecin", new Medecin());
        return "createMedecin";
    }

    @PostMapping("/admin/medecin/save")
    public String save(Model model, @Valid Medecin medecin, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "createMedecin";
        medecinRepository.save(medecin);
        return "redirect:/user/medecin/index";
    }

    @GetMapping("/admin/medecin/edit")
    public String edit(Model model, Long id){
        Medecin medecin = medecinRepository.findById(id).orElse(null);
        if(medecin == null) throw new RuntimeException("Medecin not found");
        model.addAttribute("medecin", medecin);
        return "editMedecin";
    }
}
