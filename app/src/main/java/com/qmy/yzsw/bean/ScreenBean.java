package com.qmy.yzsw.bean;

public class ScreenBean {
    private String screen;
    private String screenStr;
    private int screenImage;
    private int type;
    private boolean isChoice;

    public ScreenBean(String screen, String screenStr, int screenImage, int type, boolean isChoice) {
        this.screen = screen;
        this.screenStr = screenStr;
        this.screenImage = screenImage;
        this.type = type;
        this.isChoice = isChoice;
    }

    public boolean isChoice() {

        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }


    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getScreenStr() {
        return screenStr;
    }

    public void setScreenStr(String screenStr) {
        this.screenStr = screenStr;
    }

    public int getScreenImage() {
        return screenImage;
    }

    public void setScreenImage(int screenImage) {
        this.screenImage = screenImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
