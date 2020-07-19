package com.example.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// a command is a Web frontend object; interconversion between a POJO and its corresponding command object, and vice versa
// is provided by /converters

// a web user requesting data with POST sends the command object to a controller; Spring converts the command bean
// back to a POJO bean before injection on the back-end. The property names between calls are the same.

// Note that enumeration classes (e.g. Difficulty) are not handled by command
@NoArgsConstructor
@Setter
@Getter
public class UnitOfMeasureCommand {
    //list the properties of UnitOfMeasure and get Lombok to handle getters and setters, and parameter-less constructor
    private Long id;
    private String description;
}
