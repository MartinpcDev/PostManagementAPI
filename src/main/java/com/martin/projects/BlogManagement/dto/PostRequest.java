package com.martin.projects.BlogManagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PostRequest(
    @NotNull(message = "El title no puede ir vacio")
    @Size(min = 4, max = 100, message = "El title debe tener una longitud de entre 5 a 100 "
        + "caracteres")
    String title,

    @NotNull(message = "El content no puede ir vacio")
    @Size(min = 10, message = "El content no puede ser menor a 10 caracteres")
    String content,

    @NotNull(message = "La categoria no puede ir vacia")
    @Size(max = 60)
    String category,

    @NotNull(message = "los tags no pueder ir vacios")
    @Size(min = 1, message = "Debe tener almenos 1 tag")
    List<String> tags
) {

}
