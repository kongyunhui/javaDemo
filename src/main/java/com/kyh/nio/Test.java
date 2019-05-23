package com.kyh.nio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by kongyunhui on 2018/1/25.
 */
public class Test {
    public static void main(String[] args) {
//        methodIO();
        methodNIO();
    }

    public static void methodIO() {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("src/main/resources/input.txt"));

            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                for (int i = 0; i < len; i++)
                    System.out.print((char) buf[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void methodNIO(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("src/main/resources/input.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);

            while(fileChannel.read(buf) != -1) {
                buf.flip();
                while(buf.hasRemaining()) {
                    System.out.print((char)buf.get());
                }

                buf.compact();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
