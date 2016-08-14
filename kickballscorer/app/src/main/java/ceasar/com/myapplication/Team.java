package ceasar.com.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceejay562 on 8/13/2016.
 */

public class Team {
    private String teamName;
    private int points;
    private List<Player> players;
    private int currKickPosition;

    public Team(String teamName){
        this.teamName = teamName;
        points = 0;
        currKickPosition = 0;
        players = new ArrayList<>();
    }

    public Player getCurrentKicker(){
        return players.get(currKickPosition);
    }

    public int getCurrKickPosition(){
        return currKickPosition;
    }

    public String getTeamName(){
        return teamName;
    }

    public void nextKicker(boolean isOut){
        if(isOut){
            players.get(currKickPosition).out();
        }
        else{
            players.get(currKickPosition).hit();
        }
        // go to the front of the order if the last person kicked
        if(++currKickPosition == players.size())
            currKickPosition = 0;
    }

    public void addPoint(){
        points++;
    }

    public int getScore(){
        return points;
    }

    public void generatePlayers(int numOfPlayers){
        for(int i=0; i<numOfPlayers; i++){
            players.add(new Player("Player " + i));
        }
    }

    public void addPlayer(String playerName, int kickingNum){
        players.add(kickingNum - 1, new Player(playerName));
        regernatePlayerNames();
    }

    public void changePlayerName(String name, int kickingNum){
        players.get(kickingNum).changeName(name);
    }

    public int getNumOfPlayers(){
        return players.size();
    }

    /** Rename the players name that were auto-generated to match their batting order **/
    private void regernatePlayerNames(){
        for(int i=0; i<players.size(); i++){
            Player player = players.get(i);
            // check if the players name matches the auto-generated names
            if(player.getName().matches("Player \\d")){
                player.changeName("Player " + i);
            }
        }
    }
}
