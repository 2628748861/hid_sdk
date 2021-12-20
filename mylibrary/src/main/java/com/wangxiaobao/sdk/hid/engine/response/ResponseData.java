package com.wangxiaobao.sdk.hid.engine.response;

import java.util.Arrays;

public class ResponseData {

    private int opCode;
    private int errorCode;
    private byte[] data;
    public ResponseData(int opCode, int errorCode, byte[] data) {
        this.opCode = opCode;
        this.errorCode = errorCode;
        this.data = data;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public byte[] getData() {
        return data;
    }

    public static ResponseData fromValue(byte[] payload) {
        int opCode = payload[1];
        int errorCode = payload[2];
        int payloadLen = payload.length;

        //todo 因结果有补位，临时添加一个截取长度

        payloadLen = payload[3]==0?1:(byte) (0x00ff & payload[3]);

        return new ResponseData(
                opCode,
                errorCode,
                Arrays.copyOfRange(payload, 4, payloadLen+4)
        );
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "opCode=" + opCode +
                ", errorCode=" + errorCode +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
