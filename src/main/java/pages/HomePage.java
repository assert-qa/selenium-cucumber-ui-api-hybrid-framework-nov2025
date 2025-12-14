package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LogUtils;

import java.util.List;
import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class HomePage extends DriverFactory {

    Properties setUp = loadAllFiles();

    HomePage() {

    }

    // 1. By Locators
    String landingPageImage = setUp.getProperty("LANDING_PAGE_HOME_IMG");
    String footer = setUp.getProperty("HOME_FOOTER");
    String subscriptionLabel = setUp.getProperty("CONTACT_US_SUBSCRIPTION_LABEL");
    String subscribeEmail = setUp.getProperty("SUBSCRIBE_EMAIL");
    String subscribeButton = setUp.getProperty("HOME_SUBSCRIBE_BUTTON");
    String successMessage = setUp.getProperty("SUCCESS_MESSAGE_LABEL");
    String categoryLabel = setUp.getProperty("CATEGORY_LABEL");
    String categoryBy = setUp.getProperty("CATEGORY_BY");
    String womenCategoryExtend = setUp.getProperty("WOMEN_CATEGORY_EXTEND");
    String womenCategoryExtend2 = setUp.getProperty("WOMEN_CATEGORY_EXTEND_2");
    String menCategoryExtend = setUp.getProperty("MEN_CATEGORY_EXTEND");
    String menCategoryExtend2 = setUp.getProperty("MEN_CATEGORY_EXTEND_2");
    String kidsCategoryExtend = setUp.getProperty("KIDS_CATEGORY_EXTEND");
    String kidsCategoryExtend2 = setUp.getProperty("KIDS_CATEGORY_EXTEND_2");
    String clickedCategoryPageLabel = setUp.getProperty("CLICKED_CATEGORY_PAGE_LABEL");
    String recommendedItemsIsVisible = setUp.getProperty("RECOMMENDED_ITEMS_IS_VISIBLE");
    String addToCartButton = setUp.getProperty("ADD_TO_CART_BUTTON");
    String viewCart = setUp.getProperty("VIEW_CART");
    String clickScrollUpButton = setUp.getProperty("CLICK_SCROLL_UP_BUTTON");
    String scrollUpLabel = setUp.getProperty("SCROLL_UP_LABEL");

    // VERIFY SUBSCRIPTION IN HOME PAGE TEST STEP
    public boolean verifyLandingPage(){
        return isElementDisplayed(By.xpath(landingPageImage));
    }

    public void scrollDownToFooter(){
        scrollToElement(By.id(footer));
    }

    public String verifySubscriptionIsVisible(){
        verifyElementVisible(By.xpath(subscriptionLabel));
        return getElementText(By.xpath(subscriptionLabel));
    }

    public void setEmail(String userEmail){
        setText(By.xpath(subscribeEmail), userEmail);
    }

    public void clickSubscribeButton(){
        clickElement(By.xpath(subscribeButton));
    }

    public String verifySuccessMessage(){
        verifyElementVisible(By.xpath(successMessage));
        return getElementText(By.xpath(successMessage));
    }

    // VERIFY CATEGORY IN HOME PAGE TEST STEP
    public String verifyCategoryIsVisible(){
        verifyElementVisible(By.xpath(categoryLabel));
        return getElementText(By.xpath(categoryLabel));
    }

    public void clickWomenCategoryExtend(){
        if (isElementDisplayed(By.xpath(womenCategoryExtend))){
            clickElement(By.xpath(womenCategoryExtend));
        }else{
            clickElement(By.xpath(womenCategoryExtend2));
        }
    }

    public void clickMenCategoryExtend(){
        if (isElementDisplayed(By.xpath(menCategoryExtend))){
            clickElement(By.xpath(menCategoryExtend));
        }else{
            clickElement(By.xpath(menCategoryExtend2));
        }
    }

    public void clickKidsCategoryExtend(){
        if (isElementDisplayed(By.xpath(kidsCategoryExtend))){
            clickElement(By.xpath(kidsCategoryExtend));
        }else{
            clickElement(By.xpath(kidsCategoryExtend2));
        }
    }

    public int getCategoryCount(){ // WOMEN, MEN, KIDS
        waitForElementPresent(By.xpath(categoryBy));
        List<WebElement> category = getWebElements(By.xpath(categoryBy));

        LogUtils.info("Category size: " + category.size());
        return category.size();
    }

    public String getCategoryName(int categoryCount){
        List<WebElement> list = getWebElements(By.xpath(categoryBy));

        if (categoryCount < 0 || categoryCount >= list.size()){
            LogUtils.error("Invalid category index: " + categoryCount);
        }
        String categoryByDetails = list.get(categoryCount).getText().trim();
        LogUtils.info("Category found: " + categoryByDetails);

        return categoryByDetails;
    }

    public void clickProductCategory(String byCategory, String productByCategory){
        String xpath = String.format("//div[@id='%s']//a[contains(text(),'%s')]", byCategory, productByCategory);

        clickElement(By.xpath(xpath));
        LogUtils.info("Element: " + xpath + " was clicked!");
    }

    public String verifyCategoryPageAccordingSelectedCategoryAndProduct(){
        verifyElementVisible(By.cssSelector(clickedCategoryPageLabel));
        return getElementText(By.cssSelector(clickedCategoryPageLabel));
    }

    // ADD TO CART FROM RECOMMENDATIONS TEST STEP
    public String verifyRecommendationItemsIsVisible(){
        verifyElementVisible(By.xpath(recommendedItemsIsVisible));
        return getElementText(By.xpath(recommendedItemsIsVisible));
    }

    public void clickAddRecommendedItemToCart(){
        clickElement(By.xpath(addToCartButton));
    }

    public void clickViewCartButton(){
        clickElement(By.xpath(viewCart));
    }

    // SCROLL UP USING 'ARROW' BUTTON AND SCROLL DOWN FUNCTIONALITY
    public void clickScrollUpButton(){
        clickElement(By.xpath(clickScrollUpButton));
    }

    public String verifyLabelWhenScrollUp(){
        verifyElementVisible(By.xpath(scrollUpLabel));
        return getElementText(By.xpath(scrollUpLabel));
    }

    public void scrollToTopOfAPage(){
        scrollToElement(By.id(landingPageImage));
    }

    // ROUGH (METHOD HELPER)
    public static String capitalizeFirstLetter(String str){
        if (str == null || str.isEmpty()){
            return str;
        }

        String [] words = str.split(" ");
        StringBuilder capitalizedWords = new StringBuilder();

        for (String word : words){
            if (!word.isEmpty()){
                capitalizedWords.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toUpperCase())
                        .append(" "); // Add space back
            }
        }
        return capitalizedWords.toString().trim(); // Trim trailing space
    }
}