package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        Tournament t = new Tournament();
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Ariel",1));
        players.add(new Player("Escurridizo",2));
        players.add(new Player("Marcos",3));
        players.add(new Player("Cyrano",4));
        players.add(new Player("Mang0",5));
        players.add(new Player("Armada",6));
        players.add(new Player("Reynad",7));
        players.add(new Player("Hafu",8));
        t.setSize(players.size());
        t.setPlayers((ArrayList<Player>) players);
        t.CreateBracket();
        t.getRounds().get(0).setResult(2);
        t.getRounds().get(1).setResult(0);
        t.getRounds().get(2).setResult(1);
        t.getRounds().get(3).setResult(2);
        t.getRounds().get(4).setResult(1);
        t.getRounds().get(5).setResult(2);
        t.getRounds().get(6).setResult(2);
        t.getRounds().get(7).setResult(3);
        t.resolve(t.getRounds().get(0));
        t.resolve(t.getRounds().get(2));
        t.resolve(t.getRounds().get(4));
        t.resolve(t.getRounds().get(6));
        t.resolve(t.getRounds().get(8));
        t.resolve(t.getRounds().get(10));
        for(int i=players.size();i<players.size()+(players.size()/2);i++){
            System.out.print("\t"+t.getRounds().get(i).getSeed() + " " + t.getRounds().get(i).getPlayer().getName()+ "\t\t");
        }
        System.out.println();
        System.out.println();
        for(int i=0;i<players.size();i++){
            System.out.print(t.getRounds().get(i).getSeed() + " " + t.getRounds().get(i).getPlayer().getName()+ " ");
            if((i+1)%2==0){
                System.out.print("|");
            }
        }
        System.out.println();




    }


}
