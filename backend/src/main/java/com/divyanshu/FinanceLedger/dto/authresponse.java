package com.divyanshu.FinanceLedger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class authresponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public authresponse(String token) {
        this.token = token;
    }
}
