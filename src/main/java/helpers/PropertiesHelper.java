package helpers;

import utils.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelper {
    private static Properties properties;
    private static final String relPropertiesFilePathDefault = "src/main/resources/config.properties";

    public static synchronized Properties loadAllFiles(){
        LinkedList<String> files = new LinkedList<>();
        files.add("src/main/resources/config.properties");
        files.add("src/main/resources/env/dev.properties");
        files.add("src/main/resources/env/prod.properties");
        files.add("src/main/resources/env/staging.properties");
        files.add("src/main/java/objects/event_hub.properties");
        files.add("src/main/resources/cucumber.properties");

        try {
            Properties merged = new Properties();
            for (String filePath : files) {
                Properties tempProp = new Properties();
                String linkFile = SystemHelper.getCurrentDir() + filePath;
                try (FileInputStream input = new FileInputStream(linkFile)) {
                    tempProp.load(input);
                }
                merged.putAll(tempProp);
            }
            properties = merged;
            LogUtils.info("Properties loaded from multiple files: " + files);
            return merged;
        }catch (IOException ioe){
            LogUtils.error("Failed to load one or more properties files: " + ioe.getMessage());
            return new Properties();
        }
    }

    public static synchronized void setFile(String relPropertiesFilePath){
        properties = new Properties();
        try {
            String linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePath;
            try (FileInputStream input = new FileInputStream(linkFile)) {
                properties.load(input);
            }
        } catch (Exception e){
            LogUtils.error("Failed to load properties file: ("+relPropertiesFilePath+"): "+ e.getMessage());
        }
    }

    public static synchronized void setDefaultFile(){
        properties = new Properties();
        try {
            String linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            try (FileInputStream input = new FileInputStream(linkFile)) {
                properties.load(input);
            }
        } catch (Exception e){
            LogUtils.error("Failed to load default properties file: ("+relPropertiesFilePathDefault+"): "+ e.getMessage());
        }
    }

    public static synchronized String getValue(String key){
        if (properties == null || properties.isEmpty()) {
            loadAllFiles();
        }
        return properties.getProperty(key);
    }

    public static synchronized void setValue(String key, String keyValue){
        try {
            if (properties == null || properties.isEmpty()) {
                setDefaultFile();
            }

            String linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            LogUtils.info(linkFile);
            properties.setProperty(key, keyValue);
            try (FileOutputStream out = new FileOutputStream(linkFile)) {
                properties.store(out, null);
            }
        }catch (Exception e){
            LogUtils.error("Failed to set value in properties file for key: ("+key+"): "+ e.getMessage());
        }
    }
}
