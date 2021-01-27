package de.structuremade.ms.sicksercvice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateSick {
    @NotNull
    private String date;
    private String clocktime;
    @NotNull
    private String student;
}
