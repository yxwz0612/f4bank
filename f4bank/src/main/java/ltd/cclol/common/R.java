package ltd.cclol.common;

/*
 *后端返回给前端的固定类型包含:
 *  1.状态码
 *      1)状态码编号
 *      2)反馈信息
 *  2.数据包
 */
public class R {
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public R() {
    }


    //new的时候直接放数据会自动附带成功码
    public R(Object data) {
        this.code = ResultCodeModel.SUCCESS.getCode();
        this.msg = ResultCodeModel.SUCCESS.getMsg();
        this.data = data;
    }

    public R(StatusCode Code) {
        this.code = Code.getCode();
        this.msg = Code.getMsg();
        this.data = null;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
