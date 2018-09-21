package com.example.laptophome.myapplication;

public class view_pager_item {
    private String text;
    private int pic;

    public void setText(String text) {
        this.text = text;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getText() {

        return text;
    }

    public view_pager_item(String text, int pic) {
        this.text = text;
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public static class Main_page {
    }
}
