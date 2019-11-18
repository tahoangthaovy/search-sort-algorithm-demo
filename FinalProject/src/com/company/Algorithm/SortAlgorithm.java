package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public abstract class SortAlgorithm extends Algorithm{
    protected ArrayGUI mArrayGUI;

    public SortAlgorithm(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName);
        mArrayGUI = arrayGUI;
    }

    protected Point calculateLocation(int index, int pos) {
        Point locationLabel = mArrayGUI.getLocation(index);
        return new Point(locationLabel.x, locationLabel.y + pos * Configuration.getInstance().getElementSize());
    }

    protected JLabel createLabel() {
        JLabel result = Configuration.getInstance().createElementLabel("");
        result.setFont(new Font(result.getFont().getName(), Font.PLAIN, 18));
        result.setForeground(Color.BLUE);
        return result;
    }

    protected void setLocation(JLabel label, int index, int pos) {
        Point location = calculateLocation(index,pos);
        int width = Configuration.getInstance().getElementSize();
        int height = width;
        label.setBounds(location.x, location.y + ((pos > 0) ? 10 : -10), width, height);
    }

    protected void swap(int i, int j) throws InterruptedException {
        mArrayGUI.swapElement(i,j);
    }
}
