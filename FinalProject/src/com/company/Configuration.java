package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Configuration {
    private static Configuration instance = null;
    public static Configuration getInstance() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }
    protected Configuration() {
        mSleepTime = 15;
        mElementSize = 50;
        mElementFS = 30;
    }

    private int mSleepTime;
    private int mElementSize;
    private int mElementFS; // font size
    private JPanel mMainPanel;
    private JLabel mStatusLabel;

    public int getSleepTime() {
        return mSleepTime;
    }

    public void setSleepTime(int val) {
        mSleepTime = val;
    }

    public int getElementSize() {
        return mElementSize;
    }

    public int getElementFS() {
        return mElementFS;
    }

    public void setMainPanel(JPanel panel) {
        mMainPanel = panel;
    }

    public JPanel getMainPanel() {
        return mMainPanel;
    }

    public void setStatusLabel(JLabel label) {
        mStatusLabel = label;
    }

    public void notifyStatus(String text) {
        mStatusLabel.setText(text);
    }

    public JLabel createElementLabel(String text) {
        JLabel result = new JLabel(text);
        result.setForeground(Color.RED);
        result.setBorder(LineBorder.createGrayLineBorder());
        result.setHorizontalAlignment(SwingConstants.CENTER);

        // Size
        int elementSize = Configuration.getInstance().getElementSize();

        result.setPreferredSize(new Dimension(elementSize, elementSize));
        result.setFont(new Font(result.getFont().getName(), Font.PLAIN, Configuration.getInstance().getElementFS()));

        return result;
    }

    public void sleep(int times) throws InterruptedException {
        Thread.sleep(times * mSleepTime);
    }
}
