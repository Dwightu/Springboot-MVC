package com.ltp.gradesubmission.controller;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.service.GradeService;

// Entry point, Controller can only handle get request
@Controller
public class GradeController {
    // @GetMapping converts a method into a handler method
    // The handler method can accept GET requests.

    GradeService gradeService=new GradeService();

    @GetMapping("/")
    public String gradeForm(Model model,@RequestParam(required = false) String id){
        model.addAttribute("grade", gradeService.getGradeById(id));
        return "form";
    }
    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grade", gradeService.getGrades());
        return "grades";
    }
    @PostMapping("/handleSubmit")
    public String submitGrade(@Valid Grade grade,BindingResult result){
        System.out.println("Has errors?"+result.hasErrors());
        if(result.hasErrors())return "form";
        gradeService.submitGrade(grade);
        return "redirect:/grades";
    }

}
