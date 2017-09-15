/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Wesley Zheng, Victor Lin - Team 3
 */
public class Events 
{
    public Events()
    {
        
    }
    
    public void writeToText(String event, String time, String date) throws IOException
    {
        String outputFileName = "" + date + ".txt";

        File outputFile = new File(outputFileName);
        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile, true))) {
            out.println();
            out.printf("%s %s", time + "\n", event);
            System.out.printf("%s %s", time, event);
        }
    }
    
    public String readText(String date)throws IOException
    {
        String inputFileName = date + ".txt";
        String readDescription = "";
        String description = "";
        
        File inputFile = new File(inputFileName);
        try (Scanner inFile = new Scanner(inputFile)) {
            while(inFile.hasNextLine())
            {
                readDescription = inFile.nextLine();
                
                description += readDescription + "\n";
            }
        }
        
        return description;
    }
    
    public void updateToText(String date, String description) throws FileNotFoundException
    {
        String outputFileName = date + ".txt";

        File outputFile = new File(outputFileName);
        PrintWriter out = new PrintWriter(outputFile);

        out.print(description);

        out.close();
    }
    
    public boolean searchEvent(String date)throws IOException
    {
        File event = new File(date + ".txt");
        if(event.exists())
        {
            return true;
        }
        return false;
    }
}
