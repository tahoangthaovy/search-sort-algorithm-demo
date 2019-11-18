package com.company;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class ArrayGUI {
    private ArrayList<Integer> mData = new ArrayList<Integer>();
    private ArrayList<JLabel> mLabels = new ArrayList<JLabel>();
    private JPanel mDataPanel;

    public ArrayGUI(JPanel mDataPanel) {
        this.mDataPanel = mDataPanel;
    }

    public boolean setData(int index, Integer newValue) {
        if (index < 0 || index >= mData.size())
            return false;
        mData.set(index, newValue);
        mLabels.get(index).setText(mData.get(index).toString());
        return true;
    }

    public JPanel getDataPanel() {
        return mDataPanel;
    }

    public void resetLabels() {
        for (int i = 0; i < mData.size(); i++) {
            JLabel label = mLabels.get(i);
            Point pos = calculatePosition(mDataPanel.getWidth(), mDataPanel.getHeight(), mData.size(), i + 1);
            label.setBackground(null);
            label.setBounds(pos.x, pos.y, Configuration.getInstance().getElementSize(), Configuration.getInstance().getElementSize());
        }
    }

    public void randomData(int number) {
        mData.clear();
        mLabels.clear();

        Random random = new Random();
        for (int i = 0; i < number; i++) {
            Integer randomValue = Math.abs(random.nextInt()) % 100;
            mData.add(randomValue);
            JLabel label = Configuration.getInstance().createElementLabel(randomValue.toString());
            label.setName("DataArray");
            mLabels.add(label);

            resetLabels();
        }
    }

    private Point calculatePosition(int width ,int height, int nElement, int eIndex) {
        Point result = new Point();

        int elementSize = Configuration.getInstance().getElementSize();

        int middle = 10; // khoảng cách 2 element
        result.y = height / 2 - elementSize / 2;
        int allSize = nElement * elementSize + (nElement - 1) * middle;
        result.x = width / 2 - allSize / 2 + eIndex * elementSize + (eIndex - 1) * middle - elementSize;

        return result;
    }
    // set location of component
    // component.x = labels.get(index).x
    // component.y = labels.get(index).y + pos * (elementSize + 5)
//    public void setLocation(Component component, int index, int pos) {
//        component.setBounds
//                (getLabels().get(index).getLocation().x,
//                        getLabels().get(index).getLocation().y + pos * (mElementSize + 5), mElementSize, mElementSize);
//    }

    private void swapData(int i, int j) {
        int temp = mData.get(i);
        mData.set(i, mData.get(j));
        mData.set(j, temp);
    }

    private void swapGUI(int left, int right) throws InterruptedException {
        JLabel leftLabel = mLabels.get(left);
        JLabel rightLabel = mLabels.get(right);

        for (int i = 0; i < leftLabel.getSize().height + 5; i++) {
            leftLabel.setLocation(leftLabel.getLocation().x, leftLabel.getLocation().y + 1);
            rightLabel.setLocation(rightLabel.getLocation().x, rightLabel.getLocation().y  - 1);
            Configuration.getInstance().sleep(1);
        }

        int distanceTwoLabel = Math.abs(leftLabel.getLocation().x - rightLabel.getLocation().x);
        for (int i = 0; i < distanceTwoLabel; i++) {
            leftLabel.setLocation(leftLabel.getLocation().x + 1, leftLabel.getLocation().y);
            rightLabel.setLocation(rightLabel.getLocation().x - 1, rightLabel.getLocation().y);
            Configuration.getInstance().sleep(1);
        }

        for (int i = 0; i < leftLabel.getSize().height + 5; i++) {
            leftLabel.setLocation(leftLabel.getLocation().x, leftLabel.getLocation().y - 1);
            rightLabel.setLocation(rightLabel.getLocation().x, rightLabel.getLocation().y  + 1);
            Configuration.getInstance().sleep(1);
        }

        JLabel temp = mLabels.get(left);
        mLabels.set(left, mLabels.get(right));
        mLabels.set(right, temp);
    }

    public void swapElement(int i, int j) throws InterruptedException {
        if (i == j)
            return;
        swapData(i,j);
        int left = Math.min(i,j);
        int right = Math.max(i,j);
        swapGUI(left, right);
    }

    public void highlightElement(int index, Color color) {
        JLabel label = mLabels.get(index);
        label.setBackground(color);
        mDataPanel.updateUI();
        label.setOpaque(true);
    }

    public void unHighlightElement(int index) {
        JLabel label = mLabels.get(index);
        label.setBackground(null);
        label.setOpaque(false);
    }

    public void moveYLabel(int index, int step, int times) throws InterruptedException {
        JLabel label = mLabels.get(index);
        for (int i = 0; i < times; i++) {
            label.setLocation(label.getLocation().x, label.getLocation().y + step);
            Configuration.getInstance().sleep(1);
        }
    }

    public void moveXLabel(int index, int step, int times) throws InterruptedException {
        JLabel label = mLabels.get(index);
        for (int i = 0; i < times; i++) {
            label.setLocation(label.getLocation().x + step, label.getLocation().y);
            Configuration.getInstance().sleep(1);
        }
    }

    public Point getLocation(int index) {
        return mLabels.get(index).getLocation();
    }

    public ArrayList<JLabel> getLabels() {
        return mLabels;
    }

    public ArrayList<Integer> getData() {
        return mData;
    }

    public void sortData() {
        for (int i = 0; i < mData.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < mData.size(); j++)
                if (mData.get(j) < mData.get(minIndex))
                    minIndex = j;

            if (i != minIndex) {
                Integer temp = mData.get(i);
                mData.set(i, mData.get(minIndex)); mData.set(minIndex, temp);

                Rectangle bounds = mLabels.get(i).getBounds();
                mLabels.get(i).setBounds(mLabels.get(minIndex).getBounds()); mLabels.get(minIndex).setBounds(bounds);
                JLabel tempLB = mLabels.get(i);
                mLabels.set(i, mLabels.get(minIndex)); mLabels.set(minIndex, tempLB);
            }
        }
    }
}
