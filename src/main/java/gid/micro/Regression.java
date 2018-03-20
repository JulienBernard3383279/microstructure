package gid.micro;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class Regression {
	private OurOLSMultipleLinearRegression regression;

	public Regression() {
		this.regression = new OurOLSMultipleLinearRegression();
	}

	public void loadSampleData(List<Data> dataList, List<DataType> dataTypes) {
	    // Memory allocation
		double[] y = new double[dataList.size()];
		double[][] x = new double[dataList.size()][dataTypes.size()];

		// Fill data matrix samples
		for (int i = 0; i < dataList.size(); ++i) {
			Data data = dataList.get(i);
			y[i] = data.get(DataType.return_rf);

			for (int j = 0; j < dataTypes.size(); ++j) {
				DataType dataType = dataTypes.get(j);
				x[i][j] = data.get(dataType);
			}
		}

		// Create new sample data
		this.regression.newSampleData(y, x);
	}

	public double[] computeRegressionCoefficients() {
		// Multiple Linear Regression
        double[] coefficients = this.regression.ourCalculateBeta();
	    return coefficients;
	}
	
	private boolean hasData(List<DataType> dataTypes, Data data) {
		for (DataType dataType : dataTypes) {
			if (data.get(dataType) == null) {
			    return false;
            }
		}
		return true;
	}

	public Map<Long, List<Data>> filter(List<Data> dataList, final List<DataType> dataTypes) {
		return dataList.stream()
				.filter(x -> hasData(dataTypes, x))
				.collect(Collectors.groupingBy(Data::getDate));			
	}
}
