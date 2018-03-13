import java.util.ArrayList;
import java.util.List;

public class Stock {

    private long id;
    private List<Data> spots;

    public Stock(long id) {
        this.id = id;
        this.spots = new ArrayList<>();
    }

    public void addSpot(Data spot) {
        this.spots.add(spot);
    }

}
