package ar.edu.itba.paw.model;

/*Bracket Tree holding de projected seed value*/
public class BracketNode {
    private BracketNode left;
    private BracketNode right;
    private Player player;
    private int seed;
    private BracketNode brother;
    private BracketNode parent;
    private int result;

    public BracketNode(int seed){
        this.player = player;
        this.left = null;
        this.right=null;
        this.seed = seed;
        this.brother = null;
        this.parent = null;
        this.result = -1;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public BracketNode getBrother() {
        return brother;
    }

    public void setBrother(BracketNode brother) {
        this.brother = brother;
    }

    public BracketNode getParent() {
        return parent;
    }

    public void setParent(BracketNode parent) {
        this.parent = parent;
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
