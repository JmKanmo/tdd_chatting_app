package application.server.log;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogControllerTest {
    @Test
    public static void testLogControllerTest() throws SecurityException, IOException {
        String msg = "안녕하세요 반갑습니다!!";
        LogController.getInstance().logging(msg);
        List<String> lines = Files
                .readAllLines(Paths.get("D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log"));

        String[] splited = lines.get(lines.size() - 1).split(":");
        String checked = splited[splited.length - 1].trim();
        assertEquals(msg.equals(checked), true);
    }
}
