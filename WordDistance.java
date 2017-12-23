import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Marc-André Piché|783722 | 2017-12-17.
 * Anthony Lemieux|20077762
 * <p>
 * Calcul la distance d'édition entre deux mots en considérant leur similarité
 * implantation de l'algorithme de Levenshtein
 */


public class WordDistance {
    private int minDistance = Integer.MAX_VALUE;
    private int distanceCounter = 0;
    private Random dice = new Random();

    final private ArrayList<Result> results = new ArrayList<Result>();

    //méthode inefficace du au type de dictionnaire Hashset s'adonne à être moins bon ici, une Trie aurait été parfaite
    public String[] correction(String inconnue, Dictionnaire dictionnaire) {
        for (String item : dictionnaire.setDict) {
            //insert le mot comparé dans la liste
            Result inserted = new Result(compare(inconnue, item, Math.max(inconnue.length(), item.length())), item);
            if (inserted.distance < minDistance) {
                insert(inserted);
            }
        }
        //renvoie les 5 meilleurs mots
        String[] returned = new String[5];
        for (int j = 0; j < returned.length; j++) {
            returned[j] = results.get(j).word;
        }
        return returned;
    }


    private void insert(Result mot) {
        distanceCounter++;
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).distance < mot.distance) continue;
            if (results.get(i).distance == mot.distance) {
                if(dice.nextBoolean()){
                    results.add(i, mot);
                    if (distanceCounter > 4) {
                        minDistance = results.get(4).distance;
                    }
                    return;
                } else continue;
            }
            results.set(i, mot);
            return;
        }
        results.add(mot);
    }

private class Result {
    int distance;
    String word;

    Result(int distance, String word) {
        this.distance = distance;
        this.word = word;
    }

}

    private static int compare(String left, String right, int limite) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (limite < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }

        int n = left.length();
        int m = right.length();

        // si une String est vide
        if (n == 0) {
            return m <= limite ? m : -1;
        } else if (m == 0) {
            return n <= limite ? n : -1;
        }

        int[] p = new int[n + 1]; // tableau des couts à jour
        int[] d = new int[n + 1]; // tableau des couts courrant
        int[] tempD; //tableau pour swapper les deux

        // remplie le premier tableau
        int boundary = Math.min(n, limite) + 1;
        for (int i = 0; i < boundary; i++) {
            p[i] = i;
        }
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        //itérations
        for (int j = 1; j <= m; j++) {
            char rightJ = right.charAt(j - 1); // jth character of right
            d[0] = j;

            // calcule les indices
            int min = Math.max(1, j - limite);
            int max = j > Integer.MAX_VALUE - limite ? n : Math.min(
                    n, j + limite);

            if (min > max) {
                return -1;
            }

            if (min > 1) {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // passe [min, max] en diagonale
            for (int i = min; i <= max; i++) {
                if (left.charAt(i - 1) == rightJ) {
                    d[i] = p[i - 1];
                } else {
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            //swap les distances
            tempD = p;
            p = d;
            d = tempD;
        }

        if (p[n] <= limite) {
            return p[n];
        }
        return -1;
    }
}


