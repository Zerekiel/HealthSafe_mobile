package com.eipteam.healthsafe.nfc_manager.display;

public class Element {
    private final String label;
    private String data;

    public Element (String _label, String _data) {
        label = _label;
        data = _data;
    }

    public String getText() {
        return label;
    }

    public String getData() {
        return data;
    }

    public void setData(String _data) {
        data = _data;
    }
}
