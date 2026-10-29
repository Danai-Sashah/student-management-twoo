package com.example.student_management_twoo.Controller;

import com.example.student_management_twoo.Entity.Student;
import com.example.student_management_twoo.Repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repository;
    public StudentController(StudentRepository repository) { this.repository = repository; }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        student.setId(null);
        return new ResponseEntity<>(repository.save(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Student> getAllStudents() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> s = repository.findById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student u) {
        if (repository.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        Student s = repository.findById(id).get();
        s.setFirstName(u.getFirstName());
        s.setLastName(u.getLastName());
        s.setEmail(u.getEmail());
        s.setAge(u.getAge());
        s.setCourse(u.getCourse());
        return ResponseEntity.ok(repository.save(s));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> m) {
        if (repository.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        Student s = repository.findById(id).get();
        if (m.containsKey("firstName")) s.setFirstName((String)m.get("firstName"));
        if (m.containsKey("lastName")) s.setLastName((String)m.get("lastName"));
        if (m.containsKey("email")) s.setEmail((String)m.get("email"));
        if (m.containsKey("age")) s.setAge((Integer)m.get("age"));
        if (m.containsKey("course")) s.setCourse((String)m.get("course"));
        return ResponseEntity.ok(repository.save(s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}