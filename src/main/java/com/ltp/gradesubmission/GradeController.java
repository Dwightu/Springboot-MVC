package com.ltp.gradesubmission;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Entry point, Controller can only handle get request
@Controller
public class GradeController {
    // @GetMapping converts a method into a handler method
    // The handler method can accept GET requests.
    List<Grade> studentGrades=new ArrayList<>();

    @GetMapping("/")
    public String gradeForm(Model model,@RequestParam(required = false) String id){
        Grade grade;
        if(getGradeIndex(id)==Constants.NOT_FOUND){
            grade=new Grade();
        }else{
            grade=studentGrades.get(getGradeIndex(id));
        }
        System.out.println(id);
        model.addAttribute("grade", grade);
        return "form";
    }
    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grade", studentGrades);
        return "grades";
    }
    @PostMapping("/handleSubmit")
    public String submitGrade(@Valid Grade grade,BindingResult result){
        System.out.println("Has errors?"+result.hasErrors());
        if(result.hasErrors())return "form";
        int index=getGradeIndex(grade.getId());
        if(index==Constants.NOT_FOUND){
            studentGrades.add(grade);
        }else{
            studentGrades.set(index,grade);
        }
        return "redirect:/grades";
    }
    public Integer getGradeIndex(String id){
        for(int i=0;i<studentGrades.size();i++){
            if(studentGrades.get(i).getId().equals(id)){
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
