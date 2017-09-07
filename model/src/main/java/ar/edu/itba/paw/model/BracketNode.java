package ar.edu.itba.paw.model;

/*Bracket Tree holding de projected seed value*/
public class BracketNode {
    private BracketNode left;
    private BracketNode right;
    private Player player;
    private int seed;

    public BracketNode(int seed){
        this.player = player;
        this.left = null;
        this.right=null;
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public BracketNode getLeft() {
        return left;
    }

    public void setLeft(BracketNode left) {
        this.left = left;
    }

    public BracketNode getRight() {
        return right;
    }

    public void setRight(BracketNode right) {
        this.right = right;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
