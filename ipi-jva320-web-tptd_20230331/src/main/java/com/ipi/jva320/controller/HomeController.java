package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;


    @GetMapping(value = "/")
    public String home(ModelMap model) {
        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        return "home";
    }

    @GetMapping(value = "/salaries/{id}")
    public String salarie(ModelMap model, @PathVariable Long id) {
        model.put("salarie", salarieAideADomicileService.getSalarie(id));
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String newSalarie(ModelMap model) {
        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String createSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping(value = "/salaries/{id}/delete")
    public String deleteSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(salarie.getId());
        return "redirect:/salaries/";
    }
//
//    @PostMapping(value = "/salaries/{id}")
//    public String updateSalarie(SalarieAideADomicile salarie) throws SalarieException {
//        salarieAideADomicileService.updateSalarieAideADomicile(salarie);
//        return "redirect:/salaries/" + salarie.getId();
//    }

//    @RequestMapping(value = "/salaries")
//    public ModelAndView findSalariesByNom(String nom){
//        System.out.println("Hello  " + nom);
//        ModelAndView modelAndView = new ModelAndView("list");
//        List<SalarieAideADomicile> salarieAideADomicileList = salarieAideADomicileService.getSalaries(nom);
//        modelAndView.addObject("salaries",salarieAideADomicileList);
//        return modelAndView;
//    }

    @GetMapping(value = "/salaries")
    public String getListSalarie(final ModelMap m, SalarieAideADomicile salarie, @RequestParam(value = "nom") String name) {
        System.out.println("Hello  " + name);

        if(Objects.equals(name, "")){
            m.addAttribute("salaries", salarieAideADomicileService.getSalaries());
        } else{
            try {
                m.addAttribute("salaries", salarieAideADomicileService.getSalaries(name));
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, name + " : pas trouvé");
            }
        }
        return "list.html";
    }


    @GetMapping(value = "/salariesall")
    public String getSalaries(ModelMap model) {
        model.put("salaries", salarieAideADomicileService.getSalaries());
        return "list";
    }



}
