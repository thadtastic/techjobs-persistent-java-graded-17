package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;

import org.launchcode.techjobs.persistent.models.Skill;

import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }else{
            model.addAttribute("skill", skillRepository.save(newSkill));
        }

        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewEmployer(Model model, @PathVariable int skillId) {

        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }

    }

    // Add an index method that responds at /employers
    @GetMapping("/skill")
    public String index(Model model){
        model.addAttribute("skill", skillRepository.findAll());
        return "skills/index";
    }

}
