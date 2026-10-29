package com.example.student_management_twoo.service;

import com.example.student_management_twoo.Entity.Student;
import com.example.student_management_twoo.Repository.StudentRepository;

import java.util.List;

public class StudentService {
    private final StudentRepository repository;

    //dependancy injection
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public List<Student> getAllStudents(){
        return repository.findAll();
    }
    public Student saveStudent(Student student){
        return repository.save(student);
    }
}
