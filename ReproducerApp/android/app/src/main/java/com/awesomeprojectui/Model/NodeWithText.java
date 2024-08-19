package com.blockerplus.blockerplus.Model;

import android.view.accessibility.AccessibilityNodeInfo;

public class NodeWithText {
    private AccessibilityNodeInfo node;
    private String text;
    private int level;

    public NodeWithText(AccessibilityNodeInfo node, String text, int level) {
        this.node = node;
        this.text = text;
        this.level= level;
    }

    public AccessibilityNodeInfo getNode() {
        return node;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }
}