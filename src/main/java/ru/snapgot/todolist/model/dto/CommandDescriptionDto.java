package ru.snapgot.todolist.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class CommandDescriptionDto {
    @NotEmpty
    private String text;
}
