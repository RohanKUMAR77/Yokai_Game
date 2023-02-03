package fr.Rohan.Yokai;
import java.util.*;


public class HintCards {
    public ArrayList<String> getHintPacket() {
        return HintPacket;
    }

    ArrayList<String> HintPacket=new ArrayList<>();

    public String[][] getHintMatrix() {
        return HintMatrix;
    }

    public String[][] HintMatrix=new String[16][16];
    public void initialiseHint(){for (int i = 0; i <16 ; i++) {for (int j = 0; j < 16; j++) {HintMatrix[i][j]="0";}}}

    public ArrayList<String> getPickedHints() {
        return PickedHints;
    }

    public ArrayList<ArrayList> getHintPositions() {
        return HintPositions;
    }

    public HashMap<String, ArrayList> getHintMap() {
        return hintMap;
    }

    HashMap<String,ArrayList> hintMap=new HashMap<>();


    //Liste qui contient les positions des cartes indices sous forme de couples
    ArrayList<ArrayList> HintPositions=new ArrayList();
    //Liste des Cartes indices qui ont été tirées
    ArrayList<String> PickedHints=new ArrayList<>();
    //Liste des cartes indices 1/2/3 couleur(s) :
    ArrayList<String> oneColor=new ArrayList<>(Arrays.asList("Images/indice_bleu-1.jpg","Images/indice_violet-1.jpg","Images/indice_verte-1.jpg","Images/indice_rouge-1.jpg"));
    ArrayList<String> twoColor =new ArrayList<>(Arrays.asList("Images/indice_bleu_rouge-1.jpg", "Images/indice_bleu_violet-1.jpg", "Images/indice_rouge_violet-1.jpg", "Images/indice_vert_bleu-1.jpg", "Images/indice_vert_rouge-1.jpg", "Images/indice_vert_violet-1.jpg"));
    ArrayList<String> threeColor =new ArrayList<>(Arrays.asList("Images/indice_bleu_vert_rouge-1.jpg", "Images/indice_bleu_vert_violet-1.jpg", "Images/indice_bleu_violet_rouge-1.jpg", "Images/indice_vert_violet_rouge-1.jpg"));

    //fonction qui permet de créer un packet d'indice de manière aléatoire adapté pour 2 joueurs
    public void CreateHintPacket(){
        Random random=new Random();
        for (int i = 0; i <2 ; i++) {
            int size=oneColor.size();
            int Draw= random.nextInt(size);
            HintPacket.add(oneColor.get(Draw));
            this.oneColor.remove(Draw);
        }
        for (int i = 0; i < 3; i++) {
            int size=twoColor.size();
            int Draw= random.nextInt(size);
            HintPacket.add(twoColor.get(Draw));
            this.twoColor.remove(Draw);
        }
        for (int i = 0; i < 2; i++) {
            int size=threeColor.size();
            int Draw= random.nextInt(size);
            HintPacket.add(threeColor.get(Draw));
            this.threeColor.remove(Draw);
        }
    }
    //Fonction qui permet de tirer une carte indice
    public void pickHintCard(){
        System.out.println("You picked a Hint Card");
        System.out.println("You picked "+ HintPacket.get(0));
        PickedHints.add(HintPacket.get(0)); HintPacket.remove(0);
        System.out.println();
    }
    //fonction qui permet de placer un indice
    public void PlaceHintCard(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Give index of the Hint Card that you want to place");
        int index=scanner.nextInt();
        System.out.println("Give position line and column where you want to place your Hint Card");
        int HintLine= scanner.nextInt();int HintColumn= scanner.nextInt();
        HintMatrix[HintLine][HintColumn]=PickedHints.get(index);
        //On ajoute dans la hintMap lacarte indice avec ses coordonnées associées
        hintMap.put(PickedHints.get(index),new ArrayList(Arrays.asList(HintLine,HintColumn)));
        StdDraw.picture(HintColumn,15-HintLine,PickedHints.get(index),1,1);
        PickedHints.remove(index);
        HintPositions.add(new ArrayList(Arrays.asList(HintLine,HintColumn)));

    }
}
