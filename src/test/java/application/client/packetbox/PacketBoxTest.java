package application.client.packetbox;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class PacketBoxTest {
    @Test
    public void packetBoxTest(){
        while (true) {
            try{
                String[]arr = {"ggg"};
                String a = arr[100];
                System.out.println("gg");
                if(a.equals("anObject")){
                    throw new IOException();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}