package project3;

import java.awt.*;
import javax.swing.*;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class:            SplashScreen
 * File:               SplashScreen.java
 * Description:     Shows a splash screen for a predetermined time, after time, returns to main() in GUIDriver
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.1
 * Date:         10/31/2016
 * @version         1.0
 * @see         javax.swing
 * History Log:  10/31/2016, 01/14/2017, 02/13/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class SplashScreen extends JWindow 
{
    //create a progress bar object
    JProgressBar loading = new JProgressBar();
    //initializes class level variables
    private int duration;
    private int progress;
    private String splashScreenImage = "src/images/splash.jpg";
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Instructor SplashScreen
     * Description sets the class level variable duration as the passed parameter
     * @author      Mel Leggett
     * Date             10/31/2016
     * @param dur duration for flash screen to run
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public SplashScreen(int dur)
    {
        duration = dur;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method           showSplash()
     *  Description     Defines the size and layout of the splash screen as well as sets how long it will
     *                         display from the time given in instructor
     * @author          Mel Leggett
     * Date                 10/31/2016
     * @see         javax.swing
     * @see         java.awt.BorderLayout
     * @see         java.awt.Color
     * @see         java.awt.Dimension
     * @see         java.awt.Toolkit
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void showSplash()
    {
        //set background of JPanel
        String splashImage = splashScreenImage;
                
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.lightGray);
        
        //set size
        int width = 592;
        int height = 473;
        //center splash
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width)/2;
        int y = (screen.height - height)/2;
        setBounds(x,y,width,height);
        
        //put splash screen image into center and progress bar into South
        JLabel label = new JLabel(new ImageIcon(splashImage)); 
        content.add(label, BorderLayout.CENTER);
        content.add(loading, BorderLayout.SOUTH);
        
        //set border thickness and color
        Color border = new Color(50, 20, 20, 55);
        content.setBorder(BorderFactory.createLineBorder(border, 10));
        
        setVisible(true);
        
        try 
        {
            incProgress(100);
            Thread.sleep(duration);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.dispose(); //kills splash screen
    } //end show splash
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       incProgress()
     * Description  initiate instance of inner class object
     * @author      Mel Leggett
     * Date             10/31/2016
     * @param incProgress progress of the splash bar
     * @throws InterruptedException 
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void  incProgress(int incProgress) throws InterruptedException
    {
        //initiate instance of inner class object
        IncProgress up = new IncProgress(incProgress);
        up.myThread.start();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Inner Class          IncProgress
     * Description          Increments progress bar
     * @author          Mel Leggett
     * Date                 10/31/2016
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    class IncProgress
    {
        private int howfar;
        /***********************************************************************
         * Instructor IncProgress
         * Description  sets value of parameter value to 'howfar' class variable
         * @author  Mel Leggett
         * Date         10/31/2016
         * @param value 
         *********************************************************************/
        public IncProgress(int value)
        {
            howfar = value;
        }
        private Thread myThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //increment the progress bar until its value hits 100
                while (progress < (progress + howfar))
                {
                    progress++;
                    try
                    {
                        Thread.sleep(20);
                    }
                    catch(InterruptedException ex) {
                        
                    }
                    //updating progress bar
                    loading.setValue(progress);
                }
                
            }
        });
    }
}

