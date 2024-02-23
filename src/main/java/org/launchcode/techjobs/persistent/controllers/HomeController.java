package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {


    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    SkillRepository skillRepository;


    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    // note of the name of the variable being used to pass the selected employer id on form submission -- Add Job
    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        //Add the employer data from employerRepository into the form template.
        List<Employer> employers = (List<Employer>) employerRepository.findAll();
        model.addAttribute("employers", employers);
        //model.addAttribute("employers", employerRepository.findAll());
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("skills", skills);
        //model.addAttribute("skills", skillRepository.findAll());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam(required = false) List<Integer> skills) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Add Job");
        //    model.addAttribute(new Job());
            List<Employer> employers = (List<Employer>) employerRepository.findAll();
            model.addAttribute("employers", employers);
            model.addAttribute("skills", skillRepository.findAll());

            return "add";
        } else {


            //use employerId param somehow, use id to lookup employer and set it into the new job
            Optional<Employer> optEmployer = employerRepository.findById(employerId);
            if (optEmployer.isPresent()) {
                Employer employer = optEmployer.get();
                newJob.setEmployer(employer);
            }
        }

        // model.addAttribute("employers", optionalEmployer);
        if(skills != null) {
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillObjs);
        }
        jobRepository.save(newJob);

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
//    model.addAttribute("jobs", jobRepository.findById(jobId));
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        Job job = (Job) optionalJob.get();
        model.addAttribute("job", job);

        return "view";
    }

}
