public class Data {

    private long date;
    private Double return_rf;
    private Double beta;
    private Double aim;
    private Double log_market_cap;
    private Double log_btm;
    private Double pin;
    private Double fsrv;
    private Double turnover;
    private Double illiq_amihud;
    private Double betaSMB;
    private Double betaHML;
    private Double betaMOM;
    private Double marketReturn;
    private Double riskFreeReturn;
    private Double excessReturn;
    private Double smallMinusBig;
    private Double highMinusLow;
    private Double momentumFactor;

    public Data(long date, Double return_rf, Double beta, Double aim,
                Double log_market_cap, Double log_btm, Double pin,
                Double fsrv, Double turnover, Double illiq_amihud,
                Double betaSMB, Double betaHML, Double betaMOM,
                Double marketReturn, Double riskFreeReturn,
                Double excessReturn, Double smallMinusBig,
                Double highMinusLow, Double momentumFactor) {
        this.date = date;
        this.return_rf = return_rf;
        this.beta = beta;
        this.aim = aim;
        this.log_market_cap = log_market_cap;
        this.log_btm = log_btm;
        this.pin = pin;
        this.fsrv = fsrv;
        this.turnover = turnover;
        this.illiq_amihud = illiq_amihud;
        this.betaSMB = betaSMB;
        this.betaHML = betaHML;
        this.betaMOM = betaMOM;
        this.marketReturn = marketReturn;
        this.riskFreeReturn = riskFreeReturn;
        this.excessReturn = excessReturn;
        this.smallMinusBig = smallMinusBig;
        this.highMinusLow = highMinusLow;
        this.momentumFactor = momentumFactor;
    }
}
