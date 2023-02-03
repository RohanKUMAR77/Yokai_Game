package fr.Rohan.Yokai;
import java.util.*;

public class Board {
    public String[][] gameMatrix;
    public String[][] revealedMatrix=new String[16][16];
    //on va utiliser 2 matrices l'une étant la matrice que le joueur voit
    // et l'autre où toutes les cartes sont retournées.

    public String[][] getGameMatrix() {
        return gameMatrix;
    }

    public String[][] getRevealedMatrix() {return revealedMatrix;}

    public Board(String[][] gameMatrix) {
        this.gameMatrix=gameMatrix;
    }
//Fonction qui place les cartes faces cachées au milieu de la matrice en 4*4
    public void InitialiseGame(){
        String[][] I = new String[16][16];
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (i>=6 && i<=9 && j>=6 && j<=9 ) {
                    I[i][j]="Images/dos_carte-1.jpg";

                }
                else{
                    I[i][j]="0";
                }
            }
        }
        this.gameMatrix=I;
    }
    //Fonction qui permet d'afficher une matrice avec le repère
    public void PrintTable(String[][] matrix){
        for (int i = 0; i <16 ; i++) {
            System.out.print("C"+i+"\t");
        }
        System.out.println();
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.print("L"+i);System.out.println();
        }
    }

    public void InitialiseRevealedMatrix(){
        for (int i=0;i< revealedMatrix.length;i++){for (int j=0;j<revealedMatrix[0].length;j++){revealedMatrix[i][j]="0";}}}

    Cards kitsune=new Cards("Images/carte_rouge-1.jpg","R");Cards oni=new Cards("Images/carte_bleue-1.jpg","B");
    Cards kappa=new Cards("images/carte_verte-1.jpg","G");Cards rokurokubi=new Cards("images/carte_violette-1.jpg","P");

    //Fonction qui permet de distribuer les cartes de manière aléatoire
    public void distributeCards(){
        //liste de couple qui contient toutes les positions où il y a les cartes au début cad au milieu de la matrice en 4*4
        int[][] Couple={{6,6},{6,7},{6,8},{6,9},{7,6},{7,7},{7,8},{7,9},{8,6},{8,7},{8,8},{8,9},{9,6},{9,7},{9,8},{9,9}};
        //liste des Familles
        String[] Families={"Kitsune","Oni","Kappa","Rokurokubi"};
        //On initialise les compteurs pour chaque famille à 0 et dès que chaque famille est à 4 on a fini la distribution
        int Kit=0;int On=0;int Kap=0;int Roku=0;
        //On parcourt la liste des positions car à chaque position on associe une carte aléatoire
        for (int i=0;i< Couple.length;i++){
            //Un boolean pour vérifier que la carte est placée
            boolean placedCard=false;
            //On utilise Random
            Random random=new Random();
            while (!placedCard){
                //On prend un index aléatoire qui choisit aléatoirement une famille
                int index=random.nextInt(4);
                String Family=Families[index];
                //Ensuite la carte de la famille choisie aléatoirement est placée ssi son compteur est strictement inférieur à 4
                //Sinon la carte n'est pas placée donc placedCard=false et on repasse dans la boucle pour tomber sur une autre carte
                //Cette méthode n'est pas longue car on a que 4 familles donc on ne risque pas de prendre du temps dans les dernières cartes à placer
                if ((Kit<4)&&(Family=="Kitsune")){
                    int line=Couple[i][0];int column=Couple[i][1];revealedMatrix[line][column]=kitsune.getFacedUp();
                    Kit++;placedCard=true;
                }
                if ((On<4)&&(Family=="Oni")){
                    int line=Couple[i][0];int column=Couple[i][1];revealedMatrix[line][column]=oni.getFacedUp();
                    On++;placedCard=true;
                }
                if ((Kap<4)&&(Family=="Kappa")){
                    int line=Couple[i][0];int column=Couple[i][1];revealedMatrix[line][column]=kappa.getFacedUp();
                    Kap++;placedCard=true;
                }
                if ((Roku<4)&&(Family=="Rokurokubi")){
                    int line=Couple[i][0];int column=Couple[i][1];revealedMatrix[line][column]=rokurokubi.getFacedUp();
                    Roku++;placedCard=true;
                }
            }
        }
    }
    //La fonction seeCards permet au joueur de voir 2 cartes
    public void seeCards(ArrayList<ArrayList> HintPositions) {
        Scanner scanner = new Scanner(System.in);
        //on initialise les lignes et colones de la première carte à 0 car sinon on pourra pas l'utiliser en sortant de la boucle
        boolean isCard1Valid=false;  int line1=0; int column1=0;
        //On vérifie dans cette boucle que la carte que veut voir le joueur est valide
        //Si elle ne l'est pas on revient dans la boucle
        while (!isCard1Valid){
            //On demande les coordonnées de la carte que veut voir le joueur
            System.out.println("Chose the first card that you want to see");System.out.println("Give first card's line and column");
            line1 = scanner.nextInt();column1 = scanner.nextInt();
            //La variable HintTest permet de vérifier si les coordonnées ne sont pas les même que celle d'une carte indice
            //Cette variable est une liste car HintPositions est une liste de couples représentant les positions
            ArrayList HintTest= new ArrayList(Arrays.asList(line1,column1));
            //La carte est validée si la position donnée est celle d'une carte et que il n'y a pas de cartes indice
            if ((revealedMatrix[line1][column1]!="0")&&(!HintPositions.contains(HintTest))){
                isCard1Valid=true;
            }
            //Cas où il n'y a pas de carte à cette position
            if (revealedMatrix[line1][column1]=="0"){
                isCard1Valid=false; System.out.println("There is no card at this position");System.out.println();
            }
            //Cas où il y a une carte à cette position
            if ((revealedMatrix[line1][column1]!="0")&&(HintPositions.contains(HintTest))){
                isCard1Valid=false;System.out.println("There is a hint Card at that position, give other positions");System.out.println();
            }

        }
        //On affiche ensuite la carte que le joueur veut voir
        System.out.println("First chosen Card " + revealedMatrix[line1][column1]);
        StdDraw.picture(column1,15-line1,revealedMatrix[line1][column1],1,1);

        //On fait la même chose pour voir la 2ème carte
        boolean isCard2Valid=false;
        int line2=0; int column2=0;

            while (!isCard2Valid){
            System.out.println();System.out.println("Chose the second card that you want to see");
            System.out.println("Give second card's line and column ");line2 = scanner.nextInt(); column2 = scanner.nextInt();
                ArrayList HintTest= new ArrayList(Arrays.asList(line2,column2));
            if ((revealedMatrix[line2][column2]!="0")&&(!HintPositions.contains(HintTest))){
                isCard2Valid=true;
            }
            if (revealedMatrix[line2][column2]=="0"){
                isCard2Valid=false; System.out.println("There is no card at this position");
            }
            if ((revealedMatrix[line2][column2]!="0")&&(HintPositions.contains(HintTest))){
                isCard2Valid=false;System.out.println("There is a hint Card at that position, give other positions");
            }
        }
        System.out.println("Second chosen Card " + revealedMatrix[line2][column2]);
        StdDraw.picture(column2,15-line2,revealedMatrix[line2][column2],1,1);
        System.out.println();
    }


    //La fonction moveCards permet de déplacer une carte
    public void moveCards(ArrayList<ArrayList> HintPositions){
        boolean isValidMove=false;
        Scanner scanner=new Scanner(System.in);
        while (!isValidMove){
            //On demande au joueur les coordonnées de la carte qu'il veut déplacer
            System.out.println("Give line and column of the card that you want to move");int CardLine=scanner.nextInt();int CardColumn=scanner.nextInt();
            //On vérifie que la carte est valide
            boolean ValidCard=false;
            //Cette boucle permet d'avoir une carte valide pour qu'elle soit déplacée
            while(ValidCard==false){
                //Comme précédemment la variable HintTest permet de vérifier si il y a une carte indice à la position donnée par le joueur
                ArrayList HintTest= new ArrayList(Arrays.asList(CardLine,CardColumn));

                //La carte est valide si c'est une carte et si il n'y a pas de carte indice dessus
                if ((gameMatrix[CardLine][CardColumn]!="0")&&(HintPositions.contains(HintTest)==false)){
                    ValidCard=true;
                }
                //Cas où il n'y a pas de carte à la position donnée
                if ((gameMatrix[CardLine][CardColumn]=="0")&&(HintPositions.contains(HintTest)==false)){
                    System.out.println("There is no card at that position, give a position where there is a card");
                    CardLine=scanner.nextInt();CardColumn=scanner.nextInt();ValidCard=false;
                }
                //Cas où il y a une carte indice
                if ((gameMatrix[CardLine][CardColumn]!="0")&&(HintPositions.contains(HintTest))){
                    System.out.println("Give other card positions (line column) to move because there is a Hint card");
                    CardLine= scanner.nextInt();CardColumn= scanner.nextInt();ValidCard=false;
                }
            }

            //Ensuite on place cette carte
            System.out.println("Give position(line and column) where you want to put this card");
            int PlacingLine=scanner.nextInt();int PlacingColumn=scanner.nextInt();
            //Si il y a déjà une carte là où le joueur veut déplacer la carte on passe dans une boucle
            if (revealedMatrix[PlacingLine][PlacingColumn]!="0"){
                //Cette boucle redemande la position du déplacement jusqu'à ce qu'il n'y ait pas de carte à la position donnée
                boolean alreadyCard=true;
                while (alreadyCard){
                    System.out.println("There is already a card at that position, give another position");
                    PlacingLine=scanner.nextInt();PlacingColumn=scanner.nextInt();
                    if (revealedMatrix[PlacingLine][PlacingColumn]=="0"){alreadyCard=false;}
                }
            }
            //Si il n'y a pas de carte où on veut déplacer la carte,
            if (Objects.equals(revealedMatrix[PlacingLine][PlacingColumn], "0")){
                // on vérifie qu'il y a bien une carte adjacente après le déplacement
                //On fait le déplacement
                revealedMatrix[PlacingLine][PlacingColumn]=revealedMatrix[CardLine][CardColumn];
                revealedMatrix[CardLine][CardColumn]="0";
                //Vérifie qu'il y a carte adjacentet
                gameMatrix[PlacingLine][PlacingColumn]=gameMatrix[CardLine][CardColumn];gameMatrix[CardLine][CardColumn]="0";

                //On vérifie qu'il y a soit une carte à gauche, à droite, au dessus, ou en dessous de manière matriciellle
                if ((revealedMatrix[PlacingLine][PlacingColumn+1]!="0")|(revealedMatrix[PlacingLine][PlacingColumn-1]!="0")|(revealedMatrix[PlacingLine+1][PlacingColumn]!="0")|(revealedMatrix[PlacingLine-1][PlacingColumn]!="0")){
                    isValidMove=true;
                }
                //Si il n'y a pas de carte adjacente on annule le déplacement (en remettant les cartes comme avant)
                //Et on revient dans la boucle pour choisir un autre déplacement qui est valide
                else{
                    System.out.println("No adjacent Card next to the moved card after the move");
                    revealedMatrix[CardLine][CardColumn]=revealedMatrix[PlacingLine][PlacingColumn];
                    revealedMatrix[PlacingLine][PlacingColumn]="0";
                    gameMatrix[CardLine][CardColumn]=gameMatrix[PlacingLine][PlacingColumn];
                    gameMatrix[PlacingLine][PlacingColumn]="0";
                }
            }
        }
    }

}
