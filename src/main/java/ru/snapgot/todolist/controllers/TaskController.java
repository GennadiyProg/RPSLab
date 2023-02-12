package ru.snapgot.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.dto.CommandDescriptionDto;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.repos.UserRepo;

import jakarta.validation.Valid;
import ru.snapgot.todolist.service.TaskService;

import java.security.Principal;
import java.util.List;

@Validated
@AllArgsConstructor
@Controller
@RequestMapping("/customer/tasks")
public class TaskController {
    private final UserRepo userRepo;
    private final TaskService taskService;

    @GetMapping("/")
    public String createTask(Model model) {
        model.addAttribute("commandDescriptionDto", new CommandDescriptionDto());
        return "customer/createTask";
    }

    @PostMapping("/")
    public String addTask(@ModelAttribute @Valid CommandDescriptionDto commandDescriptionDto, Principal principal){
        taskService.save(commandDescriptionDto.getText(), userRepo.findByUsername(principal.getName()));
        return "redirect:/account";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable String id, Principal principal){
        taskService.delete(id, userRepo.findByUsername(principal.getName()));
        return "redirect:/customer/tasks/list/?isAll=true";
    }

    @GetMapping("/{id}/modification")
    public String editTaskPage(@PathVariable String id, Model model) {
        model.addAttribute("taskId", id);
        return "customer/editTask";
    }

    @PostMapping("/{id}/modification")
    public String editTask(@RequestParam(value = "newDescription") String newDescription,
                         @PathVariable String id,
                         Principal principal){
        taskService.edit(newDescription, id, userRepo.findByUsername(principal.getName()));
        return "redirect:/customer/tasks/list/?isAll=true";
    }

    @GetMapping("/list/")
    public String getTasks(@RequestParam(name = "isAll") boolean isAll,
                                         @RequestParam(name= "search", required = false, defaultValue = "") String search,
                                         Model model,
                                         Principal principal) {
        model.addAttribute("taskList", taskService.get(isAll, search, userRepo.findByUsername(principal.getName())));
        return "customer/taskList";
    }

    @PostMapping("/{id}/completed")
    public String toggleTask(@PathVariable String id,
                           Principal principal){
        taskService.toggle(id, userRepo.findByUsername(principal.getName()));
        return "redirect:/customer/tasks/list/?isAll=true";
    }
}
