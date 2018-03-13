public class Data {

    private long id;
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

    public Data(long id, long date, Double return_rf, Double beta, Double aim,
                Double log_market_cap, Double log_btm, Double pin,
                Double fsrv, Double turnover, Double illiq_amihud,
                Double betaSMB, Double betaHML, Double betaMOM,
                Double marketReturn, Double riskFreeReturn,
                Double excessReturn, Double smallMinusBig,
                Double highMinusLow, Double momentumFactor) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", date=" + date +
                ", return_rf=" + return_rf +
                ", beta=" + beta +
                ", aim=" + aim +
                ", log_market_cap=" + log_market_cap +
                ", log_btm=" + log_btm +
                ", pin=" + pin +
                ", fsrv=" + fsrv +
                ", turnover=" + turnover +
                ", illiq_amihud=" + illiq_amihud +
                ", betaSMB=" + betaSMB +
                ", betaHML=" + betaHML +
                ", betaMOM=" + betaMOM +
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
		case beta:
			return beta;
		case betaHML:
			return betaHML;
		case betaMOM:
			return betaMOM;
		case betaSMB:
			return betaSMB;
		case excessReturn:
			return excessReturn;
		case fsrv:
			return fsrv;
		case highMinusLow:
			return highMinusLow;
		case illiq_amihud:
			return illiq_amihud;
		case log_btm:
			return log_btm;
		case log_market_cap:
			return log_market_cap;
		case marketReturn:
			return marketReturn;
		case momentumFactor:
			return momentumFactor;
		case pin:
			return pin;
		case return_rf:
			return return_rf;
		case riskFreeReturn:
			return riskFreeReturn;
		case smallMinusBig:
			return smallMinusBig;
		case turnover:
		default:
			return turnover;
    	}
    }
    
    public double get_return() {
    	return return_rf + riskFreeReturn;
    }

}