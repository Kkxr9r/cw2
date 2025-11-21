package cw1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cw1.model.MedDispatchRec;
import cw1.util.QueryAvailableDrones;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryAvailableDronesRequest {

    private List<MedDispatchRec> medDispatchRecs;

    public QueryAvailableDronesRequest(List<MedDispatchRec> medDispatchRecs) {
        this.medDispatchRecs = medDispatchRecs;
    }
}
