package ltd.cclol.common;

//状态码
public class ResultCode implements StatusCode{
    private int code;
    private String msg;

    public ResultCode() {
    }

    //new的时候直接写string会返回失败码
    public ResultCode(String msg) {
        this.code = 1001;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
