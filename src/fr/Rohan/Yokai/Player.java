package fr.Rohan.Yokai;

import java.util.ArrayList;
import java.util.Scanner;


public class Player {
    public ArrayList<String> names;

    public Player(ArrayList<String> names){
        this.names=names;
    }

    public void createPlayers(int nb_Players){
        ArrayList<String> Player_names = new ArrayList<>();
        for (int i = 1; i < nb_Players + 1; i++) {
            System.out.println("Player" + i + " name ?");
            Scanner scanner = new Scanner(System.in);
            String Player_name = scanner.nextLine();
            Player_names.add(Player_name);
        }
        this.names=Player_names;
    }
    public ArrayList<String> getNames(){return names;}
    public void turn(int k){System.out.println("it is "+names.get(k)+"'s turn to play !");System.out.println();;StdDraw.text(8,1, names.get(k)+" is playing");}
}

