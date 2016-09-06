package ceasar.com.myapplication;

/**
 * Created by ceejay562 on 8/13/2016.
 */
public class Player {
    private String name;
    private int numOfHits;
    private int numOfAtBats;

    public Player(String name){
        this.name = name;
        this.numOfAtBats = 0;
        this.numOfHits = 0;
    }

    public void hit(){
        numOfAtBats++;
        numOfHits++;
    }

    public void out(){
        numOfAtBats++;
    }

    public String getName(){
        return name;
    }

    public void changeName(String name){
        this.name = name;
    }

    public int getNumOfHits(){
        return numOfHits;
    }

    public int getNumOfAtBats(){
        return numOfAtBats;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");
        sb.append(numOfHits);
        sb.append(" for ");
        sb.append(numOfAtBats);
        sb.append(")");
        // name(1 for 4)
        return sb.toString();
    }
}
