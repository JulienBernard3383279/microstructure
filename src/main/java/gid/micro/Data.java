package gid.micro;

public class Data {

    private long id;
    private long date;
    private Double return_rf;
    private Double beta;
    private Double beta_t1;
    private Double aim;
    private Double aim_t1;
    private Double log_market_cap;
    private Double log_market_cap_t1;
    private Double log_btm;
    private Double log_btm_t1;
    private Double pin;
    private Double pin_t1;
    private Double fsrv;
    private Double fsrv_t1;
    private Double turnover;
    private Double turnover_t1;
    private Double illiq_amihud;
    private Double illiq_amihud_t1;
    private Double betaSMB;
    private Double betaSMB_t1;
    private Double betaHML;
    private Double betaHML_t1;
    private Double betaMOM;
    private Double betaMOM_t1;
    private Double marketReturn;
    private Double riskFreeReturn;
    private Double excessReturn;
    private Double smallMinusBig;
    private Double highMinusLow;
    private Double momentumFactor;

    public Data(long id, long date, Double return_rf, Double beta, Double beta_t1,
                Double aim, Double aim_t1, Double log_market_cap, Double log_market_cap_t1,
                Double log_btm, Double log_btm_t1, Double pin, Double pin_t1,
                Double fsrv, Double fsrv_t1, Double turnover, Double turnover_t1,
                Double illiq_amihud, Double illiq_amihud_t1, Double betaSMB,
                Double betaSMB_t1, Double betaHML, Double betaHML_t1, Double betaMOM,
                Double betaMOM_t1, Double marketReturn, Double riskFreeReturn,
                Double excessReturn, Double smallMinusBig,
                Double highMinusLow, Double momentumFactor) {
        this.id = id;
        this.date = date;
        this.return_rf = return_rf;
        this.beta = beta;
        this.beta_t1 = beta_t1;
        this.aim = aim;
        this.aim_t1 = aim_t1;
        this.log_market_cap = log_market_cap;
        this.log_market_cap_t1 = log_market_cap_t1;
        this.log_btm = log_btm;
        this.log_btm_t1 = log_btm_t1;
        this.pin = pin;
        this.pin_t1 = pin_t1;
        this.fsrv = fsrv;
        this.fsrv_t1 = fsrv_t1;
        this.turnover = turnover;
        this.turnover_t1 = turnover_t1;
        this.illiq_amihud = illiq_amihud;
        this.illiq_amihud_t1 = illiq_amihud_t1;
        this.betaSMB = betaSMB;
        this.betaSMB_t1 = betaSMB_t1;
        this.betaHML = betaHML;
        this.betaHML_t1 = betaHML_t1;
        this.betaMOM = betaMOM;
        this.betaMOM_t1 = betaMOM_t1;
        this.marketReturn = marketReturn;
        this.riskFreeReturn = riskFreeReturn;
        this.excessReturn = excessReturn;
        this.smallMinusBig = smallMinusBig;
        this.highMinusLow = highMinusLow;
        this.momentumFactor = momentumFactor;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", date=" + date +
                ", return_rf=" + return_rf +
                ", beta=" + beta +
                ", beta_t1=" + beta_t1 +
                ", aim=" + aim +
                ", aim_t1=" + aim_t1 +
                ", log_market_cap=" + log_market_cap +
                ", log_market_cap_t1=" + log_market_cap_t1 +
                ", log_btm=" + log_btm +
                ", log_btm_t1=" + log_btm_t1 +
                ", pin=" + pin +
                ", pin_t1=" + pin_t1 +
                ", fsrv=" + fsrv +
                ", fsrv_t1=" + fsrv_t1 +
                ", turnover=" + turnover +
                ", turnover_t1=" + turnover_t1 +
                ", illiq_amihud=" + illiq_amihud +
                ", illiq_amihud_t1=" + illiq_amihud_t1 +
                ", betaSMB=" + betaSMB +
                ", betaSMB_t1=" + betaSMB_t1 +
                ", betaHML=" + betaHML +
                ", betaHML_t1=" + betaHML_t1 +
                ", betaMOM=" + betaMOM +
                ", betaMOM_t1=" + betaMOM_t1 +
                ", marketReturn=" + marketReturn +
                ", riskFreeReturn=" + riskFreeReturn +
                ", excessReturn=" + excessReturn +
                ", smallMinusBig=" + smallMinusBig +
                ", highMinusLow=" + highMinusLow +
                ", momentumFactor=" + momentumFactor +
                '}';
    }

    public long getDate() {
        return date;
    }

    public Double get(DataType type) {
        switch (type) {
            case aim:
                return aim;
            case aim_t1:
                return aim_t1;
            case beta:
                return beta;
            case beta_t1:
                return beta_t1;
            case betaHML:
                return betaHML;
            case betaHML_t1:
                return betaHML_t1;
            case betaMOM:
                return betaMOM;
            case betaMOM_t1:
                return betaMOM_t1;
            case betaSMB:
                return betaSMB;
            case betaSMB_t1:
                return betaSMB_t1;
            case pin:
                return pin;
            case pin_t1:
                return pin_t1;
            case fsrv:
                return fsrv;
            case fsrv_t1:
                return fsrv_t1;
            case illiq_amihud:
                return illiq_amihud;
            case illiq_amihud_t1:
                return illiq_amihud_t1;
            case turnover:
                return turnover;
            case turnover_t1:
                return turnover_t1;
            case log_btm:
                return log_btm;
            case log_btm_t1:
                return log_btm_t1;
            case log_market_cap:
                return log_market_cap;
            case log_market_cap_t1:
                return log_market_cap_t1;
            case marketReturn:
                return marketReturn;
            case momentumFactor:
                return momentumFactor;
            case excessReturn:
                return excessReturn;
            case riskFreeReturn:
                return riskFreeReturn;
            case smallMinusBig:
                return smallMinusBig;
            case highMinusLow:
                return highMinusLow;

            case return_return:
                if (return_rf == null || riskFreeReturn == null) {
                    return null;
                }
                return return_rf + riskFreeReturn;
            case return_rf:
            default:
                return return_rf;
        }
    }

    public double get_return() {
        return return_rf + riskFreeReturn;
    }

}