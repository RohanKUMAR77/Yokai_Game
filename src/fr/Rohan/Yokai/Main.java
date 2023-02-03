package fr.Rohan.Yokai;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Graphics graphics =new Graphics();
        StdDraw.setCanvasSize(600,600);
        StdDraw.setXscale(0,16); StdDraw.setYscale(0,16);

        System.out.println("                   THE YOKAI GAME HAS STARTED");System.out.println();
        System.out.println("How many players ?");
        Scanner scanner= new Scanner(System.in);
        int nb_players=scanner.nextInt();
        Player players=new Player(new ArrayList());
        players.createPlayers(nb_players);//On utilise la fonction player de la classe Player qui enregistre les noms des joueurs dans une liste
        String[][] matrix = new String[16][16];
        //On créé un objet de la classe board pour utiliser ses méthodes et attributs
        Board startGame=new Board(matrix);
        //La méthode InitialiseGame permet de disposer les cartes face cachées en 4*4 au milieu de la matrice GameMatrix
        startGame.InitialiseGame();
        //La méthode InitialiseRevealedMatrix initialise la matrice qui contient les cartes retournées appelée revealedMatrix
        startGame.InitialiseRevealedMatrix();
        //La méthode distributeCards permet de distribuer les cartes en 4*4 au milieu de revealedMatrix
        //Cette méthode prend bien en compte qu'il faut 4 cartes de chaque famille
        startGame.distributeCards();
        boolean EndGame=false;
        int j=0;//compteur pour faire varier le tour des joueurs
        //Liste qui contient les cartes indices qui ont été tirées
        ArrayList<String> PickedHints=new ArrayList();
        //On créée un objet de la classe HintCards
        HintCards hint=new HintCards();
        hint.initialiseHint();
        hint.CreateHintPacket(); //Le packet de cartes d'indice est réalisé de manière aléatoire tout en respectant les règles du jeu
        while (EndGame==false){
            System.out.println("Are Spirits appeased (1/0)?");
            int answer=scanner.nextInt();
            if (answer==1){
                EndGame=true;
            }
            if (answer==0){
                System.out.println();
                //On affiche les positions des cartes indices et leur couleur si elles ont été positionnées
                if (hint.getHintPositions().size()!=0){
                    System.out.println("Your Hint Positions");System.out.println(hint.getHintMap());System.out.println();
                }
                //On affiche qu'elles n'ont pas encore été positionnées sinon
                if (hint.getHintPositions().size()==0){
                    System.out.println("No hint Card placed yet");System.out.println();
                }
                //la variable n permet de faire varier le tour des joueurs, ensuite on affiche la matrice de jeu
                int n=players.getNames().size();startGame.PrintTable(startGame.getGameMatrix());
                //On affiche dans la fenêtre
                StdDraw.clear();
                graphics.establish(startGame.getGameMatrix(), hint.getPickedHints(), hint.getHintMatrix());
                System.out.println();
                if (j< n){
                    //C'est le tour du joueur d'indice j
                    players.turn(j);//La méthode turn permet de dire que c'est le tour du joueur j
                    //Pour commencer on affiche les cartes indices qui ont été tirées et pas positionnnées
                    if (hint.getPickedHints().size()!=0){
                        System.out.println(hint.getPickedHints());}

                    //On demande au joueur de regarder les cartes et de les déplacer à l'aide des méthodes de board
                    // Ces méthodes prennent en argument les positions des cartes indices pour pouvoir utiliser cette variable
                    startGame.seeCards(hint.getHintPositions());Thread.sleep(3000);
                    //On remet les cartes en face cachée dans la fenêtre de jeu
                    graphics.printBoard(startGame.getGameMatrix(), hint.getHintMatrix());
                    startGame.moveCards(hint.getHintPositions());j++;
                    //Le joueur a fini de regarder les cartes et de déplacer une carte
                    StdDraw.clear();graphics.establish(startGame.getGameMatrix(), hint.getPickedHints(), hint.getHintMatrix());
                    //Il doit ensuite tirer ou placer une carte indice
                    //Variable qui permet de dire si la carte indice a été jouée, afin d'éviter de rentrer dans tous les if
                    boolean HintPlayed=false;
                    // On a différents cas pour utiliser les cartes indices :

                    //Cas où on est obliger de tirer une carte indice
                    if ((hint.getHintPacket().size()!=0)&&(hint.getPickedHints().size()==0)){
                        hint.pickHintCard();graphics.ShowHints(hint.getPickedHints());HintPlayed=true;
                    }
                    //Cas où on peut soit placer ou tirer une carte indice
                    if ((hint.getHintPacket().size()!=0)&&(hint.getPickedHints().size()!=0)&&(!HintPlayed)){
                        System.out.println("Place or pick a Hint Card (1/0)");int HintAns= scanner.nextInt();
                        //pour tirer une carte indice
                        if (HintAns==0){
                            hint.pickHintCard();HintPlayed=true;graphics.ShowHints(hint.getPickedHints());
                        }
                        //pour placer une carte indice
                        if (HintAns==1){
                            hint.PlaceHintCard();StdDraw.clear();graphics.establish(startGame.getGameMatrix(), hint.getPickedHints(), hint.getHintMatrix());graphics.ShowHints(hint.getPickedHints());
                            HintPlayed=true;
                        }
                    }
                    //Cas où on est obligé de placer une carte indice
                    if((hint.getHintPacket().size()==0)&&(hint.getPickedHints().size()!=0)&&(!HintPlayed)){
                        hint.PlaceHintCard();StdDraw.clear();graphics.establish(startGame.getGameMatrix(),hint.getPickedHints(), hint.getHintMatrix());graphics.ShowHints(hint.getPickedHints());
                        HintPlayed=true;
                    }
                    //Cas où tous les indices ont été tirés et placés le jeu est fini dans ce cas là
                    if ((hint.getHintPacket().size()==0)&&(hint.getPickedHints().size()==0)&&(!HintPlayed)){EndGame=true;}
                    //Si j=n donc tous les joueurs ont joués, c'est esuite le tour du premier joueur on remet j à 0
                    if (j==n) {j = 0;}
                }
            }
        }
        //Quand le jeu est fini on affiche revealedMatrix (les cartes retournées), pour voir le résultat de leur partie
        System.out.println("Here are the results of your game !");startGame.PrintTable(startGame.getRevealedMatrix());
        StdDraw.clear();graphics.FinalPrintBoard(startGame.getRevealedMatrix(), hint.getPickedHints());
   }

}
