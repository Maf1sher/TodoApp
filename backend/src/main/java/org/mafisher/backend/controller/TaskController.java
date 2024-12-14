package org.mafisher.backend.controller;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.request.CreateCategoryRequest;
import org.mafisher.backend.dto.request.CreateTaskRequest;
import org.mafisher.backend.dto.response.Category;
import org.mafisher.backend.dto.response.Task;
import org.mafisher.backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllCategories(Principal principal) {
        List<Task> tasks = taskService.getAll(principal);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getCategoryById(@PathVariable("id") long id, Principal principal) {
        Task task = taskService.getById(id, principal);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody CreateTaskRequest task, Principal principal) {
        Task savedTask = taskService.create(task, principal);
        return ResponseEntity.ok(savedTask);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Task> edit(
            @PathVariable("id") long id,
            @RequestBody CreateTaskRequest task,
            Principal principal
    ) {
        Task savedTask = taskService.edit(id, task, principal);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Task> delete(@PathVariable("id") long id, Principal principal) {
        taskService.delete(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-category/{task_id}/{new_category_id}")
    public ResponseEntity<Task> changeCategory(
            @PathVariable("task_id") long task_id,
            @PathVariable("new_category_id") long new_category_id,
            Principal principal
    ){
        Task task = taskService.changeCategory(task_id, new_category_id, principal);
        return ResponseEntity.ok(task);
    }

}
