package com.lordgasmic.govee.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoveeLightData {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer onOff;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer brightness;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GoveeLightColor color;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer colorTemInKelvin;
}
