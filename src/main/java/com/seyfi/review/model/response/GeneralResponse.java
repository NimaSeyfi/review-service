package com.seyfi.review.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse {
    private Boolean error;
    private Object message;
    private Integer result_number;
    private Long sync;

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "error=" + error +
                ", message=" + message +
                ", result_number=" + result_number +
                '}';
    }

    public GeneralResponse(Boolean error, Object message, Integer result_number){
        this.error=error;
        this.message=message;
        this.result_number=result_number;
    }

}