package main;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
public class Test {
    @org.junit.Test
    public void test(){
        String str = "user:pas";
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes())));
    }
}
