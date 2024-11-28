package app;

import settings.Settings;

public class Main {
    public static void main(String[] args) {

        Settings settings = Settings.getInstance();

        System.out.println(settings.getSettings());


    }
}