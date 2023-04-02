import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Test {
    
    private static final String DATE_PATTERN = "(\\d{2}[\\./-]\\d{2}[\\./-]\\d{4})";
    private static final String NAME_PATTERN = "([A-Za-z]+\\s+[A-Za-z]+)";
    private static final String EMAIL_PATTERN = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";

    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\Lenovo\\Downloads\\Tess4J\\tessdata");
        
        try {
            String text = tesseract.doOCR(new File("C:\\Users\\Lenovo\\Desktop\\Tess4J\\tessdata\\image.jpg"));
            extractDates(text);
            extractRoomNames(text);
            extractRoomRates(text);
            extractNames(text);
            extractEmails(text);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
    
    private static void extractDates(String text) {
        System.out.println("avalibale dates:");
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String date = matcher.group(0);
            String[] dateParts = date.split("[./-]");
            String formattedDate = String.format("%s-%s-%s", dateParts[2], dateParts[1], dateParts[0]);
            System.out.println(formattedDate);
        }
    }
    
    private static void extractRoomNames(String text) {
        System.out.println("room names:");
        Pattern pattern = Pattern.compile("Roam: ([A-Za-z]+),");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String roomName = matcher.group(1);
            System.out.println(roomName);
        }
    }
    
    private static void extractRoomRates(String text) {
        System.out.println("room rates:");
        Pattern pattern = Pattern.compile("Rate: \\$(\\d+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String rate = matcher.group(1);
            System.out.println("$" + rate);
        }
    }
    
    private static void extractNames(String text) {
        System.out.println("names:");
        Pattern pattern = Pattern.compile(NAME_PATTERN + ",\\s+" + NAME_PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String name = matcher.group(0);
            System.out.println(name);
        }
    }
    
    private static void extractEmails(String text) {
        System.out.println(" emails:");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String email = matcher.group(0);
            System.out.println(email);
        }
    }
}
