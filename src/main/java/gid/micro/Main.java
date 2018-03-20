package gid.micro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Data> dataList = Parser.parseData("./data/data.csv");
        System.out.println(dataList.get(0));

        //// Correlations
        Correlation corr = new Correlation(dataList);
        System.out.println(corr.toLatex());

        //// Regression
        Regression regression = new Regression();

        // Filer data for regression
        Map<Long, List<Data>> dataListPerDate = new HashMap<>();

        List<DataType> dataTypes = new ArrayList<>();
        dataTypes.add(DataType.beta);
        dataTypes.add(DataType.aim);

        dataListPerDate = regression.filter(dataList, dataTypes);

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
        }
    }
}