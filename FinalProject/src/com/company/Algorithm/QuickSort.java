package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class QuickSort extends SortAlgorithm {
    public QuickSort(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName, arrayGUI);
    }

    @Override
    public void initialize() {
        mIIndexLabel = createLabel();
        mJIndexLabel = createLabel();
        mLeftLabel = createLabel();
        mRightLabel = createLabel();

        mArrayGUI.getDataPanel().add(mIIndexLabel);
        mArrayGUI.getDataPanel().add(mJIndexLabel);
        mArrayGUI.getDataPanel().add(mLeftLabel);
        mArrayGUI.getDataPanel().add(mRightLabel);
    }

    private JLabel mIIndexLabel;
    private JLabel mJIndexLabel;
    private JLabel mLeftLabel;
    private JLabel mRightLabel;

    @Override
    protected void doExecute() throws InterruptedException {
        ArrayList<Integer> data = mArrayGUI.getData();
        quickSort(data, 0, data.size() - 1);
        Configuration.getInstance().notifyStatus("Completed");
    }

    void setIJIndex(int i, int j) {
        if (i < 0 || i >= mArrayGUI.getData().size() || j < 0 || j >= mArrayGUI.getData().size())
            return;
        setLocation(mIIndexLabel, i, -2);
        mIIndexLabel.setText("i="+ i);
        setLocation(mJIndexLabel, j, -2);
        mJIndexLabel.setText("j="+ j);
        if (i == j) {
            mJIndexLabel.setVisible(false);
            mIIndexLabel.setText("i,j="+i);
        }
    }

    void setLRIndex(int i, int j) {
        if (i < 0 || i >= mArrayGUI.getData().size() || j < 0 || j >= mArrayGUI.getData().size())
            return;
        setLocation(mLeftLabel, i, 2);
        mLeftLabel.setText("L="+ i);
        setLocation(mRightLabel, j, 2);
        mRightLabel.setText("R="+ j);
        if (i == j) {
            mRightLabel.setVisible(false);
            mLeftLabel.setText("L,R="+i);
        }
    }

    private void quickSort(ArrayList<Integer> data, int l, int r) throws InterruptedException {
        mIIndexLabel.setVisible(true);
        mJIndexLabel.setVisible(true);
        mLeftLabel.setVisible(true);
        mRightLabel.setVisible(true);

        chooseLine(0);
        Configuration.getInstance().sleep(5);

        int key = data.get((l + r) / 2);
        Configuration.getInstance().notifyStatus("Key = " + key);

        int i = l, j = r;

        setIJIndex(i,j);
        setLRIndex(l,r);

        chooseLine(3);
        Configuration.getInstance().sleep(5);
        while (i <= j) {
            while (data.get(i) < key) {
                setIJIndex(i,j);
                i++;
                chooseLine(4);
                Configuration.getInstance().sleep(35);
            }

            while (data.get(j) > key) {
                setIJIndex(i,j);
                j--;
                chooseLine(5);
                Configuration.getInstance().sleep(35);
            }

            if (i <= j) {
                if (i < j) {
                    chooseLine(7);
                    Configuration.getInstance().sleep(35);
                    mArrayGUI.swapElement(i, j);
                }
                i++;
                j--;
                setIJIndex(i,j);
            }
        }

        if (l < j) {
            chooseLine(11);
            Configuration.getInstance().sleep(50);
            quickSort(data, l, j);
        }

        if (i < r) {
            quickSort(data, i, r);
            chooseLine(12);
            Configuration.getInstance().sleep(5);
        }

        for (int p = l; p <= r; p++) {
            mArrayGUI.highlightElement(p, new Color(190,225,190));
        }

        chooseLine(13);
    }
}
