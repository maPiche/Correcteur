import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

/**
 * Created by Marc-André Piché|783722 - 2017-12-19.
 * Creer un dictionnaire a partir d'un fichier text : une ligne = un mot
 * querry revoie true/false si le mot est dans le dictionnaire
 */

class Dictionnaire {

        private HashSet<String> setDict = new HashSet<>(); //Declare la structure d'un dictionnaire
        private char[] punctuation = new char[]{' ','.',',','!','?','(',')','{','}','[',']','\n','+','&','@','<','>',';',':','\'','\"','\\','/'}; //Ponctuation possible
        																																		//a ne pas prendre en compte
        Dictionnaire(File dictionnaire) throws IOException { //Creation du dictionnaire a partir dun fichier txt
            sort(punctuation);
            BufferedReader input = new BufferedReader(new FileReader(dictionnaire));
            String line;
            while ((line = input.readLine()) != null) { //Lecture du fichier ligne par ligne
                setDict.add(line);
            }
            input.close();
        }

        //Indique si un charactere est une punctuation -* un appelle pour chaque lettre du texte
        public boolean isPunctuation(char c){
            return (binarySearch(punctuation, c)>-1);
        }
        //Recherche un certain mot dans le dictionnaire
        public Boolean querry(String word){
            return setDict.contains(word);
        }
        //Verifie si le dictionnaire est vide ou non
        public boolean isEmpty(){
            return setDict.isEmpty();
        }

}
