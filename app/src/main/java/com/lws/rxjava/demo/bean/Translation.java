package com.lws.rxjava.demo.bean;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lws on 2018/2/12.
 */

public class Translation {
    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","out":"示例","vendor":"ciba","err_no":0}
     * <p>
     * 解析：
     * status : 请求成功时取1
     * content : 内容信息
     * "from":原文内容类型
     * "to":译文内容类型
     * "out":译文内容
     * "vendor":来源平台
     * "err_no":请求成功时取1
     */

    private int status;
    private Content content;

    public void show() {
        Log.d("RxJava", content.out);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {
        /**
         * from : en-EU
         * to : zh-CN
         * out : 示例
         * vendor : ciba
         * err_no : 0
         */

        private String from;
        private String to;
        private String out;
        private String vendor;
        @SerializedName("err_no")
        private int errNo;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public int getErrNo() {
            return errNo;
        }

        public void setErrNo(int errNo) {
            this.errNo = errNo;
        }
    }
}
