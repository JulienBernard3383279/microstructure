package gid.micro;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class Regression {
	OLSMultipleLinearRegression regression;
	
	private boolean hasDatas(List<DataType> dataTypes, Data data) {
		for (DataType dataType : dataTypes) {
			if (data.get(dataType)==null) return false;
		}
		return true;
	}
	private Map<Long,List<Data>> filter(final List<DataType> dataTypes, List<Data> datas) {
		return datas.stream()
				.filter(x -> hasDatas(dataTypes, x))
				.collect(Collectors.groupingBy(Data::getDate));			
	}
}
