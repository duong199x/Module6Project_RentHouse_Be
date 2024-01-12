package vn.codegym.houserental.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final Map<String, String> SystemMessage;
    static {
        SystemMessage = new HashMap<>();

        //put vào mã message và message
        //Danh cho House
        SystemMessage.put("MS-HO-01", "Dang nha thanh cong");
        SystemMessage.put("ER-HO-01", "Create house fail");

        //Danh cho User
        SystemMessage.put("MS-US-01", "Register succeed");

        SystemMessage.put("MS-VR-01","Xác thực email thành công");
        SystemMessage.put("ER-VR-01","Liên kết kích hoạt không hợp lệ hoặc đã hết hạn.");
        SystemMessage.put("HR-USER-99","Lỗi khi tạo tài khoản.");
    }
}
