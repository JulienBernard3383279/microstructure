import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Parser {

    public static List<Stock> parseStocks(String dataCsv) {
        List<Stock> stocks = new ArrayList<>();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(dataCsv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    @SuppressWarnings("resource")
    public static List<DirtyStock> parseDirtyStocks(String dataCsv, String stockNumberCsv) {
        Map<Integer, DirtyStock> titres = new Hashtable<>();

        BufferedReader br = null;
        String line;
        String csvSplit = ";";

        try {
            // STOCK NUMBERS
            List<Integer> stockNumbers = new ArrayList<>();

            br = new BufferedReader(new FileReader(stockNumberCsv));

            // skip first line
            br.readLine();

            while ((line = br.readLine()) != null) {
                stockNumbers.add(Integer.valueOf(line));
            }

            // STOCKS
            for (Integer id: stockNumbers) {
                titres.put(id, new DirtyStock(id));
            }

            br = new BufferedReader(new FileReader(dataCsv));

            // skip first line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] stock = line.split(csvSplit);
                int stockNumber = Integer.valueOf(stock[0]);

                if (stockNumbers.contains(stockNumber)) {
                    int year = Integer.valueOf(stock[1]);
                    int month = Integer.valueOf(stock[2]);
                    double return_rf = Double.valueOf(stock[3])+Double.valueOf(stock[28]);
                    double beta = Double.valueOf(stock[4]);
                    DirtyStock titre = titres.get(stockNumber);
                    titre.addYield(year, month, return_rf);
                    titre.addBeta(year, month, beta);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ArrayList<>(titres.values());
    }

}