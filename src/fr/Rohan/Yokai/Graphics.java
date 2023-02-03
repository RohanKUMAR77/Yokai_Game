package fr.Rohan.Yokai;

import java.util.ArrayList;

public class Graphics {
    //Fonction qui affiche les cartes et les cartes indices si elles sont placées
    public void printBoard(String[][] gameMatrix,String[][] hintMatrix) {
        for (int i = 0; i <16 ; i++) {
            for (int j = 0; j < 16; j++) {
                if (gameMatrix[i][j]!="0"){
                    //On prête attention au fait d'adapter les coordonnées matritielles à la fenêtre de jeu
                    StdDraw.picture(j, 15-i, gameMatrix[i][j], 1, 1);
                }
            }
        }
        for (int i = 0; i <16 ; i++) {
            for (int j = 0; j < 16; j++) {
                if (hintMatrix[i][j]!="0"){
                    //On prête attention au fait d'adapter les coordonnées matritielles à la fenêtre de jeu
                    StdDraw.picture(j, 15-i, hintMatrix[i][j], 1, 1);
                }
            }
        }

        //Repère pour les coordonnées
        for (int i = 1; i <16 ; i++) {
            StdDraw.text(i,15,"C"+i);
        }
        for (int i = 1; i < 16; i++) {
            StdDraw.text(0.2,15-i,"L"+i);
        }
    }
    public void ShowHints(ArrayList<String> pickedHints){
        StdDraw.text(2,14,"Picked Hints :");
        for (int i = 0; i < pickedHints.size() ; i++) {
            StdDraw.picture(7+i,14, pickedHints.get(i),1,1);
        }
    }
    //Fonction qui permet d'ajouter l'affichage des cartes indices qui ont été tirées
    public void establish(String[][] gameMatrix,ArrayList<String> pickedHints,String[][] hintMatrix){
        if (!pickedHints.isEmpty()){
            ShowHints(pickedHints);
        }
        printBoard(gameMatrix,hintMatrix);
    }
    //Fonction qui affiche les cartes retournées sans les cartes indices
    public void FinalPrintBoard(String[][] gameMatrix,ArrayList<String> pickedHints) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (gameMatrix[i][j] != "0") {
                    //On prête attention au fait de mettre les coordonnées matritielles adaptées à la fenêtre
                    StdDraw.picture(j, 15 - i, gameMatrix[i][j], 1, 1);
                }
            }
        }
        //Repère pour les coordonnées
        for (int i = 1; i <16 ; i++) {
            StdDraw.text(i,15,"C"+i);
        }
        for (int i = 1; i < 16; i++) {
            StdDraw.text(0.2,15-i,"L"+i);
        }
        if (!pickedHints.isEmpty()){
            ShowHints(pickedHints);
        }
        StdDraw.text(8,1,"HERE ARE THE RESULTS OF YOUR GAME !");

    }
}
