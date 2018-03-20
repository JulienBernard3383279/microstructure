package gid.micro;

import java.util.List;

public class Correlation {
	double[][] corr;
	DataType[] variables = { DataType.return_return, DataType.beta, DataType.aim, DataType.log_market_cap,
			DataType.log_btm, DataType.pin, DataType.fsrv, DataType.turnover, DataType.illiq_amihud };

	public Correlation(List<Data> dataList) {
		int nbVariables = variables.length;
		corr = new double[nbVariables][nbVariables];
		double[][] simpleSum = new double[nbVariables][nbVariables];
		double[][] squareSum = new double[nbVariables][nbVariables];
		double[][] productSum = new double[nbVariables][nbVariables];
		int[][] nbData = new int[nbVariables][nbVariables];

		DataType xData;
		DataType yData;
		Double xValue;
		Double yValue;
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
				n = nbData[i][j];
				cov = productSum[i][j] / n - simpleSum[i][j] * simpleSum[j][i] / n / n;
				// standard error of x
				sigmax = Math.sqrt(squareSum[i][j] / n - simpleSum[i][j] * simpleSum[i][j] / n / n);
				// standard error of y
				sigmay = Math.sqrt(squareSum[j][i] / n - simpleSum[j][i] * simpleSum[j][i] / n / n);
				corr[i][j] = cov / sigmax / sigmay;
				corr[j][i] = corr[i][j];
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
		for (int i = 0; i < corr.length + 1 ; ++i) {
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
				tmp = Math.round(tmp*100000d)/100000d;
				res += tmp;
				
			}
			res += "\\\\ \n";
			res += "\\hline\n";
		}
		res += "\\end{tabular} \\\\\n";

		return res;
	}
}
