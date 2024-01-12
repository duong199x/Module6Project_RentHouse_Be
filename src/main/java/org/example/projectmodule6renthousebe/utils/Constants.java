package org.example.projectmodule6renthousebe.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String> SystemMessage;
    static {
        SystemMessage = new HashMap<>();
        //put vào mã message và message
        SystemMessage.put("MS-HO-01", "Create house success");
        SystemMessage.put("ER-HO-01", "Create house fail");
        SystemMessage.put("MS-LO-01", "Đăng xuất thành công");

    }
}
