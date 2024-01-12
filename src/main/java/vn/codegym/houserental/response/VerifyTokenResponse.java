package vn.codegym.houserental.response;

public class VerifyTokenResponse extends BaseResponse{
    public VerifyTokenResponse(boolean success, String messageCode) {
        super(success, messageCode);
    }
}
