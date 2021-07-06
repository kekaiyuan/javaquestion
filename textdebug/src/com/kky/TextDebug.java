package com.kky;

import java.io.*;

public class TextDebug {

    public static void main(String[] args) throws Exception {
        //textDebug("");

//        File file = new File("D:\\DK\\Github\\kekaiyuan.github.io\\_posts");
//        searchFile(file);

        autoForm("oracle.txt");
    }

    static void searchFile(File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f:files){
                searchFile(f);
            }
        }else{
            if(file.toString().toLowerCase().endsWith(".md")){
                //System.out.println(file.getAbsoluteFile());
                textDebug(file);
            }
        }
    }



    /**
     * 替换乱码
     * @param file
     * @throws Exception
     */
    static void textDebug(File file) throws Exception{
        String[] wrong = {"丌", "戒", "幵", "亍"};
        String[] correct = {"不", "或", "并", "于"};

        //读取文件
        //File file = new File(path);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        //内存流
        CharArrayWriter caw = new CharArrayWriter();

        //替换
        String line = null;

        int length = wrong.length;

        //以行为单位进行遍历
        while ((line = br.readLine()) != null) {
            //替换每一行中符合被替换字符条件的字符串

            int i = 0;
            while (i < length) {
                line = line.replaceAll(wrong[i], correct[i]);
                i++;
            }

            //将该行写入内存
            caw.write(line);
            //添加换行符，并进入下次循环
            caw.append(System.getProperty("line.separator"));
        }
        //关闭输入流
        br.close();

        //将内存中的流写入源文件
        FileWriter fw = new FileWriter(file);
        caw.writeTo(fw);
        fw.close();
    }

    /**
     * 将 Oracle 中的表转为 markdown 支持的表格格式
     * @param path
     * @throws Exception
     */
    static void autoForm(String path) throws Exception{
        //读取文件
        File file = new File(path);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        //内存流
        CharArrayWriter caw = new CharArrayWriter();

        //替换
        String line = null;

        //以行为单位进行遍历
        while ((line = br.readLine()) != null) {

            while(line.contains("  ")){
                line = line.replaceAll("  "," | ");
            }

            while(line.contains("| |")){
                line = line.replaceAll("[|] [|]","|");
            }

            line = "| " + line + " |";

            //将该行写入内存
            caw.write(line);
            //添加换行符，并进入下次循环
            caw.append(System.getProperty("line.separator"));
        }
        //关闭输入流
        br.close();

        //将内存中的流写入源文件
        FileWriter fw = new FileWriter(file);
        caw.writeTo(fw);
        fw.close();
    }

}

