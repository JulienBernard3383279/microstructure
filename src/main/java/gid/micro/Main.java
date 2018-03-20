package gid.micro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Data> dataList = Parser.parseData("./data/data.csv");
        System.out.println(dataList.get(0));
        System.out.println(dataList.get(1));

        //// Correlations
        Correlation corr = new Correlation(dataList);
        
        Map<Long,List<Data>> dataDict = dataList.stream().collect(Collectors.groupingBy(Data::getDate));
        GestionAIM.managePort(1990*12, 2000*12, dataDict);

//        System.out.println(corr.toLatex());

        //// Regression
        Regression regression = new Regression();

        // Filer data for regression
        Map<Long, List<Data>> dataListPerDate;

        List<DataType> dataTypes = new ArrayList<>();

        dataTypes.add(DataType.beta);
        dataTypes.add(DataType.aim);
        dataTypes.add(DataType.pin);

        dataTypes.add(DataType.return_rf);
        dataListPerDate = regression.filter(dataList, dataTypes);
        dataTypes.remove(DataType.return_rf);

        List<Double> leFutur =
                dataListPerDate
                        .entrySet() // -> Liste de ( date (Long) , liste de données (List<Data) )
                        .stream() // magie
                        .map(e -> { // obtension des coefficients de régression (+ intercept en index 0) sous forme de liste
                            regression.loadSampleData(e.getValue(),dataTypes);
                            double[] coefficients = regression.computeRegressionCoefficients();
                            List<Double> toBeReturned = new LinkedList<Double>();
                            for (int i = 0; i < dataTypes.size() + 1; ++i) {
                                toBeReturned.add(coefficients[i]);
                            }
                            return toBeReturned;
                        }) // additions termes à termes des listes de coefficients
                        .reduce(
                                (i,j) -> {
                                    for (int k = 0; k < i.size(); ++k) {
                                        i.set(k, i.get(k)+j.get(k));
                                    }
                                    return i;
                                })
                        .get() // OptionalList -> List
                        .stream() // magie
                        .map(x -> x / dataListPerDate.size()) // transformation en moyenne
                        .collect(Collectors.toList());

        for (int i = 0; i < leFutur.size(); ++i) {
            System.out.println(leFutur.get(i));
        }
        
        /*if (i.size() == 0) {
        for (int k = 0; k < j.size(); ++k) {
        	i.add(new Double(0));
        }*/
        
        /*dataListPerDate.entrySet()
        	.stream()
        	.collect(Collectors.toMap(Map.Entry::getKey,
        			e -> e.getValue()
        				  .stream()
        			      .mapToDouble( a -> {
        					regression.loadSampleData(dataListPerDate.get(x), dataTypes);
        					return 1;
        			} )
        			.average()));
        
        
        long date = 1990 * 12 + 1;
        while (date <= 1990 * 12 + 12) {
            regression.loadSampleData(dataListPerDate.get(date), dataTypes);

            double[] coefficients = regression.computeRegressionCoefficients();
            System.out.print(date + ": ");
            for (int i = 0; i < 3; ++i) {
                System.out.print(coefficients[i] + " ");
            }
            System.out.println();

            ++date;
        }*/
    }
}