package com.kky.netty.netty01;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    //单例模式
    public static final ClientFrame INSTANCE = new ClientFrame();

    //对话框和显示框
    private TextArea textArea = new TextArea();
    private TextField textField = new TextField();

    private Client client = null;

    private ClientFrame(){
        //初始化
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.add(textArea,BorderLayout.CENTER);
        this.add(textField,BorderLayout.SOUTH);
        textArea.setEditable(false);

        //当对话框敲下回车时触发
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送数据
                client.send(textField.getText());
                //textArea.append(textField.getText());
                //textArea.setText(textArea.getText()+textField.getText()+"\n");
                //对话框清空
                textField.setText("");
            }
        });

        //关闭窗口时调用
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //连接关闭
                client.closeConnect();
                //系统退出
                System.exit(-1);
            }
        });

    }

    //与服务器连接
    private void connectToServer(){
        client = new Client();
        client.connect();
    }

    public static void main(String[] args) {
        ClientFrame clientFrame = ClientFrame.INSTANCE;

        clientFrame.setVisible(true);
        clientFrame.connectToServer();
    }

    //更新显示框
    public void updateText(String msg){
        this.textArea.append(msg+"\n");
    }
}
