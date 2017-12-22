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

        private HashSet<String> setDict = new HashSet<>();
        private char[] punctuation = new char[]{' ','.',',','!','?','(',')','{','}','[',']','\n','+','&','@','<','>',';',':','\'','\"','\\','/'};

        Dictionnaire(File dictionnaire) throws IOException {
            sort(punctuation);
            BufferedReader input = new BufferedReader(new FileReader(dictionnaire));
            String line;
            while ((line = input.readLine()) != null) {
                setDict.add(line);
            }
            input.close();
        }

        //indique si un character est une punctuation -* un appelle pour chaque lettre du texte
        public boolean isPunctuation(char c){
            return (binarySearch(punctuation, c)>-1);
        }

        public Boolean querry(String word){
            return setDict.contains(word);
        }

        public boolean isEmpty(){
            return setDict.isEmpty();
        }

}
