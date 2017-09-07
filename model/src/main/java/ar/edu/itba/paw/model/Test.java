package ar.edu.itba.paw.model;

public class Test {
    public static void main(String[] args){
        Tournament t = new Tournament();
        t.setSize(128);
        t.CreateBracket();
        t.bracketToString(t.getBracket());


    }


}
