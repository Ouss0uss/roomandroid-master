package calculatrice;

public class Operations {
    public Long addition(Long a, Long b) {
        return a + b;
    }

    public Character lireSymbole() {
        return '+';
    }

    public static double soustraire(final double... pNombres) {
        if (pNombres.length < 2) {
            throw new IllegalArgumentException("Il faut au moins deux nombres en entrée");
        }
        double lRetour = pNombres[0];
        for (int i = 1; i < pNombres.length; i++) {
            lRetour -= pNombres[i];
        }
        return lRetour;
    }


}
