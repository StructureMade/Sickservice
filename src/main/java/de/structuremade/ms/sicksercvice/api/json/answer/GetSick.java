package de.structuremade.ms.sicksercvice.api.json.answer;


import de.structuremade.ms.sicksercvice.utils.database.entity.Sick;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSick {
    private List<Sick> sicks;
}
