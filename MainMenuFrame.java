/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.Date;
import javax.swing.JOptionPane;



/**
 *
 * @author Wesley Zheng, Victor Lin - Team 3
 */

public final class MainMenuFrame extends JFrame
{
    //initializing variables and values
    String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    DefaultTableModel model = new DefaultTableModel(null,columns);
    Calendar cal = new GregorianCalendar();
    JTable table = new JTable(model);
    JLabel label, descriptionLabel, dateLabel, timeLabel, clockLabel, helpLabel, help2Label, help3Label;
    JTextField [] alarmTextField, alarmLabel;
    JButton prevButton, nextButton, saveButton, setAlarmButton, updateButton; 
    JPanel calendarPanel, menuPanel, timePanel, descriptionPanel, eventPanel, notiPanel, clockPanel, alarmPanel, editPanel;
    JTextArea message, description;
    JScrollPane pane, descriptionPane, eventPane;
    JTextField dateField, timeField;
    String month;
    JComboBox hour1Combo, minute1Combo,hour2Combo, minute2Combo;
    TableCellRenderer renderer;
    int year;
    int timerCount = 0;
    int numAlarm = 3;
    String [] alarms, tempAlarms;
    static Events e = new Events();
    
    //buttons and labels 
    public MainMenuFrame() throws IOException
    {
        this.alarmLabel = new JTextField[numAlarm];
        this.alarmTextField = new JTextField[numAlarm];
        alarms = new String[numAlarm];
        
        label = new JLabel("Calendar");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        message = new JTextArea();
        descriptionLabel = new JLabel("Description");
        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent ae) -> {
            String date = dateField.getText();
            String event = description.getText();
            String time = getTime();
            
            try {
                e.writeToText(event, time, date);
                JOptionPane.showMessageDialog(MainMenuFrame.this,
                            "Event Saved",
                            "Notification",
                            JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //update and overwrites the whole text file 
        updateButton = new JButton("Update");
        updateButton.addActionListener((ActionEvent ae) -> {
            String txtName = "";
            txtName = txtName + dateField.getText();
            Events update = new Events();
            try {
                update.updateToText(txtName, message.getText());
                JOptionPane.showMessageDialog(MainMenuFrame.this,
                            "Event Succesfully Updated",
                            "Notification",
                            JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        
        //button for going back and forth on the calendar
        prevButton = new JButton("<-");
        prevButton.addActionListener((ActionEvent ae) -> {
            cal.add(Calendar.MONTH, -1);
            try {
                updateMonth();
            } catch (IOException ex) {
                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        nextButton = new JButton("->");
        nextButton.addActionListener((ActionEvent ae) -> {
             cal.add(Calendar.MONTH, +1);
            try {
                updateMonth();
            } catch (IOException ex) {
                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        
        clockLabel = new JLabel("");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String string = new SimpleDateFormat("HH:mm:ss").format(new Date());
                clockLabel.setText(string);
                clockLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
            }
        }, 0, 1000);
        
        //Button for the alaram
        setAlarmButton = new JButton("Set Alarm");
        setAlarmButton.addActionListener((ActionEvent ae) -> {
        for(int g = 0; g < numAlarm; g++)
        {
                alarms[g] = alarmTextField[g].getText() + ":00";
        }
        JOptionPane.showMessageDialog(MainMenuFrame.this,
                        "Alarm has been set.",
                        "",
                        JOptionPane.INFORMATION_MESSAGE);
        
        //Calls the Alarm class and invokes the class
         Timer alarmTime = new Timer();
         alarmTime.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (String alarm : alarms) {
                    if (!(alarm.equals(""))) {
                        if (alarm.equals(clockLabel.getText())) {   
                            Alarm ring = new Alarm();
                            try {
                                ring.ringAlarm();
                            } catch (IOException ex) {
                                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }, 0, 1000);
        });
        createPanel();
    }
    
    //panels for the calendar 
    public void createPanel() throws IOException
    {
        menuPanel =  new JPanel();
        menuPanel.setPreferredSize(new Dimension(1000,1000));
        menuPanel.setLayout(new GridLayout(2,2));
        //Edit Panel
        editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        editPanel.setOpaque(true);
        editPanel.setBackground(Color.WHITE);
        editPanel.setBorder(BorderFactory.createTitledBorder(""));
        descriptionPane = new JScrollPane(message);
        editPanel.add(descriptionPane, BorderLayout.CENTER);
        editPanel.add(updateButton, BorderLayout.SOUTH);
        
        //Description Panel
        descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setOpaque(true);
        descriptionPanel.setBackground(Color.WHITE);
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
        descriptionPanel.add(editPanel, BorderLayout.CENTER);
            
            //Event Panel
            eventPanel = new JPanel();
            eventPanel.setLayout(new BorderLayout());
            eventPanel.setBackground(Color.WHITE);
            eventPanel.setBorder(BorderFactory.createTitledBorder(""));
            eventPane = new JScrollPane(description);
            eventPanel.setPreferredSize(new Dimension(300,200));
            eventPanel.add(descriptionLabel);
            eventPanel.add(eventPane, BorderLayout.CENTER);
            eventPanel.add(saveButton, BorderLayout.SOUTH);
        
        descriptionPanel.add(eventPanel, BorderLayout.WEST);
        
            //notifcation panel
            notiPanel = new JPanel();
            notiPanel.setBackground(Color.WHITE);
            notiPanel.setBorder(BorderFactory.createTitledBorder(""));
            notiPanel.setPreferredSize(new Dimension(300,150));
            dateLabel = new JLabel("Date - Month_Day_Year");
            dateField = new JTextField(15);
            dateField.setEditable(false);

            
            //TIME COMBO BOX
            JLabel colon = new JLabel(":");
            JLabel colon2 = new JLabel(":");
            
            hour1Combo = new JComboBox();
            hour1Combo.setEditable(true);
        
            minute1Combo = new JComboBox();
            minute1Combo.setEditable(true);

            hour2Combo = new JComboBox();
            hour2Combo.setEditable(true);

            minute2Combo = new JComboBox();
            minute2Combo.setEditable(true);
            
            //LOOP FOR HOURS
            int add;
            for(int hour1 = 0; hour1 < 24; hour1++)
            {
                String min;
                add = hour1;
                min = "" + add;
                if(min.length() < 2)
                {
                    min = "0" + min;
                }

                hour1Combo.addItem(min);
                hour2Combo.addItem(min);
            }
            
            //loop for minutes
            for(int minute1 = 0; minute1 < 60; minute1++)
            {
                String min;
                add = minute1;
                min = "" + add;
                if(min.length() < 2)
                {
                    min = "0" + min;
                }
                minute1Combo.addItem(min);
                minute2Combo.addItem(min);
            }
            
            notiPanel.add(dateLabel);
            notiPanel.add(dateField);
            notiPanel.add(hour1Combo);
            notiPanel.add(colon);
            notiPanel.add(minute1Combo);
            notiPanel.add(hour2Combo);
            notiPanel.add(colon2);
            notiPanel.add(minute2Combo);
            
            
            
        eventPanel.add(notiPanel, BorderLayout.NORTH);

        //Alarm Panel
        alarmPanel = new JPanel();
        alarmPanel.setOpaque(true);
        alarmPanel.setBackground(Color.WHITE);
        alarmPanel.setLayout(new FlowLayout());
        alarmPanel.setPreferredSize(new Dimension(150,40));
        helpLabel = new JLabel("****Please use HH:MM fomat****");
        help2Label = new JLabel("***Please use 24 hr clock***");
        help3Label = new JLabel("<html>Click & Hightlight 'Alarm #'<br>To Change Description</html>");
        helpLabel.setForeground(Color.RED);
        help2Label.setForeground(Color.RED);
        help3Label.setForeground(Color.RED);
        
        alarmPanel.add(help3Label);
        for(int p = 0; p < numAlarm; p++)
        {
            alarmLabel[p] = new JTextField("Alarm " + (p + 1));
            alarmLabel[p].setBorder(BorderFactory.createEmptyBorder());
            alarmLabel[p].setBackground(alarmPanel.getBackground());
            alarmTextField[p] = new JTextField();
            alarmTextField[p].setColumns(12);
            alarmPanel.add(alarmLabel[p]);
            alarmPanel.add(alarmTextField[p]);
        }
        
        alarmPanel.add(helpLabel);
        alarmPanel.add(help2Label);
        alarmPanel.add(setAlarmButton);
        
        clockPanel = new JPanel();
        clockPanel.setLayout(new BorderLayout());
        clockPanel.setOpaque(true);
        clockPanel.setBackground(Color.WHITE);
        clockPanel.setBorder(BorderFactory.createTitledBorder("Alarm"));
        clockPanel.add(clockLabel, BorderLayout.NORTH);
        clockPanel.add(alarmPanel, BorderLayout.WEST);
        descriptionPanel.add(clockPanel, BorderLayout.EAST);
        
        //CalendarPanel
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setOpaque(true);
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(prevButton,BorderLayout.WEST);
        buttonPanel.add(label,BorderLayout.CENTER);
        buttonPanel.add(nextButton,BorderLayout.EAST);
        
        table = new JTable(model) {
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component comp = super.prepareRenderer(renderer, row, col);
        try {
            if (e.searchEvent(month + "_" + table.getValueAt(row, col) + "_" + year) ==  true)
            {
                comp.setBackground(Color.LIGHT_GRAY);
            }
            else
            {
                comp.setBackground(Color.WHITE);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            updateMonth();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comp;
        }
    };  
        
        table.setRowHeight(75);
        pane = new JScrollPane(table);
        
        //Mouse Event for Calendar
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            dateField.setText(month + "_" + table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())
                                        + "_" + year);
            String txtName = "";
            txtName = txtName + dateField.getText();
            Events p = new Events();
            try {
                if(p.searchEvent(txtName) == true)
                {
                    try {
                        message.setText("");
                        setMessage(txtName);
                    } catch (IOException ex) {
                        Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   } catch (IOException ex) {
                Logger.getLogger(MainMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });

        calendarPanel.add(buttonPanel,BorderLayout.NORTH);
        calendarPanel.add(pane,BorderLayout.CENTER);
        updateMonth();
        
        menuPanel.add(descriptionPanel);
        menuPanel.add(calendarPanel);
        add(menuPanel);
    }
    
  //GET TIME FROM COMBOBOXES
   private String getTime() {
        String createTime;

        createTime = String.valueOf(hour1Combo.getSelectedItem()) + ":" + String.valueOf(minute1Combo.getSelectedItem()) + 
                " to " + String.valueOf(hour2Combo.getSelectedItem()) + ":" + String.valueOf(minute2Combo.getSelectedItem());
        return createTime;
    }
    
  public void updateMonth() throws IOException 
  {
    cal.set(Calendar.DAY_OF_MONTH, 1);
 
    month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    year = cal.get(Calendar.YEAR);
    label.setText(month + " " + year);
 
    int startDay = cal.get(Calendar.DAY_OF_WEEK);
    int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
 
    model.setRowCount(0);
    model.setRowCount(weeks);
 
    int i = startDay-1;
    for(int day=1;day<=numberOfDays;day++){
      model.setValueAt(day, i/7 , i%7 );    
      i = i + 1;
    }
  }
  
  public void setMessage(String date) throws IOException
  {
      message.setText(e.readText(date));
  }

}
