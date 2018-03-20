package gid.micro;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Data> dataList = Parser.parseData("./data/data.csv");
        System.out.println(dataList.get(0));
        Correlation corr = new Correlation(dataList);
        System.out.println(corr.toLatex());
    }
}