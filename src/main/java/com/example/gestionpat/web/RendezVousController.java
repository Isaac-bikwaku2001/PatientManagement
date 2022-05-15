package com.example.gestionpat.web;

import com.example.gestionpat.entities.RendezVous;
import com.example.gestionpat.repositories.RendezVousRepository;
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
public class RendezVousController {
    RendezVousRepository rendezVousRepository;

    @GetMapping("/user/rendezvous/index")
    public String rendezVous(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<RendezVous> pageRendezVous = rendezVousRepository.findByStatusContains(keyword, PageRequest.of(page, size));
        model.addAttribute("ListRendezVous", pageRendezVous.getContent());
        model.addAttribute("pages", new int[pageRendezVous.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "rendezVous";
    }

    @GetMapping("/admin/rendezvous/delete")
    public String delete(Long id, String keyword, int page){
        rendezVousRepository.deleteById(id);
        return "redirect:/user/rendezvous/index?page="+ page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/rendezvous/create")
    public String create(Model model){
        model.addAttribute("rendezvous", new RendezVous());
        return "createRDV";
    }

    @PostMapping("/admin/rendezvous/save")
    public String save(Model model, @Valid RendezVous rendezVous, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "createRDV";
        rendezVousRepository.save(rendezVous);
        return "redirect:/user/rendezvous/index";
    }

    @GetMapping("/admin/rendezvous/edit")
    public String edit(Model model, Long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElse(null);
        if(rendezVous == null) throw new RuntimeException("Rendez-vous not found");
        model.addAttribute("rendezvous", rendezVous);
        return "editRDV";
    }
}
