import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Marc-André Piché|783722 - 2017-12-19.
 */

class Dictionnaire {

        private HashSet<String> setDict = new HashSet<>();

        Dictionnaire(File dictionnaire) throws IOException{

            BufferedReader input = new BufferedReader(new FileReader(dictionnaire));

                String line = null;
                while ((line = input.readLine())!=null){
                    setDict.add(line);
                }
        }

        public Boolean querry
}
