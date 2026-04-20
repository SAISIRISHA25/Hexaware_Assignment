package com.example.prac1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    List<Student> users=new ArrayList<>();
    

    MainController(){

        users.add(new Student(101,"Ajay",90));
        users.add(new Student(102,"Jatin",90));
        users.add(new Student(103,"Nithin",90));
        users.add(new Student(104,"Kavitha",90));


    }
    @GetMapping("/showAll")
    List showUsers(){
        return users;

    }
@GetMapping("/search/{rno}")
public Student searchRoll(@PathVariable int rno){
    for(Student s : users){
        if(s.getRoll() == rno){
            return s;
        }
    }
    return null;
}

@GetMapping("/searchByName/{name}")
public Student searchByName(@PathVariable String name){
    for(Student s : users){
        if(s.getName().equalsIgnoreCase(name)){
            return s;
        }
    }
    return null;
}


@PostMapping("/saveData")
String SaveData(@RequestBody Student s){
    users.add(s);
    return "Saved Data";
}

@PutMapping("/update/{rno}/{marks}")
public String updateMarks(@PathVariable int rno, @PathVariable int marks) {

    for(Student s : users){
        if(s.getRoll() == rno){
            s.setMarks(marks);
            return "Marks updated successfully";
        }
    }

    return "Student not found";
}


@DeleteMapping("/delete/{rno}")
public String deleteStudent(@PathVariable int rno) {

    for(Student s : users){
        if(s.getRoll() == rno){
            users.remove(s);
            return "Student deleted successfully";
        }
    }

    return "Student not found";
}
    



}
