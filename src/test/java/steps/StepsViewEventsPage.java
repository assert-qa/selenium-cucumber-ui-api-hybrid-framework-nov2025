package steps;

import hooks.TestContext;
import io.cucumber.java.en.And;
import keywords.WebUI;
import pages.EventPage;
import pages.models.EventDetailDataObject;

import java.util.List;

public class StepsViewEventsPage {
    private TestContext testContext;
    private EventPage eventPage;

    public StepsViewEventsPage(TestContext testContext) {
        this.testContext = testContext;
        this.eventPage = new EventPage();
    }

    public StepsViewEventsPage() {
        this(new TestContext());
    }

    @And("I should see list of events")
    public void i_should_see_list_of_events() {
        List<EventDetailDataObject> events = eventPage.getEventList();

        WebUI.verifyFalse(events.isEmpty(), "Event list is empty");

        WebUI.verifyTrue(events.size() >= 3,"Less than 3 events displayed");

        for (EventDetailDataObject event : events) {
            WebUI.verifyFalse(event.getTitle().isEmpty());
            WebUI.verifyFalse(event.getDate().isEmpty());
            WebUI.verifyFalse(event.getLocation().isEmpty());
            WebUI.verifyFalse(event.getPrice().isEmpty());
        }
    }

    @And("each event should display title, date, location, and price")
    public void each_event_should_display_title_date_location_and_price() {
        List<EventDetailDataObject> events = eventPage.getEventList();

        for (EventDetailDataObject event : events) {
            WebUI.verifyFalse(event.getTitle().isEmpty(), "Event title is missing");
            WebUI.verifyFalse(event.getDate().isEmpty(), "Event date is missing");
            WebUI.verifyFalse(event.getLocation().isEmpty(), "Event location is missing");
            WebUI.verifyFalse(event.getPrice().isEmpty(), "Event price is missing");
        }
    }
}
