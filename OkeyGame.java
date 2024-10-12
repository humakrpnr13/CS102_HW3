import java.util.Arrays;
import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {

        for(int i = 0; i < 15; i++){
            players[0].addTile(tiles[i]);
        }

        for(int i = 1; i < players.length; i++){
            for(int j = 0; j < 14; j++){
                players[i].addTile(tiles[j]);
            }
        }
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile top = tiles[tiles.length - 1];
        tiles = Arrays.copyOf(tiles, tiles.length - 1);
        return "Picked tile: " + top;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for(int i = 0; i < tiles.length; i++){
            int randTile = rand.nextInt(tiles.length);
            Tile temp = tiles[i];
            tiles[i] = tiles[randTile];
            tiles[randTile] = temp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return players[currentPlayerIndex].isWinningHand();
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        boolean isFound = false;
        if(lastDiscardedTile != null){
            for( Tile tile : players[currentPlayerIndex].getTiles()){
                if(lastDiscardedTile.canFormChainWith(tile)){
                    isFound = true;
                }
            }
        }
        if(isFound){
            players[currentPlayerIndex].addTile(lastDiscardedTile);
            System.out.println("Computer picked the last discarded tile: " + lastDiscardedTile.toString());
            
        }
        else{
            players[currentPlayerIndex].addTile(tiles[tiles.length-1]);
            System.out.println("Computer doesn't picked the last discarded tile " + getTopTile());
    
        }

    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        boolean isDiscarded= false;
        //first we will check if there are duplicates
        Tile [] tilesToCheck = players[currentPlayerIndex].getTiles();
        for(int i = 0; i<tilesToCheck.length &&!isDiscarded ; i++){
            for(int c = i+1; c<tilesToCheck.length&&!isDiscarded; c++){
                if(tilesToCheck[i].compareTo(tilesToCheck[c])==0){
                    discardTile(c);
                    isDiscarded = true;
                }
            }
        }
        //if there is no duplicates, we will look if tile contribute to a chain
        if(!isDiscarded){
            boolean canForm = false;
            for(int i = 0; i<tilesToCheck.length &&!isDiscarded ; i++){
                for(int c = i+1; c<tilesToCheck.length&&!isDiscarded; c++){
                    if(tilesToCheck[i].canFormChainWith(tilesToCheck[c])){
                        canForm = true;
                    }
                }
                if(!canForm){
                    discardTile(i);
                    isDiscarded = true;
                }
            }
            
        }
        //if there is no single ones, we will discard the smallest one
        if(!isDiscarded){
            int smallest = smallestTileIndex(tilesToCheck);
            discardTile(smallest);
        }
    }
    /**
     * additional method I used for looking for the smallest value in a tile and get its index
     * @param tileIndex
     */
    public int smallestTileIndex (Tile[] tile){
        int index = 0;
        for(int i= 1; i < tile.length; i++){
            if(tiles[i].compareTo(tile[smallestTileIndex(tile)])<0){
                index = i;
            }
        }
        return index;
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
        System.out.println("Discarded tile: " + lastDiscardedTile);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
}
