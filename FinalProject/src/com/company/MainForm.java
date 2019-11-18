package com.company;

import com.company.Algorithm.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainForm extends JFrame {
    private JPanel mControlPanel;
    private JPanel mMainPanel;
    private JPanel mDataPanel;
    private JPanel mStatusPanel;
    private JComboBox mAlgorithmCbBox;
    private JScrollPane mSourceCode;
    private JTextField mRandomNumber;
    private JTextField mTextNumber;
    private JSlider mVelocSlider;
    private ArrayGUI mArrayGUI;
    private Thread mRunThread;
    private ArrayList<Algorithm> mAlgorithms;
    private ArrayList<String> mAlgorithmNames;
    private JLabel mStatusLabel;

    public MainForm() throws IOException {
        mDataPanel = new JPanel();
        mArrayGUI = new ArrayGUI(mDataPanel);
        createAlgorithm();

        initializeUI();
        changeAlgorithm(0);
    }

    private void fixSize(int controlSize) {
        mControlPanel.setBounds(0,0,getWidth(), controlSize);
        mDataPanel.setBounds(0,controlSize,getWidth(), getHeight()-controlSize-100);
        mStatusPanel.setBounds(0,getHeight()-100,getWidth(),100);
    }

    public void resetArrayGUI() {
        Configuration.getInstance().notifyStatus("");
        if (mRunThread != null)
            mRunThread.stop();
        mArrayGUI.resetLabels();
        mDataPanel.removeAll();
        showArray();
    }

    public void createAlgorithm() throws IOException {
        mAlgorithms = new ArrayList<>();
        mAlgorithms.add(new SelectionSort("/Users/vyta/Documents/OOP_Final/Algorithm/SelectionSort.txt", mArrayGUI));
        mAlgorithms.add(new QuickSort("/Users/vyta/Documents/OOP_Final/Algorithm/QuickSort.txt", mArrayGUI));
        mAlgorithms.add(new LinearSearch("/Users/vyta/Documents/OOP_Final/Algorithm/LinearSearch.txt", mArrayGUI));
        mAlgorithms.add(new BinarySearch("/Users/vyta/Documents/OOP_Final/Algorithm/BinarySearch.txt", mArrayGUI));

        mAlgorithmNames = new ArrayList<>();
        mAlgorithmNames.add("Selection Sort");
        mAlgorithmNames.add("Quick Sort");
        mAlgorithmNames.add("Linear Search");
        mAlgorithmNames.add("Binary Search");
    }

    public final void changeAlgorithm(int index) {
        if (mSourceCode != null)
            mControlPanel.remove(mSourceCode);
        mSourceCode = new JScrollPane(mAlgorithms.get(index).getListGUI());
        mSourceCode.setPreferredSize(mAlgorithms.get(index).getSizeSourceCode());
        mControlPanel.add(mSourceCode);

        if (index == 0 || index == 1)
            mTextNumber.setVisible(false);
        else {
            mTextNumber.setVisible(true);
            mTextNumber.setText("0");
        }

        fixSize(mSourceCode.getPreferredSize().height);
    }

    public final void createControlPanel() {
        mControlPanel = new JPanel();
        mControlPanel.setLayout(new FlowLayout());
        mControlPanel.setBorder(BorderFactory.createEtchedBorder());

        mMainPanel.add(mControlPanel);

        mRandomNumber = new JTextField();
        mRandomNumber.setPreferredSize(new Dimension(50, 30));
        mRandomNumber.setHorizontalAlignment(SwingConstants.CENTER);
        mRandomNumber.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!(ch >= '0' && ch <= '9'))
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        mRandomNumber.setText("10");

        JButton randomButton = new JButton("Random Data");
        randomButton.addActionListener(e -> {
            int number = Integer.parseInt(mRandomNumber.getText());
            if (number < 3 || number > 15) {
                JOptionPane.showMessageDialog
                        (MainForm.this, "Nhập số phần tử random từ 3 đến 15");
                return;
            }

            mArrayGUI.randomData(number);
            resetArrayGUI();
        });

        mAlgorithmCbBox=new JComboBox(mAlgorithmNames.toArray());
        mAlgorithmCbBox.addActionListener(e -> {
            int index = mAlgorithmCbBox.getSelectedIndex();
            changeAlgorithm(index);

            resetArrayGUI();

            mMainPanel.updateUI();
        });

        mTextNumber = new JTextField();
        mTextNumber.setPreferredSize(new Dimension(50, 30));
        mTextNumber.setHorizontalAlignment(SwingConstants.CENTER);
        mTextNumber.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!(ch >= '0' && ch <= '9'))
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        mTextNumber.setVisible(false);

        mVelocSlider = new JSlider();
        mVelocSlider.setPreferredSize(new Dimension(125, 30));
        mVelocSlider.setMinimum(0);
        mVelocSlider.setMaximum(14);
        mVelocSlider.setValue(5);

        mVelocSlider.addChangeListener(e -> {
            int value = 15 - mVelocSlider.getValue();
            Configuration.getInstance().setSleepTime(value);
        });

        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            if (mArrayGUI.getData().size() == 0) {
                JOptionPane.showMessageDialog
                        (MainForm.this, "Random dữ liệu trước khi chạy mô phỏng");
                return;
            }

            int algo = mAlgorithmCbBox.getSelectedIndex();

            resetArrayGUI();
            mMainPanel.updateUI();

            if (algo == 2 || algo == 3)
                ((SearchAlgorithm)mAlgorithms.get(algo)).setNumber(Integer.parseInt(mTextNumber.getText()));

            mAlgorithms.get(algo).initialize();
            mRunThread = mAlgorithms.get(algo).execute();
        });

        mControlPanel.add(mRandomNumber);
        mControlPanel.add(randomButton);
        mControlPanel.add(mAlgorithmCbBox);
        mControlPanel.add(mTextNumber);
        mControlPanel.add(mVelocSlider);
        mControlPanel.add(runButton);
    }

    public final void createStatusPanel() {
        mStatusPanel = new JPanel();
        mStatusPanel.setLayout(new FlowLayout());
        mStatusPanel.setBorder(BorderFactory.createEtchedBorder());

        mStatusLabel = new JLabel();
        Configuration.getInstance().setStatusLabel(mStatusLabel);
        mStatusLabel.setForeground(Color.RED);
        mStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //mStatusLabel.setPreferredSize(new Dimension(getWidth(), 100));
        mStatusLabel.setFont(new Font(mStatusLabel.getFont().getName(), Font.PLAIN, 40));
        mStatusPanel.add(mStatusLabel);

        mMainPanel.add(mStatusPanel);
    }

    public final void createDataPanel() {
        mMainPanel.add(mDataPanel);
        mDataPanel.setLayout(null);
        mDataPanel.setBorder(BorderFactory.createEtchedBorder());
    }

    public final void initializeUI() {

        mMainPanel = new JPanel();
        Configuration.getInstance().setMainPanel(mMainPanel);

        add(mMainPanel);
        setTitle("Final Project");
        setSize(930, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createControlPanel();
        createDataPanel();
        createStatusPanel();

        mMainPanel.setLayout(null);
    }

    public void showArray() {
        for (Component comp : mDataPanel.getComponents()) {
            if (comp.getName() == "DataArray")
                mDataPanel.remove(comp);
        }

        ArrayList<JLabel> panels = mArrayGUI.getLabels();

        int width = mDataPanel.getWidth();
        int height = mDataPanel.getHeight();

        for (int i = 0; i < panels.size(); i++) {
            mDataPanel.add(panels.get(i));
        }
        mMainPanel.updateUI();
    }
}


