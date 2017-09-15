/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Wesley Zheng, Victor Lin - Team 3
 */
public class StartUIFrame extends JFrame
{
    JButton startButton, quitButton;
    
    public StartUIFrame()
    {
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100,50));
        quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(100,50));
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainMenuUIView.displayMainMenuUI();
                } catch (IOException ex) {
                    Logger.getLogger(StartUIFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        
        quitButton.addActionListener((ActionEvent e) -> {
        dispose();
        });
        
        createPanel();
    }
    
    public void createPanel()
    {
        JPanel template = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        
        template.setLayout(new BorderLayout());
        template.add(buttonPanel,BorderLayout.CENTER);
        
        add(template);
    }
}
