package com.ltp.gradesubmission.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ltp.gradesubmission.Constants;
import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;

// Entry point, Controller can only handle get request
@Controller
public class GradeController {
    // @GetMapping converts a method into a handler method
    // The handler method can accept GET requests.

    GradeRepository gradeRepository=new GradeRepository();

    @GetMapping("/")
    public String gradeForm(Model model,@RequestParam(required = false) String id){
        int index = getGradeIndex(id);
        model.addAttribute("grade", index == Constants.NOT_FOUND ? new Grade() : gradeRepository.getGrade(index));
        return "form";
    }
    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grade", gradeRepository.getGrades());
        return "grades";
    }
    @PostMapping("/handleSubmit")
    public String submitGrade(@Valid Grade grade,BindingResult result){
        System.out.println("Has errors?"+result.hasErrors());
        if(result.hasErrors())return "form";
        int index=getGradeIndex(grade.getId());
        if(index==Constants.NOT_FOUND){
            gradeRepository.addGrade(grade);
        }else{
            gradeRepository.updateGrade(grade, index);
        }
        return "redirect:/grades";
    }
    public Integer getGradeIndex(String id){
        for(int i=0;i<gradeRepository.getGrades().size();i++){
            if(gradeRepository.getGrades().get(i).getId().equals(id)){
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
