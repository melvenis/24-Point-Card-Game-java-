package project3;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   Player
 * File:     Player.java
 * Description:  Builds a player object that inherits from Player with statistics
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/07/2017
 * @version         1.0
 * History Log:  03/07/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Player extends Person{
    private int gamesPlayed;
    private int gamesWon;
    private int winStreak;
    private int recordStreak;
    private int recordSpeed;
    private boolean timerIO; //if timer is on or off
    private int timerSeconds; //time allowed to solve hand
    private final int DEFAULT_TIMER = 45; //default seconds to solve
    
    //Default constructor
    public Player() {
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Overloaded constructor
     * Description builds a new Player object with name only(from Person class)
     * @param name name of the new player
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Player(String name) {
        super(name);
        timerIO = true; //defaul player includes timer
        timerSeconds = DEFAULT_TIMER;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Overloaded Constructor
     * Description builds the player object with all fields passed in to the constructor
     * @param name name of the player
     * @param gamesPlayed total games played
     * @param gamesWon total games won
     * @param winStreak consecutive current wins
     * @param recordStreak most consecutive wins ever
     * @param recordSpeed  fastest solution ever
     * @param timerIO on off switch for timer state
     * @param timerSeconds amount of time in seconds for timer to run
     * date 03/06/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Player(String name, int gamesPlayed, int gamesWon, int winStreak, 
            int recordStreak, int recordSpeed, boolean timerIO, int timerSeconds) {
        super(name);
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.winStreak = winStreak;
        this.recordStreak = recordStreak;
        this.recordSpeed = recordSpeed;
        this.timerIO = timerIO;
        this.timerSeconds = timerSeconds;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getGamesPlayed()
     * @return total games played
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setGamesPlayed
     * @param gamesPlayed the total games played
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * getGamesWon
     * @return total games won
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * setGamesWon
     * @param gamesWon total games won
     */
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getWinStreak
     * @return current win streak
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getWinStreak() {
        return winStreak;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setWinStreak
     * @param winStreak sets the current win Streak
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getRecordStreak()
     * @return the most consecutive wins ever
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getRecordStreak() {
        return recordStreak;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setRecordStreak
     * @param recordStreak the most consecutive wins
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setRecordStreak(int recordStreak) {
        this.recordStreak = recordStreak;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getRecordSpeed()
     * @return fastest solution ever
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getRecordSpeed() {
        return recordSpeed;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setRecordSpeed
     * @param recordSpeed set the fastest solution
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setRecordSpeed(int recordSpeed) {
        this.recordSpeed = recordSpeed;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * isTimerIO()
     * @return boolean if timer is on or off
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public boolean isTimerIO() {
        return timerIO;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setTimerIO()
     * @param timerIO if timer ON/OFF
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setTimerIO(boolean timerIO) {
        this.timerIO = timerIO;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getTimerSeconds()
     * @return timerSeconds total time to solve hand
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getTimerSeconds() {
        return timerSeconds;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setTimerSeconds
     * @param timerSeconds the total time to solve the hand
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setTimerSeconds(int timerSeconds) {
        this.timerSeconds = timerSeconds;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * toString()
     * Override to return all the variables in String
     * @return String of all variables
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public String toString() {
        return this.getName() + "," + gamesPlayed + "," +  gamesWon + "," + winStreak + 
                "," + recordStreak + "," + recordSpeed + "," + timerIO + "," + timerSeconds +  "\n";
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * equals()
     * Description compares to Player objects by Name
     * @param pl player to extract name to compare
     * @return true/false on objects are equal
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public boolean equals(Player pl) {
        return (this.getName().equals(pl.getName()));
    }
}
