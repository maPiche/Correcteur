import java.util.Arrays;

/**
 * Created by Marc-André Piché|783722 on 2017-12-22.
 *
 * Calcul la distance d'édition entre deux mots en considérant leur similarité
 * implantation de l'algorithme de Jaro-Winkler
 */


public class WordDistance {

    public Double apply(String left, String right)throws IllegalArgumentException   {
            final double defaultScalingFactor = 0.1;

            if (left == null || right == null) {
                throw new IllegalArgumentException("Strings must not be null");
            }

            final int[] mtp = matches(left, right);
            final double m = mtp[0];
            if (m == 0) {
                return 0D;
            }
            final double j = ((m / left.length() + m / right.length() + (m - mtp[1]) / m)) / 3;
            return j < 0.7D ? j : j + Math.min(defaultScalingFactor, 1D / mtp[3]) * mtp[2] * (1D - j);

        }

        protected static int[] matches(String first, String second) {
            CharSequence max, min;
            if (first.length() > second.length()) {
                max = first;
                min = second;
            } else {
                max = second;
                min = first;
            }
            final int range = Math.max(max.length() / 2 - 1, 0);
            final int[] matchIndexes = new int[min.length()];
            Arrays.fill(matchIndexes, -1);
            final boolean[] matchFlags = new boolean[max.length()];
            int matches = 0;
            for (int mi = 0; mi < min.length(); mi++) {
                final char c1 = min.charAt(mi);
                for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                    if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                        matchIndexes[mi] = xi;
                        matchFlags[xi] = true;
                        matches++;
                        break;
                    }
                }
            }
            final char[] ms1 = new char[matches];
            final char[] ms2 = new char[matches];
            for (int i = 0, si = 0; i < min.length(); i++) {
                if (matchIndexes[i] != -1) {
                    ms1[si] = min.charAt(i);
                    si++;
                }
            }
            for (int i = 0, si = 0; i < max.length(); i++) {
                if (matchFlags[i]) {
                    ms2[si] = max.charAt(i);
                    si++;
                }
            }
            int transpositions = 0;
            for (int mi = 0; mi < ms1.length; mi++) {
                if (ms1[mi] != ms2[mi]) {
                    transpositions++;
                }
            }
            int prefix = 0;
            for (int mi = 0; mi < min.length(); mi++) {
                if (first.charAt(mi) == second.charAt(mi)) {
                    prefix++;
                } else {
                    break;
                }
            }
            return new int[]{matches, transpositions / 2, prefix, max.length()};
        }
    }


}
