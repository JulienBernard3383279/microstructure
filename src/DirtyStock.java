import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public class DirtyStock {

    private int id;
    private Map<Long, Double> yields;
    private Map<Long, Double> beta;

    public DirtyStock(int id) {
        this.id = id;
        yields = new Hashtable<>();
        beta = new Hashtable<>();
    }

    public void addYield(long year, long month, double rendement) {
        Long date = year * 12 + month;
        this.yields.put(date, rendement);
    }

    public void addBeta(long year, long month, double beta) {
        Long date = year * 12 + month;
        this.beta.put(date, beta);
    }

    public int size() {
        return yields.size();
    }

    public int getId() {
        return id;
    }

    public Map<Long, Double> getYields() {
        return yields;
    }

    /*
     * Crée la liste de, pour chaque mois, le rendement mensuel associé
     */
    public List<Double> listYields(long begin, long numberMonths) {
        List<Double> toBeReturned = new LinkedList<Double>();
        long date = begin;

        for (int i = 0; i < numberMonths; ++i) {
            toBeReturned.add(yields.get(date));
            date++;
        }

        return toBeReturned;
    }

    /*
     * Rendement en entrée : mensuels
     * Rendement en sortie : mensuels
     */
    public double averageYield(long begin, long numberMonths) {
        double moyenneGeo=1.0;
        long date = begin;

        for (int i=0; i<numberMonths; i++) {
            moyenneGeo *= (1.0+yields.get(date));
            date++;
        }

        return Math.pow(moyenneGeo, (1.0 / numberMonths)) - 1.0;
    }

    public double averageBeta(long begin, long numberMonths) {
        double somme=0.0;
        long date = begin;

        for (int i=0; i<numberMonths; i++) {
            somme += beta.get(date);
            date++;
        }

        return somme/numberMonths;
    }

    public double averageBetaWindow(long begin, long end) {
        return averageBeta(begin, end-begin);
    }

    @Override
    public String toString() {
        return "DirtyStock { "
                + "id=" + id
                + ", yields=" + yields
                + "}";
    }
}