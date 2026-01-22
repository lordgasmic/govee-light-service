package com.lordgasmic.govee.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoveeLightMessage {

    private String cmd;
    private GoveeLightData data;
}
