package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LinearSearch extends SearchAlgorithm {

    public LinearSearch(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName, arrayGUI);
    }

    private JLabel mIndexLabel;

    @Override
    public void initialize() {
        super.initialize();

        mIndexLabel = Configuration.getInstance().createElementLabel("");
        mIndexLabel.setForeground(Color.BLUE);
        mArrayGUI.getDataPanel().add(mIndexLabel);
        Point location = calculateLocation(0,1);
        int width = Configuration.getInstance().getElementSize();
        int height = width;
        mIndexLabel.setBounds(location.x, location.y + 5, width, height);
        mIndexLabel.setFont(new Font(mIndexLabel.getFont().getName(), Font.PLAIN, 18));
    }

    private void setIndexLabel(int index) {
        Rectangle rect = mIndexLabel.getBounds();
        Point local = mArrayGUI.getLocation(index);

        mIndexLabel.setBounds(local.x, rect.y, rect.width, rect.height);
        mIndexLabel.setText("i = " + String.valueOf(index));
    }

    @Override
    protected void doExecute() throws InterruptedException {
        ArrayList<Integer> data = mArrayGUI.getData();

        for (int i = 0; i < data.size(); i++) {
            setIndexLabel(i);
            chooseLine(1);

            int x = mArrayGUI.getLocation(i).x;
            moveXLabelTo(mNumberLabel, x, 1);

            chooseLine(2);
            mArrayGUI.highlightElement(i, Color.GREEN);
            Configuration.getInstance().sleep(30);
            mArrayGUI.moveYLabel(i, -1, Configuration.getInstance().getElementSize());
            chooseLine(3);
            // compare
            Configuration.getInstance().sleep(50);
            if (data.get(i) == mNumber) {
                chooseLine(4);
                mArrayGUI.highlightElement(i, Color.BLUE);
                Configuration.getInstance().sleep(40);
                Configuration.getInstance().notifyStatus("BINGO!!");
                return;
            }
            mArrayGUI.moveYLabel(i, 1, Configuration.getInstance().getElementSize());
            mArrayGUI.highlightElement(i, new Color(190,190,190));
        }

        Configuration.getInstance().notifyStatus("404");
        chooseLine(6);
    }
}
