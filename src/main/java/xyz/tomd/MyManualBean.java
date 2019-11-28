package xyz.tomd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyManualBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyManualBean.class);

    public MyManualBean() {
        // Look for this in the logs - this proves that the beans.xml has been parsed.
        LOGGER.info("Instantiating MyManualBean!");
    }

    public String hiya() {
        return "Hiya";
    }
}
