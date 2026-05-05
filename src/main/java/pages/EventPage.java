package pages;

import factory.DriverFactory;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.models.EventDetailDataObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class EventPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public EventPage(){
    }

    String eventPage = setUp.getProperty("NAVIGATE_TO_EVENT_PAGE");
    String searchEvent = setUp.getProperty("SEARCH_EVENT_INPUT");
    String eventList = setUp.getProperty("EVENT_LIST");
    String selectEventCategory = setUp.getProperty("SELECT_EVENT_CATEGORY");
    String selectEventCity = setUp.getProperty("SELECT_EVENT_CITY");
    String clearFilterButton = setUp.getProperty("CLEAR_FILTER");
    String addNewEventButton = setUp.getProperty("ADD_NEW_EVENT");
    String listEvents = setUp.getProperty("EVENTS");
    String confirmBookingButton = setUp.getProperty("CONFIRM_BOOKING_BUTTON");
    String totalEventPrice = setUp.getProperty("TOTAL_PRICE_TICKET");
    String addEventButton = setUp.getProperty("ADD_EVENT_BUTTON");
    String allEventLists = setUp.getProperty("ALL_EVENT_LIST");


    /*
     - Book Event
     - Dynamic Select Event (by event name) when searching for event > verify event details

     */
    public void goToEventPage(){
        WebUI.clickElement(By.xpath(eventPage));
    }

    public void searchEvent(String eventName){
        WebUI.setText(By.xpath(searchEvent), eventName);
    }

    public List<EventDetailDataObject> getEventList() {
        List<EventDetailDataObject> events = new ArrayList<>();

        List<WebElement> eventCards = WebUI.getWebElements(By.cssSelector(eventList));

        for (WebElement card : eventCards) {
            EventDetailDataObject event = new EventDetailDataObject();

            event.setTitle(card.findElement(By.cssSelector("h3")).getText());

            List<WebElement> details = card.findElements(By.cssSelector("p"));
            event.setDate(details.get(0).getText());
            event.setLocation(details.get(1).getText());

            event.setPrice(card.findElement(By.xpath(".//*[contains(text(),'$')]")).getText());

            events.add(event);
        }

        return events;
    }








}
