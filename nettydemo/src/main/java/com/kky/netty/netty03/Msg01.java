package com.kky.netty.netty03;

import java.io.*;

public class Msg01 extends Msg{
    private int anInt;
    private char aChar;

    public Msg01(int anInt, char aChar) {
        this.anInt = anInt;
        this.aChar = aChar;
    }

    public Msg01() {
    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(anInt);
            dataOutputStream.writeChar(aChar);

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

            this.anInt = dataInputStream.readInt();
            this.aChar = dataInputStream.readChar();

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
        return MsgType.Msg01;
    }

    @Override
    public String toString() {
        return "Msg01{" +
                "anInt=" + anInt +
                ", aChar=" + aChar +
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
