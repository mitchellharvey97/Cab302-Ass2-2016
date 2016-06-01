/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private JPanel pnlDisplay;
    private JPanel pnlTwo;
    private JPanel pnlBtn;
    private JPanel pnlFour;
    private JPanel pnlFive;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnSwitch;

    private JTextArea areDisplay;

    /**
     * @param arg0
     * @throws HeadlessException
     */
    public GUISimulator(String arg0) throws HeadlessException {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        createGUI();
    }

    /**
     * @param args
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        //JFrame.setDefaultLookAndFeelDecorated(false);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        pnlDisplay = createPanel();//Color.WHITE);
        pnlTwo = createPanel();//Color.BLACK);
        pnlBtn = createPanel();//Color.BLUE);
        pnlFour = createPanel();//Color.RED);
        pnlFive = createPanel();//Color.YELLOW);

        btnLoad = createButton("Load");
        btnUnload = createButton("Unload");
        btnFind = createButton("Find");
        btnSwitch = createButton("Switch");

        areDisplay = createTextArea();

        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(areDisplay, BorderLayout.CENTER);

        layoutButtonPanel();

        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlTwo, BorderLayout.NORTH);
        this.getContentPane().add(pnlBtn, BorderLayout.SOUTH);
        this.getContentPane().add(pnlFour, BorderLayout.EAST);
        this.getContentPane().add(pnlFive, BorderLayout.WEST);
        repaint();
        
        this.setVisible(true);
    }

    private JPanel createPanel() { //Color c) {
        JPanel jp = new JPanel();
        //jp.setBackground(c);
        return jp;
    }

    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }

    private JTextArea createTextArea() {
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setFont(new Font("Arial", Font.BOLD, 24));
        jta.setBorder(BorderFactory.createEtchedBorder());
        return jta;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);

        // add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;

        addToPanel(pnlBtn, btnLoad, constraints, 0, 0, 2, 1);
        addToPanel(pnlBtn, btnUnload, constraints, 3, 0, 2, 1);
        addToPanel(pnlBtn, btnFind, constraints, 0, 2, 2, 1);
        addToPanel(pnlBtn, btnSwitch, constraints, 3, 2, 2, 1);
    }

    /**
     * 
     * A convenience method to add a component to given grid bag layout
     * locations. Code due to Cay Horstmann
     *
     * @param c
     *            the component to add
     * @param constraints
     *            the grid bag constraints to use
     * @param x
     *            the x grid position
     * @param y
     *            the y grid position
     * @param w
     *            the grid width
     * @param h
     *            the grid height
     */
    private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get event source
        Object src = e.getSource();

        // Consider the alternatives - not all active at once.
        if (src == btnLoad) {
            JButton btn = ((JButton) src);
            areDisplay.setText(btn.getText().trim());
        } else if (src == btnUnload) {
            JButton btn = ((JButton) src);
            areDisplay.setText(btn.getText().trim());
        } else if (src == btnSwitch) {
            JOptionPane.showMessageDialog(this, "A Warning Message", "Wiring Class: Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else if (src == btnFind) {
            JOptionPane.showMessageDialog(this, "An Error Message", "Wiring Class: Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
