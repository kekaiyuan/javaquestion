package com.kky.netty.netty02;

public class Msg {
    public int x, y;

    public Msg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Msg: " + x +" , "+ y;
    }
}
