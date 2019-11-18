package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public abstract class SearchAlgorithm extends Algorithm {
    protected ArrayGUI mArrayGUI;
    protected Integer mNumber;

    public SearchAlgorithm(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName);
        mArrayGUI = arrayGUI;
    }

    protected JLabel mNumberLabel;

    public Point calculateLocation(int index, int pos) {
        Point locationLabel = mArrayGUI.getLocation(index);
        return new Point(locationLabel.x, locationLabel.y + pos * (Configuration.getInstance().getElementSize() + 1));
    }

    @Override
    public void initialize() {
        mNumberLabel = Configuration.getInstance().createElementLabel(mNumber.toString());
        mNumberLabel.setBackground(Color.RED);
        mArrayGUI.getDataPanel().add(mNumberLabel);
        Point location = calculateLocation(0,-2);
        int width = Configuration.getInstance().getElementSize();
        int height = width;
        mNumberLabel.setBounds(location.x, location.y, width, height);
    }

    public void setNumber(Integer number) {
        mNumber = number;
    }
}
