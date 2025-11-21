package cw1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.Query;
import java.util.List;



@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRequest {

    private List<Query> queryList;

    public QueryRequest(List<Query> queryList) {
        this.queryList = queryList;
    }
}
