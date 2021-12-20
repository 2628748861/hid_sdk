package com.wangxiaobao.sdk.hid.engine.request;

import java.util.Arrays;

public class RequestData {
    private final int OP_HEAD =0x05;
    private final int opCode;
    private final int dataLength;
    private byte[] data;
    private RequestData(int code, byte[] payload) {
        this.opCode = code;
        this.dataLength = payload == null? 0 : payload.length;
        if (dataLength > 0) {
            this.data = Arrays.copyOf(payload, dataLength);
        }
    }

    public byte[] getData() {
        return data;
    }

    public int getDataLength() {
        return dataLength;
    }

    public int getOpCode() {
        return opCode;
    }

    public byte[] getByteData() {
        byte[] result = new byte[dataLength==0?1+3:dataLength+3];
        result[0] = (byte) (0x00ff & OP_HEAD);
        result[1] = (byte) (0x00ff & opCode);
        result[2] = (byte) dataLength;
        if (dataLength > 0) {
            System.arraycopy(data, 0, result, 3, dataLength);
        }else{
            result[3] = (byte) 0;
        }
        return result;
    }



    public static class Builder{
        private int opCode;
        private byte[] payload;

        public Builder cmd(int opCode) {
            this.opCode = opCode;
            return this;
        }
        public Builder payload(byte[] payload) {
            this.payload = payload;
            return this;
        }
        public RequestData build() {
            return new RequestData(opCode,payload);
        }
    }

}
