import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static class PortfoliosYields {
        private List<List<DirtyStock>> portfolios;
        private List<Double> yields;

        public List<DirtyStock> getPortfolio(int i) {
            return portfolios.get(i);
        }

        public void setPortfolios(List<List<DirtyStock>> portfolios) {
            this.portfolios = portfolios;
        }

        public double getYield(int i) {
            return yields.get(i);
        }

        public void setYields(List<Double> yields) {
            this.yields = yields;
        }
    }

    /**
     * @summary Renvoie une liste de 10 listes contenant chacune 10 Ã©lÃ©ments, la premiÃ¨re est P1, la 10Ã¨me est P10,
     * selon la tri par rendement maximal durant la pÃ©riode fournie via debut et nombreMois
     *
     * @param stocks Les donnÃ©es
     * @param debut La date de dÃ©but
     * @param nombreMois Le nombre de mois pris en compte
     * @return
     */
    public static PortfoliosYields extractP1toP10(List<DirtyStock> stocks, long debut, long nombreMois) {
        // Extraire pour chaque titre les yields et faire la moyenne
        Map<DirtyStock,Double> stockToAverageYield = new HashMap<DirtyStock,Double>();
        List<DirtyStock> titresClone = new LinkedList<DirtyStock>();

        for (DirtyStock stock : stocks) {
            stockToAverageYield.put(stock, stock.averageYield(debut, nombreMois)); //mensuel->mensuel
            titresClone.add(stock);
        }

        // Tri par ordre croissant de rentabilité
        Collections.sort(titresClone, Comparator.comparing(stockToAverageYield::get));
		/*for (DirtyStock titre : titresClone) {
			if (nombreMois==1) System.out.println(titre.getId() + " : "+stockToAverageYield.get(titre));
		}*/
        // En faire des paquets de 10
        List<List<DirtyStock>> toBeReturned = new LinkedList<List<DirtyStock>>();
        for (int i = 0; i<10; i++) {
            toBeReturned.add(new LinkedList<DirtyStock>());
            for (int j = 0; j<10; j++) {
                toBeReturned.get(i).add(titresClone.get(10*i+j));
            }
        }
        //toBeReturned[0] = pires actifs
        //toBeReturned[9] = meilleurs actifs

        // Calculer le rendement pour chaque portefeuille
        List<Double> yields = new LinkedList<Double>();
        double d;
        for (int i = 0; i<10; i++) {
            d = 0.0;
            for (int j = 0; j<10; j++) {
                d += stockToAverageYield.get(toBeReturned.get(i).get(j));
            }
            d/=10.0;
            yields.add(d);
        }
        PortfoliosYields per = new PortfoliosYields();
        per.setPortfolios(toBeReturned);
        per.setYields(yields);

        return per;
    }

    /*
     * Pour une fenêtre temporelle donnée, renvoie le rendement mensuel moyen du portefeuille
     */
    public static double portfolioYield(List<DirtyStock> portfolio, long debut, long monthNumber) {
        Double temp = new Double(0);
        double moyenneGeo = 1;
        for (int i = 0; i < monthNumber; i++) {
            double sousMoyenneAri = 0.0;
            for (DirtyStock stock: portfolio) {
                temp = stock.getYields().get(debut+i);
                sousMoyenneAri += temp;
            }
            sousMoyenneAri /= portfolio.size();
            moyenneGeo *= (1+sousMoyenneAri);
        }
        moyenneGeo = Math.pow(moyenneGeo, 1.0/monthNumber);
        return moyenneGeo;
    }

    public static double portfolioBeta(List<DirtyStock> portfolio, long debut, long monthNumber) {
        double somme = 0;
        for (int i=0; i<10; i++) {
            somme += portfolio.get(i).averageBeta(debut, monthNumber);
        }
        return somme/10.0;
    }

    public static String titresChoisis(List<DirtyStock> portefeuille) {
        String str = "";
        for (DirtyStock titre : portefeuille) {
            str += titre.getId();
            str+=" ";
        }
        return str;
    }

    /*
     * Renvoie de 0.998 à 1.0 selon la réduction de valeur du portefeuille dû aux coûts de transaction.
     */
    public static double transactionCosts(List<DirtyStock> list1, List<DirtyStock> list2) {
        Set<Integer> set1 = list1.stream().map(DirtyStock::getId).collect(Collectors.toSet());
        Set<Integer> set2 = list2.stream().map(DirtyStock::getId).collect(Collectors.toSet());
        Set<Integer> result = new HashSet<Integer>();
        result.addAll(set1);
        result.retainAll(set2);
        return 0.998 + ( (0.0002)*result.size() );
        //List<DirtyStock> list2stream = list2.stream().sorted(Comparator.comparing(DirtyStock::getId)).collect(Collectors.toList());
    }
}