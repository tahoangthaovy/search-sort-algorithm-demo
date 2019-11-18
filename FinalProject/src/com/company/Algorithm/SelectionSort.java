package com.company.Algorithm;

import com.company.ArrayGUI;
import com.company.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionSort extends SortAlgorithm{
    public SelectionSort(String fileName, ArrayGUI arrayGUI) throws IOException {
        super(fileName, arrayGUI);
    }

    @Override
    public void initialize() {
        mIIndexLabel = createLabel();
        mJIndexLabel = createLabel();
        mMinIndexLabel = createLabel();

        mArrayGUI.getDataPanel().add(mIIndexLabel);
        mArrayGUI.getDataPanel().add(mJIndexLabel);
        mArrayGUI.getDataPanel().add(mMinIndexLabel);
    }

    private JLabel mIIndexLabel;
    private JLabel mJIndexLabel;
    private JLabel mMinIndexLabel;

    @Override
    protected void doExecute() throws InterruptedException {
        mMinIndexLabel.setText("MIN");

        ArrayList<Integer> data = mArrayGUI.getData();
        for (int i = 0; i < data.size() - 1; i++) {
            setLocation(mIIndexLabel, i, 2);
            mIIndexLabel.setText("i="+String.valueOf(i));
            setLocation(mMinIndexLabel, i, -2);

            int minIndex = i;
            chooseLine(2);

            mArrayGUI.highlightElement(minIndex, Color.BLUE);
            Configuration.getInstance().sleep(30);

            for (int j = i + 1; j < data.size(); j++) {
                setLocation(mJIndexLabel, j, 2);
                mJIndexLabel.setText("j="+String.valueOf(j));
                chooseLine(3);
                mArrayGUI.highlightElement(j, Color.GREEN);
                Configuration.getInstance().sleep(50);
                chooseLine(4);
                Configuration.getInstance().sleep(50);;
                if (data.get(minIndex) > data.get(j)) {
                    setLocation(mMinIndexLabel, j, -2);
                    chooseLine(5);
                    mArrayGUI.unHighlightElement(minIndex);
                    minIndex = j;
                    mArrayGUI.highlightElement(minIndex, Color.BLUE);
                    Configuration.getInstance().sleep(30);
                } else
                    mArrayGUI.unHighlightElement(j);
            }
            mArrayGUI.unHighlightElement(minIndex);

            chooseLine(6);
            mArrayGUI.swapElement(minIndex, i);

            mArrayGUI.highlightElement(i, new Color(190,225,190));
        }
        Configuration.getInstance().notifyStatus("Completed");
        mArrayGUI.highlightElement(data.size() - 1, new Color(190,225,190));
    }
}
