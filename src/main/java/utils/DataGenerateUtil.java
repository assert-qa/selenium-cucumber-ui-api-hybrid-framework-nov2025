package utils;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static helpers.DataFakerHelper.*;

public class DataGenerateUtil {
    private static final List<String> TITLES = Arrays.asList("Mr.", "Mrs.");
    private static final List<String> COUNTRIES = Arrays.asList("India","United States","Canada","Australia","Israel","New Zealand","Singapore");
    private static final List<String> SUBJECTS = Arrays.asList("Product Inquiry", "Technical Support",
            "Billing Issue", "Product Problem",
            "Feedback & Suggestions", "Partnership Request");
    private static final List<String> CATEGORY = Arrays.asList("WOMEN", "MEN", "KIDS");

    @Test
    public void testData(){
        System.out.println(getProgrammingLanguage());
        LogUtils.info(getProgrammingLanguage());
    }

    public static String getProgrammingLanguage(){
        return getFaker().programmingLanguage().name();
    }

    public static String getTitle(){
        return TITLES.get(getRandomNumber(0, TITLES.size()));
    }

    public static String getName(){
        return getFaker().name().fullName();
    }

    public static String getEmail(){
        return getFaker().internet().emailAddress();
    }

    public static String getPassword(){
        return getFaker().internet().password();
    }

    public static String getDate(){
        return String.valueOf(getFaker().number().numberBetween(1, 32));
    }

    public static String getMonth(){
        return getRandomMonth();
    }

    public static String getYear(){
        return String.valueOf(getFaker().number().numberBetween(1900, 2021));
    }

    public static String getFirstName(){
        return getFaker().name().firstName();
    }

    public static String getLastName(){
        return getFaker().name().lastName();
    }

    public static String getCompany(){
        return getFaker().company().name();
    }

    public static String getAddress(){
        return getFaker().address().streetAddress();
    }

    public static String getAddress2(){
        return getFaker().address().buildingNumber();
    }

    public static String getCountry(){
        return COUNTRIES.get(getRandomNumber(0, COUNTRIES.size()));
    }

    public static String getState(){
        return getFaker().address().state();
    }

    public static String getCity() {
        return getFaker().address().cityName();
    }

    public static String getZipCode(){
        return getFaker().address().zipCode();
    }

    public static String getMobileNumber(){
        return getFaker().phoneNumber().cellPhone();
    }

    public static String getSubject(){
        return SUBJECTS.get(getRandomNumber(0, SUBJECTS.size()));
    }

    public static String getMessage(){
        return getFaker().lorem().sentence();
    }

    public static String getCardNumber(){
        return getFaker().finance().creditCard();
    }

    public static String getCVC(){
        return getFaker().number().digits(3);
    }

    public static String getCategories(){
        return CATEGORY.get(getRandomNumber(0, CATEGORY.size()));
    }

}
