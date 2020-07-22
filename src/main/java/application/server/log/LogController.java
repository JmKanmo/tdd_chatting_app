package application.server.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogController {
    private static LogController self = new LogController();
    private FileHandler fileHandler;
    private static Logger logger;

    private LogController() {
        try {
            this.fileHandler = new FileHandler(
                    "D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log", true);
            this.logger = Logger.getLogger(LogController.class.getName());
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setEncoding("UTF-8");
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logging(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static LogController getInstance() {
        return self;
    }
}
