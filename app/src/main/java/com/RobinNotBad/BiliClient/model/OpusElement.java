package com.RobinNotBad.BiliClient.model;

public class OpusElement {
    public enum ElementType {LINE, PICTURE}

    ElementType type;
    CharSequence content;

    public OpusElement(){}

    public OpusElement(ElementType type, CharSequence content) {
        this.type = type;
        this.content = content;
    }
}
