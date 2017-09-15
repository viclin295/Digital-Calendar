/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Wesley Zheng, Victor Lin - Team 3
 */
public class MainMenuUIView 
{
    public static void displayMainMenuUI() throws IOException
    {
   
    JFrame frame = new MainMenuFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Digital Organizer");
    frame.setVisible(true);
    frame.setSize(1000, 1000);
    }
}
