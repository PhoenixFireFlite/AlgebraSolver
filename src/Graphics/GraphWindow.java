package Graphics;

import javax.swing.*;

public class GraphWindow {

    private JFrame frame;

    GraphWindow(){
        initWindow();
    }

    private void initWindow(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPanel(JPanel panel){
        panel.setSize(frame.getSize());
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String args[]){ new GraphWindow(); }

}
