package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BinarySearch extends SearchAlgorithm {

    public BinarySearch(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName, arrayGUI);
    }

    private JLabel mLeftLabel;
    private JLabel mRightLabel;
    private JLabel mMidLabel;

    private JLabel createLabel(String name) {
        JLabel result = Configuration.getInstance().createElementLabel(name);
        result.setFont(new Font(result.getFont().getName(), Font.PLAIN, 15));
        result.setForeground(Color.BLUE);
        return result;
    }

    protected void setLocation(JLabel label, int index) {
        Point location = calculateLocation(index,1);
        int width = Configuration.getInstance().getElementSize();
        int height = width;
        label.setBounds(location.x, location.y + 5, width, height);
    }


    @Override
    public void initialize() {
        super.initialize();
        mArrayGUI.sortData();

        mLeftLabel = createLabel("L");
        mRightLabel = createLabel("R");
        mMidLabel = createLabel("M");

        mArrayGUI.getDataPanel().add(mLeftLabel);
        mArrayGUI.getDataPanel().add(mRightLabel);
        mArrayGUI.getDataPanel().add(mMidLabel);
    }

    @Override
    protected void doExecute() throws InterruptedException {
        binarySearch(mArrayGUI.getData(), 0, mArrayGUI.getData().size() - 1);
    }

    private void binarySearch(ArrayList<Integer> data, int l, int r) throws InterruptedException {
        mMidLabel.setVisible(false);
        if (r >= l) {
            chooseLine(0);

            if (l < r) {
                setLocation(mLeftLabel, l);
                setLocation(mRightLabel, r);
            } else {
                setLocation(mLeftLabel, l);
                mRightLabel.setVisible(false);
                mLeftLabel.setText("L,R");
            }

            int mid = (l + r) / 2;
            mArrayGUI.highlightElement(mid, Color.GREEN);
            Configuration.getInstance().sleep(30);

            mMidLabel.setVisible(true);
            if (mid != l)
                setLocation(mMidLabel, mid);
            else {
                mMidLabel.setVisible(false);
                mLeftLabel.setText(mLeftLabel.getText() + ",M");
            }

            chooseLine(3);
            int x = mArrayGUI.getLocation(mid).x;
            moveXLabelTo(mNumberLabel, x, 2);
            mArrayGUI.moveYLabel(mid, -1, Configuration.getInstance().getElementSize());
            Configuration.getInstance().sleep(100);

            if (data.get(mid) == mNumber) {
                chooseLine(4);
                Configuration.getInstance().sleep(40);
                //chooseLine(6);
                mArrayGUI.highlightElement(mid, Color.BLUE);
                Configuration.getInstance().sleep(40);
                Configuration.getInstance().notifyStatus("BINGO!!");
                return;
            }
            else {
                mArrayGUI.unHighlightElement(mid);
                mArrayGUI.moveYLabel(mid, 1, Configuration.getInstance().getElementSize());

                chooseLine(5);
                Configuration.getInstance().sleep(40);

                if (data.get(mid) > mNumber) {
                    for (int i = r; i >= mid; i--) {
                        mArrayGUI.highlightElement(i, new Color(190,190,190));
                        Configuration.getInstance().sleep(30);
                    }

                    chooseLine(6);
                    Configuration.getInstance().sleep(50);
                    binarySearch(data, l, mid - 1);
                }
                else {
                    chooseLine(7);
                    Configuration.getInstance().sleep(40);

                    for (int i = l; i <= mid; i++) {
                        mArrayGUI.highlightElement(i, new Color(190,190,190));
                        Configuration.getInstance().sleep(30);
                    }

                    chooseLine(8);
                    Configuration.getInstance().sleep(50);
                    binarySearch(data, mid + 1, r);
                }
            }
        } else {
            Configuration.getInstance().notifyStatus("404");
            chooseLine(11);
        }
    }
}
