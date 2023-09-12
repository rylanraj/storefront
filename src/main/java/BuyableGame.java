
public class BuyableGame extends Buyable {

    // Added showDetails method
    private int numPlayers;
    private String genre;
    
    public BuyableGame(double price, String name, int numPlayers, String genre) {
        super(price, name, "Game");
        this.numPlayers = numPlayers;
        this.genre = genre;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    
    public String getGenre() {
        return genre;
    }

    @Override
    public void showDetails(){
        super.showDetails();
        System.out.println("Number of players: " + getNumPlayers());
        System.out.println("Genre: " + getGenre());
    }
    
}
