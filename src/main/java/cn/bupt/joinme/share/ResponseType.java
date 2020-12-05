package cn.bupt.joinme.share;

public enum ResponseType {
    SUCCESS(10000, "success"),
    RESOURCES_NOT_EXIST(10001, "资源不存在"),
    RESOURCES_ALREADY_EXIST(10002, "资源已存在"),
    SERVICE_ERROR(50000, "服务器异常");

    private int code;
    private String msg;

    ResponseType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
