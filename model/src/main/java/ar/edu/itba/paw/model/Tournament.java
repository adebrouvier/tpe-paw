package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tournament {
    private ArrayList<Player> players; /*If the total number is not a power of 2, it should be filled with BYES*/
    private int id;
    private BracketNode bracket;
    private int size;

    public Tournament(){
        this.players = new ArrayList<Player>();
        this.id = 1;
        this.bracket = new BracketNode(0);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BracketNode getBracket() {
        return bracket;
    }

    public void setBracket(BracketNode bracket) {
        this.bracket = bracket;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    /*Recursively arranges projected bracket*/
    public void CreateBracket(){
        bracket.setSeed(1);
        int depth = 1;
        BracketNode current;
        int totalDepth = (int) (Math.log(size)/Math.log(2));/*Size should always be a power of 2*/
        createBracketRecursive(depth, bracket, totalDepth);
    }
    private void createBracketRecursive(double depth, BracketNode current,int totalDepth){
        if(depth>totalDepth){
            /*We could hold the parent's seed value, in order to add the player to the bracket right away*/
            /*Otherwise we would have to iterate the tree once more adding the players to the leafs*/
            return;
        }
        current.setLeft(new BracketNode(current.getSeed()));
        current.setRight(new BracketNode((int) ((Math.pow(2,depth))-current.getSeed()+1)));
        createBracketRecursive(depth+1,current.getLeft(),totalDepth);
        createBracketRecursive(depth+1,current.getRight(),totalDepth);
    }

    /*Method used for testing purpose*/
    public void bracketToString(BracketNode current){
        if(current == null){
            return;
        }
        bracketToString(current.getLeft());
        System.out.println(current.getSeed());
        bracketToString(current.getRight());

    }

}
