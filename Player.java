public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        Tile[] newHand = new Tile[playerTiles.length-1];

        for(int i = index; i < playerTiles.length; i++){
            playerTiles[i] = playerTiles[i+1];
        }

        for(int n = 0; n < newHand.length; n++) {
            newHand[n] = playerTiles[n];
        }

        Tile removedTile = playerTiles[index];

        return removedTile;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {
        if(numberOfTiles < 15) {
            int index = 0;
            while( index < numberOfTiles && playerTiles[index] != null && playerTiles[index].compareTo(t) <= 0) {
                index++;
            }
            for(int i = numberOfTiles-1; i > index; i--) {
                playerTiles[i] = playerTiles[i-1];
            }        
            playerTiles[index] = t; 
            numberOfTiles++;   
        }
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        int chainCount = 0;
        int yellow = 0;
        int black = 0;
        int red = 0;
        int blue = 0;

        for(int value = 1; value <= 7; value++) {

            for(int i = 0; i < playerTiles.length; i++) {
                Tile currentTile = playerTiles[i];
                if(currentTile != null && currentTile.getValue() == value) {
                    if(currentTile.getColor() == 'Y') {
                        yellow++;
                    }
                    else if(currentTile.getColor() == 'B') {
                        blue++;
                    }
                    else if(currentTile.getColor() == 'R') {
                        red++;
                    }
                    else if(currentTile.getColor() == 'K') {
                        black++;
                    }
                }
            }
            int[] colorCount = new int[4]; //0: 'Y' 1: 'B' 2: 'R' 3: 'K'
                colorCount[0] = yellow;
                colorCount[1] = blue;
                colorCount[2] = red;
                colorCount[3] = black;

                int diffColor = 0;
                for(int i = 0; i < colorCount.length; i++) {
                    if(colorCount[i]>0) {
                        diffColor++;
                    }
                }

                if(diffColor >= 4) {
                    chainCount++;
                }
        }
        if(chainCount>=3) {
            return true;
        }
        else {
            return false;
        }

    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i] != null && playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i] != null){
                System.out.print(playerTiles[i].toString() + " ");
            }
            
        }
        System.out.println();
    }
 
    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
