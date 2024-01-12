package org.example.projectmodule6renthousebe.requests;

import org.example.projectmodule6renthousebe.response.BaseResponse;

public class LogoutResponse extends BaseResponse {
    public LogoutResponse(boolean success, String messageCode) {
        super(success, messageCode);
    }
}
