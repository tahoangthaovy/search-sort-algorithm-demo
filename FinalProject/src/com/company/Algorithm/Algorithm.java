package com.company.Algorithm;

import com.company.Configuration;
import sun.management.snmp.jvminstr.JvmClassLoadingImpl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
    protected JList<String> mListGUI;

    private List<String> mSourceCode;
    public Algorithm(String fileName) throws IOException {
        mSourceCode = Files.readAllLines(Paths.get(fileName));
        mListGUI = new JList<>();
        mListGUI.setListData(getSourceCode());
        mListGUI.setBorder(BorderFactory.createEtchedBorder());
    }

    public Dimension getSizeSourceCode() {
        int maxLength = 0;
        for (int i = 0; i < mSourceCode.size(); i++)
            maxLength = Math.max(mSourceCode.get(i).length(), maxLength);

        return new Dimension((maxLength + 4) * 5, (mSourceCode.size() + 1) * 18);
    }

    protected String[] getSourceCode() {
        String[] result = new String[mSourceCode.size()];
        int i = 0;
        for (String s : mSourceCode)
            result[i++] = s;
        return result;
    }

    public JList<String> getListGUI() {
        return mListGUI;
    }

    void chooseLine(int line) {
        mListGUI.setSelectedIndex(line);
    }

    protected void moveXLabelTo(JLabel label, int x, int step) throws InterruptedException {
        if (step == 0)
            return;
        int sign = 1;
        if (x < label.getLocation().x)
            sign = -1;
        while (true) {
            int labelX = label.getLocation().x;
            if (x == labelX)
                break;
            int fixStep = Math.min(step, Math.abs(x-labelX));
            label.setLocation(label.getLocation().x + sign * fixStep, label.getLocation().y);
            Configuration.getInstance().sleep(1);
        }
    }

    public abstract void initialize();

    protected abstract void doExecute() throws InterruptedException;

    public Thread execute() {
        Thread result = new Thread(() -> {
            try {
                doExecute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        result.start();
        return result;
    }
}
