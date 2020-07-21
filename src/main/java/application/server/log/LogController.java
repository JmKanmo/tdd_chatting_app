package application.server.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogController {
    public static void logging(String msg) {
        FileHandler fileHandler = null;
        Logger logger = Logger.getLogger(LogController.class.getName());

        try {
            fileHandler = new FileHandler(
                    "D:\\chatting_app\\src\\main\\java\\application\\server\\log\\server.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setEncoding("UTF-8");
            logger.addHandler(fileHandler);
            logger.log(Level.INFO, msg);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
