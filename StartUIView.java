/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import javax.swing.JFrame;

/**
 *
 * @author wxz5098
 */
public class StartUIView
{
   public static void displayStartUI()
   {
      JFrame frame = new StartUIFrame();
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setTitle("Digital Organizer");
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
      frame.setSize(350, 125);
   }
}
