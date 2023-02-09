package ru.snapgot.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;

    @Override
    public Task save(String description, User user) {
        Task task = new Task(description, false, user);
        taskRepo.save(task);
        return task;
    }

    @Override
    public void delete(String id, User user) {
        taskRepo.deleteTask(Long.parseLong(id), user);
    }

    @Override
    public void edit(String newDescription, String id, User user) {
        taskRepo.editTask(Long.parseLong(id), newDescription, user);
    }

    @Override
    public void toggle(String id, User user) {
        taskRepo.toggleTask(Long.parseLong(id), user);
    }

    @Override
    public List<DisplayTaskDto> get(boolean isAll, String search, User user) {
        List<DisplayTaskDto> listTasks = new ArrayList<>();
        taskRepo.getFilteredTask(isAll, search, user)
                .forEach(task -> listTasks.add(
                                new DisplayTaskDto(
                                        task.getId().toString(),
                                        task.getDescription(),
                                        task.getCompleted())
                        )
                );
        return listTasks;
    }
}
