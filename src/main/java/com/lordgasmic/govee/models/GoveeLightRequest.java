package com.lordgasmic.govee.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoveeLightRequest {

    private GoveeLightMessage msg;

    @Data
    @Builder
    public static class GoveeLightMessage {

        private String cmd;
        private GoveeLightData data;

        @Data
        @Builder
        public static class GoveeLightData {
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private Long value;
        }
    }
}
