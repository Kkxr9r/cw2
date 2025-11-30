package cw1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cw1.model.MedDispatchRec;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalcDeliveryPathRequest {

    private List<MedDispatchRec> medDispatchRecs;

    public CalcDeliveryPathRequest(List<MedDispatchRec> medDispatchRecs) {

        this.medDispatchRecs = medDispatchRecs;
    }
}
