package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedDispatchRec {

    private int id;
    private LocalDate date;
    private LocalTime time;
    private Requirements requirements;


    public void validateMedDispatchRec(){
        if (requirements.isHeating() && requirements.isCooling()) {
            throw new InvalidDataException("heating and cooling cannot be both true");
        }
    }
}
