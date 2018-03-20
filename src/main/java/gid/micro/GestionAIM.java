package gid.micro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;

public class GestionAIM {
	
	public static List<VecteurRenta> managePort(long debut, long fin, Map<Long,List<Data>> dataDict) {
		List<VecteurRenta> port = new ArrayList<VecteurRenta>();
		
		long curr = debut;
		List<Data> selectedAssets;
		VecteurRenta currVect = new VecteurRenta(1,1,1);
		int nbAssets;
		double Rp;
		while (curr <= fin) {
			dataDict.get(curr).sort(new DataAIMComparator());
			nbAssets = dataDict.get(curr).size();
			selectedAssets = dataDict.get(curr).subList(nbAssets*9/10, nbAssets);
			nbAssets = selectedAssets.size();
			Rp = 0;
			for (Data d : selectedAssets) {
				Rp += d.get(DataType.return_rf);
			}
			Rp/=nbAssets;
			
			currVect = new VecteurRenta(curr, Rp, dataDict.get(curr).get(0).get(DataType.marketReturn)-dataDict.get(curr).get(0).get(DataType.riskFreeReturn));
			port.add(currVect);
			curr+=1;
		}
				
		OurOLSMultipleLinearRegression regression = new OurOLSMultipleLinearRegression();
		double[] y = new double[port.size()];
		double[][] x = new double[port.size()][1];
		int j = 0;
		for (VecteurRenta v : port) {
			y[j] = v.Rp;
			x[j][0] = v.Rm;
			++j;
		}
		regression.newSampleData(y, x);
		double[] beta = regression.ourCalculateBeta();
		double alphaJ = beta[0];
		double betaM = beta[1];
		
		int residualdf = regression.estimateResiduals().length - beta.length;
		for (int i = 0; i < beta.length; i++) {
			double tstat = beta[i] / regression.estimateRegressionParametersStandardErrors()[i];

			double pvalue = new TDistribution(residualdf).cumulativeProbability(-FastMath.abs(tstat)) * 2;

			System.out.println("coef " + beta[i] + " p-value(" + i + ") : " + pvalue);
		}

		return port;
	}
}
