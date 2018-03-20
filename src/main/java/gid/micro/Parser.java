package gid.micro;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Data> parseData(String dataCsv) {
        List<Data> dataList = new ArrayList<Data>();
        BufferedReader br;

        try {
            // Open csv file
            br = new BufferedReader(new FileReader(dataCsv));

            // Skip header line
            br.readLine();

            String line;
            String delimiter = ",";
            while ((line = br.readLine()) != null) {
                String[] dataArray = line.split(delimiter);

                long id = Long.valueOf(dataArray[0]);
                long date = 12 * Long.valueOf(dataArray[1])
                        + Long.valueOf(dataArray[2]);
                Double return_rf = dataArray[3].equals("null") ?
                        null : Double.valueOf(dataArray[3]);
                Double beta = dataArray[4].equals("null") ?
                        null : Double.valueOf(dataArray[4]);
                Double beta_t1 = dataArray[5].equals("null") ?
                        null : Double.valueOf(dataArray[5]);
                Double aim = dataArray[6].equals("null") ?
                        null : Double.valueOf(dataArray[6]);
                Double aim_t1 = dataArray[7].equals("null") ?
                        null : Double.valueOf(dataArray[7]);
                Double log_market_cap = dataArray[8].equals("null") ?
                        null : Double.valueOf(dataArray[8]);
                Double log_market_cap_t1 = dataArray[9].equals("null") ?
                        null : Double.valueOf(dataArray[9]);
                Double log_btm = dataArray[10].equals("null") ?
                        null : Double.valueOf(dataArray[10]);
                Double log_btm_t1 = dataArray[11].equals("null") ?
                        null : Double.valueOf(dataArray[11]);
                Double pin = dataArray[12].equals("null") ?
                        null : Double.valueOf(dataArray[12]);
                Double pin_t1 = dataArray[13].equals("null") ?
                        null : Double.valueOf(dataArray[13]);
                Double fsrv = dataArray[14].equals("null") ?
                        null : Double.valueOf(dataArray[14]);
                Double fsrv_t1 = dataArray[15].equals("null") ?
                        null : Double.valueOf(dataArray[15]);
                Double turnover = dataArray[16].equals("null") ?
                        null : Double.valueOf(dataArray[16]);
                Double turnover_t1 = dataArray[17].equals("null") ?
                        null : Double.valueOf(dataArray[17]);
                Double illiq_amihud = dataArray[18].equals("null") ?
                        null : Double.valueOf(dataArray[18]);
                Double illiq_amihud_t1 = dataArray[19].equals("null") ?
                        null : Double.valueOf(dataArray[19]);
                Double betaSMB = dataArray[20].equals("null") ?
                        null : Double.valueOf(dataArray[20]);
                Double betaSMB_t1 = dataArray[21].equals("null") ?
                        null : Double.valueOf(dataArray[21]);
                Double betaHML = dataArray[22].equals("null") ?
                        null : Double.valueOf(dataArray[22]);
                Double betaHML_t1 = dataArray[23].equals("null") ?
                        null : Double.valueOf(dataArray[23]);
                Double betaMOM = dataArray[24].equals("null") ?
                        null : Double.valueOf(dataArray[24]);
                Double betaMOM_t1 = dataArray[25].equals("null") ?
                        null : Double.valueOf(dataArray[25]);
                Double marketReturn = dataArray[26].equals("null") ?
                        null : Double.valueOf(dataArray[26]);
                Double riskFreeReturn = dataArray[27].equals("null") ?
                        null : Double.valueOf(dataArray[27]);
                Double excessReturn = dataArray[28].equals("null") ?
                        null : Double.valueOf(dataArray[28]);
                Double smallMinusBig = dataArray[29].equals("null") ?
                        null : Double.valueOf(dataArray[29]);
                Double highMinusLow = dataArray[30].equals("null") ?
                        null : Double.valueOf(dataArray[30]);
                Double momentumFactor = dataArray[31].equals("null") ?
                        null : Double.valueOf(dataArray[31]);

                // Create data object
                Data data = new Data(
                        id,
                        date,
                        return_rf,
                        beta,
                        beta_t1,
                        aim,
                        aim_t1,
                        log_market_cap,
                        log_market_cap_t1,
                        log_btm,
                        log_btm_t1,
                        pin,
                        pin_t1,
                        fsrv,
                        fsrv_t1,
                        turnover,
                        turnover_t1,
                        illiq_amihud,
                        illiq_amihud_t1,
                        betaSMB,
                        betaSMB_t1,
                        betaHML,
                        betaHML_t1,
                        betaMOM,
                        betaMOM_t1,
                        marketReturn,
                        riskFreeReturn,
                        excessReturn,
                        smallMinusBig,
                        highMinusLow,
                        momentumFactor
                );

                // Add data into data list
                dataList.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

}