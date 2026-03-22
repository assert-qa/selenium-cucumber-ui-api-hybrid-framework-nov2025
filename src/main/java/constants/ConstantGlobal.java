package constants;

import helpers.PropertiesHelper;

public class ConstantGlobal {

    static {
        PropertiesHelper.loadAllFiles();
    }

    public final static String BROWSER = PropertiesHelper.getValue("BROWSER");
    public final static Boolean HEADLESS = Boolean.parseBoolean(PropertiesHelper.getValue("HEADLESS"));
    public final static String BASE_URL = PropertiesHelper.getValue("BASE_URL");
    public final static String VALID_EMAIL = PropertiesHelper.getValue("VALID_LOGIN_EMAIL");
    public final static String VALID_PASSWORD = PropertiesHelper.getValue("VALID_LOGIN_PASSWORD");
    public final static String STEP_TIME = PropertiesHelper.getValue("STEP_TIME");
    public final static String ENV = PropertiesHelper.getValue("ENV");
    public final static String EXPLICIT_TIMEOUT = PropertiesHelper.getValue("EXPLICIT_WAIT_TIMEOUT");
    public final static String HARD_WAIT_TIMEOUT = PropertiesHelper.getValue("HARD_WAIT_TIMEOUT");
    public final static String PAGE_LOAD_TIMEOUT = PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT");
    public final static String SCREENSHOT_PATH = PropertiesHelper.getValue("SCREENSHOT_PATH");
    public final static String RECORD_VIDEO_PATH = PropertiesHelper.getValue("RECORD_VIDEO_PATH");
    public final static String RECORD_VIDEO = PropertiesHelper.getValue("RECORD_VIDEO");
    public final static String SCREENSHOT_STEP = PropertiesHelper.getValue("SCREENSHOT_STEP_ALL");
    public final static String EXTENT_REPORT_PATH = PropertiesHelper.getValue("EXTENT_REPORT_PATH");
    public final static String AUTHOR = PropertiesHelper.getValue("AUTHOR");
    public final static String LOCATE = PropertiesHelper.getValue("LOCATE");

    private static String getFirstNonBlank(String... keys) {
        for (String key : keys) {
            String value = PropertiesHelper.getValue(key);
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return "";
    }
}