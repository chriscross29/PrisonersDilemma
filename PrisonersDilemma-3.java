/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * main class
 * 
 * @author Chris D'Mello 1566105
 * @author Jayin Nejal 1537199
 * assignment group 9
 * 
 * assignment copyright Kees Huizing 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.SwingUtilities;

class PrisonersDilemma extends JFrame implements ChangeListener, ActionListener, ItemListener {

    private PlayingField field;
    private JPanel fieldPanel;
    private JPanel gui;
    private JPanel bottom;
    private JPanel top;
    private JButton goPause;
    private JButton reset;
    private JCheckBox rule;
    private JLabel deflection;
    private JLabel frequency;
    private JSlider dSlider;
    private JSlider fSlider;

    void buildGUI() {
        SwingUtilities.invokeLater(() -> {
            field = new PlayingField(); // panel of type PlayingField
            fieldPanel = new JPanel();
            fieldPanel.setPreferredSize(new Dimension(530, 530));
            fieldPanel.add(field);
            add(fieldPanel, BorderLayout.CENTER);

            gui = new JPanel();
            gui.setLayout(new GridLayout(2, 1));

            top = new JPanel();
            top.setLayout(new FlowLayout());

            deflection = new JLabel("deflection α = 0.0", SwingConstants.CENTER);
            top.add(deflection);

            dSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
            dSlider.setMajorTickSpacing(10);
            dSlider.setPaintTicks(true);

            java.util.Hashtable<Integer, JLabel> dLabelTable = new java.util.Hashtable<Integer, JLabel>();
            for (int i = 0; i <= 30; i += 10) {
                dLabelTable.put(i, new JLabel(Double.toString(i / 10)));
            }
            dSlider.setLabelTable(dLabelTable);
            dSlider.setPaintLabels(true);
            top.add(dSlider);

            dSlider.addChangeListener(this);

            frequency = new JLabel("frequency = 1  ", SwingConstants.CENTER);
            top.add(frequency);

            fSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 1);
            fSlider.setMajorTickSpacing(10);
            fSlider.setPaintTicks(true);

            java.util.Hashtable<Integer, JLabel> fLabelTable = new java.util.Hashtable<Integer, JLabel>();
            fLabelTable.put(0, new JLabel(Integer.toString(1)));
            for (int i = 10; i <= 60; i += 10) {
                fLabelTable.put(i, new JLabel(Integer.toString(i)));
            }
            fSlider.setLabelTable(fLabelTable);
            fSlider.setPaintLabels(true);
            top.add(fSlider);
            fSlider.addChangeListener(this);

            gui.add(top);

            bottom = new JPanel();
            bottom.setLayout(new FlowLayout());

            goPause = new JButton("GO");
            bottom.add(goPause);
            goPause.addActionListener(this);

            rule = new JCheckBox("Alternative Update Rule");
            bottom.add(rule);
            rule.addItemListener(this);

            reset = new JButton("Reset");
            bottom.add(reset);
            reset.addActionListener(this);

            gui.add(bottom);

            add(gui, BorderLayout.SOUTH);

            this.pack();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        double alpha = dSlider.getValue() / 10.0; // stores alpha value from the slider
        field.setAlpha(alpha);
        deflection.setText("deflection α = " + alpha);
        int freq = fSlider.getValue(); // stores speed / frequency value from the slider
        field.setFrequency(freq);
        field.speed();
        if (freq == 0) {
            frequency.setText("frequency = " + (freq + 1));
        } else {
            frequency.setText("frequency = " + freq);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goPause) {
            if ("GO".equals(e.getActionCommand())) {
                field.startStop(true);
                goPause.setText("Pause");
            } else if ("Pause".equals(e.getActionCommand())) {
                field.startStop(false);
                goPause.setText("GO");
            }
        } else if (e.getSource() == reset) {
            field.resetGrid();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (rule.isSelected()) {
            field.setRule(true);
        } else {
            field.setRule(false);
        }
    }

    public static void main(String[] a) {
        (new PrisonersDilemma()).buildGUI();
    }
}