package com.alexeiboriskin.walnuttask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PingResponse {

    private boolean success = true;
}
