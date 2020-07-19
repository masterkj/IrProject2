package utils;

public class HelpingFunctions {
    public static String removeDecimeters(String line){
        return String.join(" ",line.split("[ \n\t\r.,;%^&*:!?(){}]"));
    }
}
