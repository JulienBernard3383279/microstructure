package gid.micro;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class OurOLSMultipleLinearRegression extends OLSMultipleLinearRegression {
	public double[] ourCalculateBeta() {
		RealVector result = calculateBeta();
		double[] array = new double[result.getDimension()];
		for (int i = 0 ; i < result.getDimension(); ++i) {
			array[i] = result.getEntry(i);
		}
		return array;
	}
	
	public double[][] ourCalculateBetaVariance() {
		RealMatrix result = calculateBetaVariance();
		return result.getData();
	}
}
