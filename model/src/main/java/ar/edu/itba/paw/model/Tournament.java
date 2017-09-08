package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tournament {
    private String name;
    private ArrayList<Player> players; /*If the total number is not a power of 2, it should be filled with BYES*/
    private int id;
    private BracketNode bracket;
    private int size;
    private List<BracketNode> rounds; /*This should make the tournament iteration simpler*/

    public Tournament(String name, int id){
        this.players = new ArrayList<Player>();
        this.name = name;
        this.id = id;
        this.bracket = new BracketNode(0);
        this.rounds = new LinkedList<BracketNode>();
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<BracketNode> getRounds() { return rounds; }

    public void setRounds(List<BracketNode> firstRound) { this.rounds = firstRound; }

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
        int totalDepth = (int) (Math.log(size)/Math.log(2));/*Size should always be a power of 2*/
        createBracketRecursive(depth, bracket, totalDepth);
    }
    private void createBracketRecursive(double depth, BracketNode current,int totalDepth){
        if(depth>totalDepth){
            /*We could hold the parent's seed value, in order to add the player to the bracket right away*/
            /*Otherwise we would have to iterate the tree once more adding the players to the leafs*/
            current.setPlayer(players.get(current.getSeed()-1));
            rounds.add(current);
            return;
        }
        /*Sets childs*/
        current.setLeft(new BracketNode(current.getSeed()));
        current.setRight(new BracketNode((int) ((Math.pow(2,depth))-current.getSeed()+1)));
        current.getLeft().setBrother(current.getRight());
        current.getRight().setBrother(current.getLeft());
        current.getRight().setParent(current);
        current.getLeft().setParent(current);
        createBracketRecursive(depth+1,current.getLeft(),totalDepth);
        createBracketRecursive(depth+1,current.getRight(),totalDepth);
        current.setSeed(0);
    }
    /*Ties cant happen in a tournament*/
    /*This method could be Overwritten for a Double Elimination Tournament*/
    public void resolve(BracketNode node){
        BracketNode brother = node.getBrother();
        if(node.getResult() == -1 || brother.getResult() == -1 || node.getResult() == brother.getResult()){
            return; /*Should let know the user that he is trying to end an inconclusive match*/
        }
        if(node.getResult() > brother.getResult()){
            node.getParent().setPlayer(node.getPlayer());
            node.getParent().setSeed(node.getSeed());
        }else{
            brother.getParent().setPlayer(brother.getPlayer());
            brother.getParent().setSeed(brother.getSeed());
        }
        rounds.add(node.getParent());

        return;
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
