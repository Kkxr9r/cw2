package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedDispatchRec {

    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private Requirements requirements;
    private Position delivery;


    public static void validateMedDispatchRecs(List<MedDispatchRec> medDispatchRecs) throws InvalidDataException {
        if  (medDispatchRecs == null || medDispatchRecs.isEmpty()) {
            throw new InvalidDataException("no MedDispatchRec given");
        }
        for (MedDispatchRec medDispatchRec : medDispatchRecs) {
            validateMedDispatchRec(medDispatchRec);
        }
    }

    private static void validateMedDispatchRec(MedDispatchRec medDispatchRec) throws InvalidDataException {
        if (medDispatchRec == null){
            throw new InvalidDataException("MedDispatchRec is cannot be null");
        }

        if (medDispatchRec.getId() == null){
            throw new InvalidDataException("MedDispatchRec id cannot be null");
        }
        if (medDispatchRec.getDelivery() == null){
            throw new InvalidDataException("MedDispatchRec delivery cannot be null");
        }

        Requirements requirements = medDispatchRec.getRequirements();
        if (requirements == null){
            throw new InvalidDataException("requirements cannot be null");
        }
        if (requirements.getCapacity() == null){
            throw new InvalidDataException("capacity cannot be null");
        }
        if (requirements.getCapacity() < 0){
            throw new InvalidDataException("capacity cannot be negative");
        }
        if (requirements.isHeating() && requirements.isCooling()) {
            throw new InvalidDataException("heating and cooling cannot be both true");
        }
    }
}
