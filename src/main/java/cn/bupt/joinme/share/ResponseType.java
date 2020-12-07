package cn.bupt.joinme.share;

public enum ResponseType {

    SUCCESS(10000, "sucess"),
    USER_NOT_EXIST(10001, "user not exist"),
    USER_ALREADY_EXIST(10002, "user already exist"),
    NO_PERMISSON(10003, "no permisson"),
    USER_NOT_LOGIN(10004, "user not login"),
    USER_CREDENTIAL_ERROR(10005, "user credential error"),
    SESSION_EXPIRE(10006, "session expire"),
    COMMON_FAIL(10007, "common failure"),
    SERVICE_ERROR(50000, "service error"),
    FILE_TRANSFER_ERROR(10008, "file transfer error");



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
