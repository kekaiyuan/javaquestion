package com.kky.netty.netty03;

import java.io.*;

public class Msg02 extends Msg{
    private int x;
    private int y;

    public Msg02(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Msg02() {
    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(x);
            dataOutputStream.writeInt(y);

            dataOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        }
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dataInputStream = null;
        try{
            dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.Msg02;
    }

    @Override
    public String toString() {
        return "Msg02{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public void clientHandle() {
        System.out.println("client receive:" + this.toString());
    }

    @Override
    public void serverHandle(){
        ServerFrame.INSTANCE.updateClientMsg(this.toString());
    }
}
