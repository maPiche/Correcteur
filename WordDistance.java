import java.util.Arrays;

/**
 * Created by Marc-André Piché|783722 on 2017-12-22.
 * <p>
 * Calcul la distance d'édition entre deux mots en considérant leur similarité
 * implantation de l'algorithme de Levenshtein
 */


public class WordDistance {

    public static int compare(String left, String right, int limite) {
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


