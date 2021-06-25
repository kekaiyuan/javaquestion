package com.kky.netty.netty03;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(client.channel!=null){
                    Msg01 msg01 = new Msg01(1, 'a');
                    client.send(msg01);
                    Msg02 msg02 = new Msg02(100,100);
                    client.send(msg02);
                }
            }
        }).start();

        //客户端与服务器进行连接
        client.connect();
    }
}
