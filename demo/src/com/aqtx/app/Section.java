package com.aqtx.app;

import java.util.List;

/**
 * Created by yq895943339 on 2017/3/14.
 */

public class Section {
    /**
     * code : 200
     * msg : 返回成功
     * data : [{"id":"1","name":"专项组","o":null},{"id":"13","name":"工程建设处","o":null},{"id":"14","name":"电信网安全处","o":null},{"id":"15","name":"科技处","o":null},{"id":"16","name":"科技成果转化中心","o":null},{"id":"17","name":"系统保障处","o":null},{"id":"18","name":"网络安全处","o":null},{"id":"19","name":"网络安全研究室","o":null},{"id":"20","name":"行政处","o":null},{"id":"12","name":"实验室","o":null},{"id":"11","name":"发展计划处","o":null},{"id":"10","name":"动力维护与安全生产处","o":null},{"id":"2","name":"中心领导","o":null},{"id":"3","name":"互联网信息处","o":null},{"id":"4","name":"互联网管理处","o":null},{"id":"5","name":"信息中心","o":null},{"id":"6","name":"信息安全处","o":null},{"id":"7","name":"信息安全研究室","o":null},{"id":"8","name":"党群工作办公室","o":null},{"id":"9","name":"办公室","o":null},{"id":"21","name":"财务处","o":null}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 专项组
         * o : null
         */

        private String id;
        private String name;
        private Object o;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getO() {
            return o;
        }

        public void setO(Object o) {
            this.o = o;
        }
    }
}
