package pages;

import factory.DriverFactory;
import keywords.WebUI;
import org.openqa.selenium.By;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class EventPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public EventPage(){
    }

    String eventPage = setUp.getProperty("NAVIGATE_TO_EVENT_PAGE");

    public void goToEventPage(){
        WebUI.clickElement(By.xpath(eventPage));
    }



}
