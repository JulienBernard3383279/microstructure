package gid.micro;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class Correlation {
	double[][] corr;
	double[][] pvalues;
	DataType[] variables = { DataType.return_return, DataType.beta, DataType.aim, DataType.log_market_cap,
			DataType.log_btm, DataType.pin, DataType.fsrv, DataType.turnover, DataType.illiq_amihud };

	public Correlation(List<Data> dataList) {
		int nbVariables = variables.length;
		corr = new double[nbVariables][nbVariables];
		pvalues = new double[nbVariables][nbVariables];
		double[][] simpleSum = new double[nbVariables][nbVariables];
		double[][] squareSum = new double[nbVariables][nbVariables];
		double[][] productSum = new double[nbVariables][nbVariables];
		int[][] nbData = new int[nbVariables][nbVariables];

		DataType xData;
		DataType yData;
		Double xValue;
		Double yValue;

		HashMap<DataType, HashMap<DataType, ArrayList<Vector<Double>>>> mats = new HashMap<DataType, HashMap<DataType, ArrayList<Vector<Double>>>>();
		for (int i = 0; i < nbVariables; ++i) {
			xData = variables[i];
			mats.put(xData, new HashMap<DataType, ArrayList<Vector<Double>>>());
			for (int j = i; j < nbVariables; ++j) {
				yData = variables[j];
				mats.get(xData).put(yData, new ArrayList<Vector<Double>>());
			}
		}

		for (Data row : dataList) {
			for (int i = 0; i < nbVariables; ++i) {
				xData = variables[i];
				xValue = row.get(xData);
				if (xValue != null) {
					for (int j = i; j < nbVariables; ++j) {
						yData = variables[j];
						yValue = row.get(yData);
						if (yValue != null) {
							nbData[i][j] += 1;
							nbData[j][i] += 1;

							simpleSum[i][j] += xValue;
							squareSum[i][j] += xValue * xValue;
							simpleSum[j][i] += yValue;
							squareSum[j][i] += yValue * yValue;

							productSum[i][j] += xValue * yValue;
							productSum[j][i] += xValue * yValue;
							Vector<Double> v = new Vector<Double>();
							v.add(xValue);
							v.add(yValue);
							mats.get(xData).get(yData).add(v);
						}
					}
				}
			}
		}

		int n;
		double cov, sigmax, sigmay;
		for (int i = 0; i < nbVariables; ++i) {
			corr[i][i] = 1;
			for (int j = i + 1; j < nbVariables; ++j) {
				/*
				 * n = nbData[i][j]; cov = productSum[i][j] / n - simpleSum[i][j] *
				 * simpleSum[j][i] / n / n; // standard error of x sigmax =
				 * Math.sqrt(squareSum[i][j] / n - simpleSum[i][j] * simpleSum[i][j] / n / n);
				 * // standard error of y sigmay = Math.sqrt(squareSum[j][i] / n -
				 * simpleSum[j][i] * simpleSum[j][i] / n / n); corr[i][j] = cov / sigmax /
				 * sigmay; corr[j][i] = corr[i][j];
				 */

				List<Vector<Double>> currV = mats.get(variables[i]).get(variables[j]);
				double[][] currData = new double[currV.size()][2];
				for (int k = 0; k < currV.size(); ++k) {
					currData[k][0] = currV.get(k).get(0);
					currData[k][1] = currV.get(k).get(1);
				}

				PearsonsCorrelation pc = new PearsonsCorrelation(currData);
				RealMatrix tmp = pc.computeCorrelationMatrix(currData);
				System.out.println(tmp.getEntry(0, 1));
				corr[i][j] = pc.computeCorrelationMatrix(currData).getEntry(0, 1);
				pvalues[i][j] = pc.getCorrelationPValues().getEntry(0, 1);
			}
		}
	}

	@Override
	public String toString() {
		String res = "        ";
		for (int i = 0; i < corr.length; ++i) {
			res += this.variables[i].toString() + "        ";
		}
		res += "\n";
		for (int i = 0; i < corr.length; ++i) {
			res += this.variables[i].toString() + "    ";
			for (int j = 0; j < corr.length; ++j) {
				res += corr[i][j] + "  ";
			}
			res += "\n";
		}
		return res;
	}

	public String toLatex() {
		String res = "\\begin{tabular}{|";
		for (int i = 0; i < corr.length + 1; ++i) {
			res += "c|";
		}
		res += "}\n";
		res += "\\hline\n";
		res += " & ";

		for (int i = 0; i < corr.length - 1; ++i) {
			res += this.variables[i].toString() + " & ";
		}

		res += this.variables[corr.length - 1].toString();
		res += " \\\\ \n";
		res += "\\hline\n";

		for (int i = 0; i < corr.length; ++i) {
			res += this.variables[i].toString();
			for (int j = 0; j < corr.length; ++j) {
				res += " & ";
				double tmp = corr[i][j];
				tmp = Math.round(tmp * 100000d) / 100000d;
				res += tmp;

			}
			res += "\\\\ \n";
			res += "\\hline\n";
		}
		res += "\\end{tabular} \\\\\n";

		return res;
	}
	
	public String toLatexPValues() {
		String res = "\\begin{tabular}{|";
		for (int i = 0; i < corr.length + 1; ++i) {
			res += "c|";
		}
		res += "}\n";
		res += "\\hline\n";
		res += " & ";

		for (int i = 0; i < pvalues.length - 1; ++i) {
			res += this.variables[i].toString() + " & ";
		}

		res += this.variables[pvalues.length - 1].toString();
		res += " \\\\ \n";
		res += "\\hline\n";

		for (int i = 0; i < pvalues.length; ++i) {
			res += this.variables[i].toString();
			for (int j = 0; j < pvalues.length; ++j) {
				res += " & ";
				double tmp = pvalues[i][j];
				tmp = Math.round(tmp * 100000d) / 100000d;
				res += tmp;

			}
			res += "\\\\ \n";
			res += "\\hline\n";
		}
		res += "\\end{tabular} \\\\\n";

		return res;
	}
}
