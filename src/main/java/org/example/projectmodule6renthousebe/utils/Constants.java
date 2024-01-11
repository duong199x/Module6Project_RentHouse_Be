package org.example.projectmodule6renthousebe.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String> SystemMessage;
    static {
        SystemMessage = new HashMap<>();
        //put vào mã message và message
        SystemMessage.put("MS-HO-01", "Thêm nhà thành công");
        SystemMessage.put("ER-HO-02", "Thêm nhà thất bại!");
        SystemMessage.put("MS-HO-03", "Sửa thông tin nhà thành công");
        SystemMessage.put("ER-HO-04", "Sửa thông tin nhà thất bại!");
        SystemMessage.put("MS-HO-05", "Xóa nhà thành công");
        SystemMessage.put("ER-HO-06", "Xóa nhà nhà thất bại!");
        SystemMessage.put("MS-HO-07", "Thêm tiện ích trong nhà thành công");
        SystemMessage.put("ER-HO-08", "Thêm tiện ích trong nhà thất bại!");

        SystemMessage.put("MS-CT-01", "Thêm loại nhà thành công");
        SystemMessage.put("ER-CT-02", "Thêm loại nhà thất bại!");
        SystemMessage.put("MS-CT-03", "Xóa loại nhà thành công");
        SystemMessage.put("ER-CT-04", "Xóa loại nhà thất bại!");

        SystemMessage.put("MS-CV-01", "Thêm tiện ích thành công");
        SystemMessage.put("ER-CV-02", "Thêm tiện ích thất bại!");
        SystemMessage.put("MS-CV-03", "Xóa tiện ích thành công");
        SystemMessage.put("ER-CV-04", "Xóa tiện ích thất bại!");

        SystemMessage.put("MS-IM-01", "Thêm ảnh thành công");
        SystemMessage.put("ER-IM-02", "Thêm ảnh thất bại!");
        SystemMessage.put("MS-IM-03", "Sửa ảnh thành công");
        SystemMessage.put("ER-IM-04", "Sửa ảnh thất bại!");
        SystemMessage.put("MS-IM-05", "Xóa ảnh thành công");
        SystemMessage.put("ER-IM-06", "Xóa ảnh thất bại!");

        SystemMessage.put("MS-US-01", "Đăng ký tài khoản thành công");
        SystemMessage.put("ER-US-02", "Đăng ký tài khoản thất bại!");
        SystemMessage.put("MS-US-03", "Đăng nhập thành công");
        SystemMessage.put("ER-US-04", "Đăng nhập thất bại!");
        SystemMessage.put("MS-US-05", "Cập nhật tài khoản thành công");
        SystemMessage.put("ER-US-06", "Cập nhật tài khoản thất bại!");
        SystemMessage.put("MS-US-07", "Sửa avatar thành công");
        SystemMessage.put("ER-US-08", "Sửa avatar thất bại!");
        SystemMessage.put("MS-US-09", "Đổi mật khẩu thành công");
        SystemMessage.put("ER-US-10", "Đổi mật khẩu thất bại!");
    }
}
