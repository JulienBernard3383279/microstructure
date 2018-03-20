package gid.micro;

import java.util.Comparator;

public class DataAIMComparator implements Comparator<Data> {

	public int compare(Data o1, Data o2) {
		//TODO IMPLEMENTER
		if (o1.get(DataType.aim_t1)!=null) {
			if (o2.get(DataType.aim_t1)!=null) {
				return (o1.get(DataType.aim_t1) >= o2.get(DataType.aim_t1 )) ? 1 : -1;
			}
			else {
				return 1;
			}
		}
		else if (o2.get(DataType.aim_t1)!=null) {
			return -1;
		}
		return 0;
	}

}
