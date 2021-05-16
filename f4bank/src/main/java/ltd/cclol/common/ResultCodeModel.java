package ltd.cclol.common;

//常用状态码固定模型
public enum ResultCodeModel implements StatusCode{
    SUCCESS(1000, "成功"),
    FAILED(1001, "失败"),
    FAILED_NULL(1002,"没有找到结果"),
    VALIDATE_ERROR(1003, "参数校验失败"),
    RESPONSE_ERROR(1004, "response返回包装失败");

    private int code;
    private String msg;

    ResultCodeModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResultCodeModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
