import java.util.ArrayList;
import java.util.*;

/**
 * The organism game
 * @author Xiang Li
 *
 */
public class OrganismsGame implements OrganismsGameInterface, Constants{
	/**count the total round the game runs, should be 5000 will no Exceptions*/
	int round = 0;
	
	/**create the 10x10 board with players on it */
	public Player[][] allPlayer = new Player[10][10];
	
	/**array with the energy of each organism on the square */
	public int[][] energy = new int[10][10];
	
	/**array with the amount of food on each square*/
	public int[][] food = new int[10][10];
	
	/**array to keep track whether the player has moved this round*/
	public boolean[][] doubleMoveInOneRound = new boolean[10][10];
	
	/**array with the ID of each player*/
	public int[][] ids = new int[10][10];
	
	public ArrayList<Player> players = new ArrayList<>();
	public double p,q;
	public GameConfig game;
	
	/** the HashMap whose key is the player object, and whose value is its Id number*/
	public HashMap<Player,Integer> playerToId = new HashMap<>();
	Random random;
	
	public static void main(String[] args) {
		System.out.println("Game Start, you are battling with the computer player");
		GameConfig gameData = new GameConfiguration(10,200,500,30);
		ArrayList<Player> playerss = new ArrayList<>();
		RandomPlayer randomPlayer = new RandomPlayer();
		playerss.add(randomPlayer);
		HumanPlayer you = new HumanPlayer();
		playerss.add(you);
		ComputerPlayer com = new ComputerPlayer();
		playerss.add(com);
		OrganismsGame org = new OrganismsGame();
		org.initialize(gameData,0.01,0.01,playerss);
		if (org.playGame()) {
			System.out.println("the game runs " + org.round + " rounds");
		}else {
			System.out.println("the round the game runs: " + org.round);
			System.out.println("The game ended because of the exception");
		}
		System.out.println("the players on the board are shown as below:");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (org.allPlayer[i][j] == null) {
					System.out.print(" none " + "  ");
				} else {
					System.out.print(org.allPlayer[i][j].name() + "  ");
				}
			}
			System.out.println("");
		}
		for (int i = 0; i < org.getResults().size(); i++) {
			System.out.println("The total energy of the player named " + org.players.get(i).name() + " is "
					+ org.getResults().get(i).getEnergy());
			System.out.println("the count of that player named " + org.players.get(i).name() + "is "
					+ org.getResults().get(i).getCount());
		}
		
	}


	/**
	 * initialize the data for the game
	 */
	public void initialize(GameConfig game, double p, double q, ArrayList<Player> players) {
		this.game = game;
		this.p = p;
		this.q = q;
		this.players = players;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				doubleMoveInOneRound[i][j] = false;
				double haveFood = Math.random();
				if(haveFood <= p) {
					food[i][j] = 1;
				}else {
					food[i][j] = 0;
				}
			}
		}
		for (int i = 0; i < players.size(); i++) {
			random = new Random();
			players.get(i).register(this.game, i);
			int playerId = random.nextInt(100000);//set the player ID for each player
			playerToId.put(players.get(i), playerId);//create the HashMap of player to player ID
			int rowPlaced = random.nextInt(10);
			int columnPlaced = random.nextInt(10);
			while(allPlayer[rowPlaced][columnPlaced] != null) {
			    rowPlaced = random.nextInt(10);
				columnPlaced = random.nextInt(10);
			}
			allPlayer[rowPlaced][columnPlaced] = players.get(i);
			energy[rowPlaced][columnPlaced] = 500;
			ids[rowPlaced][columnPlaced] = playerId;
		}
		for(int i = 0;i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(energy[i][j] != 500) {
					energy[i][j] = 0; //set the energy on the grid without player to 0
					ids[i][j] = -1;   //set the id of the grid without player to -1;
				}
			}
		}
			
	}


	@Override
	public boolean playGame() {
		try{
		while(round < 5000) {
			System.out.println("round: " + (this.round + 1));
			for(int a = 0; a < 10; a++) {
				for(int b = 0; b < 10; b++) {
					doubleMoveInOneRound[a][b] = false;
				}
			}
			//starting to eat the food
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) { 
					if(allPlayer[i][j] != null) {
						if(energy[i][j] < game.M() && food[i][j] > 0) {
						    energy[i][j] = energy[i][j] + game.u();
						    food[i][j]--;
						    if(energy[i][j] > game.M()) {
						    	energy[i][j] = game.M();
						    }
						}
					}else {
						if(food[i][j] == 0) {
							double foodBlowIn = Math.random();
							if (foodBlowIn <= p) {
								food[i][j] += 1;
								if (food[i][j] > game.K()) {
									food[i][j] = game.K();
								}
						    }
						}else {
							int a = 0;
							int sumOfFood = food[i][j];
							while(a < sumOfFood) {
								double foodDouble = Math.random();
								if(foodDouble <= q) {
									food[i][j]++;
								}
								a++;
							}
							if (food[i][j] > game.K()) {
								food[i][j] = game.K();
							}
						}
						
					}
				}
			}
			
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					// if there is a player on the grid, then start the moving action
					if(doubleMoveInOneRound[i][j] == false && allPlayer[i][j] != null) {
						boolean[] foodNear = new boolean[5];
						for(int a = 0; a < 5; a++) {
							foodNear[a] = (this.food[(10 + i + Constants.CYTrans[a]) % 10][(10 + j + Constants.CXTrans[a]) % 10] > 0);
						}
						int[] neighbor = new int[5];
						for(int b = 0; b < 5; b++){
							if(allPlayer[(10 + i+Constants.CYTrans[b]) % 10][(10 +j + Constants.CXTrans[b]) % 10] != null) {
								neighbor[b] = 0;
							}else {
								neighbor[b] = -1;
							}
						}

						Move oneMove = allPlayer[i][j].move(foodNear, neighbor, this.food[i][j], energy[i][j]);
						if(oneMove.type < 5 && oneMove.type > 0) {
							if(allPlayer[(10 + i + Constants.CYTrans[oneMove.type]) % 10][(10 + j + Constants.CXTrans[oneMove.type]) % 10] == null) {
							    allPlayer[(10 + i + Constants.CYTrans[oneMove.type]) % 10][(10 + j + Constants.CXTrans[oneMove.type]) % 10] = allPlayer[i][j];
							    allPlayer[i][j] = null;
							    ids[(10 + i + Constants.CYTrans[oneMove.type]) % 10][(10 + j + Constants.CXTrans[oneMove.type]) % 10] = ids[i][j];
							    ids[i][j] = -1;
							    energy[(10 + i + Constants.CYTrans[oneMove.type]) % 10][(10 + j + Constants.CXTrans[oneMove.type]) % 10] = energy[i][j] - game.v();
							    energy[i][j] = 0;
							    // the organism will not move again in this round
							    doubleMoveInOneRound[(10 + i + Constants.CYTrans[oneMove.type]) % 10][(10 + j + Constants.CXTrans[oneMove.type]) % 10] = true;
							} else {
								energy[i][j] = energy[i][j] - game.s();
							}
						}
						if(oneMove.type == 0) {
							energy[i][j] = energy[i][j] - game.s();
						}
						if(oneMove.type == 5) {
							if(allPlayer[(10 + i + Constants.CYTrans[oneMove.childpos]) % 10][(10 + j + Constants.CXTrans[oneMove.childpos]) % 10] == null) {
								//create the child when reproducing 
								Player child = allPlayer[i][j].getClass().newInstance();
								child.register(this.game, this.players.indexOf(allPlayer[i][j]));
								allPlayer[(10 + i + Constants.CYTrans[oneMove.childpos]) % 10][(10 + j + Constants.CXTrans[oneMove.childpos]) % 10] = child;
								energy[i][j] = (energy[i][j] - this.game.v())/2;
								energy[(10 + i + Constants.CYTrans[oneMove.childpos]) % 10][(10 + j + Constants.CXTrans[oneMove.childpos]) % 10] = energy[i][j];
								ids[(10 + i + Constants.CYTrans[oneMove.childpos]) % 10][(10 + j + Constants.CXTrans[oneMove.childpos]) % 10] = ids[i][j];
								// the child will not move again in this round
								doubleMoveInOneRound[(10 + i + Constants.CYTrans[oneMove.childpos]) % 10][(10 + j + Constants.CXTrans[oneMove.childpos]) % 10] = true;
								
								
							}else {
								energy[i][j] = energy[i][j] - game.s();
							}
						}
					}

				}
			}
			//check whether or not the organism on the grid(x,y) runs out of energy
			// if yes, this organism died and this instance of that player should be moved out of the array
			for(int x = 0; x < 10; x++) {
				for(int y = 0; y < 10; y++) {
					if(energy[x][y] <= 0) {
						energy[x][y] = 0;
						allPlayer[x][y] = null;
						ids[x][y] = -1;
					}
				}
			}
			
			round ++;
		}
		return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public ArrayList<PlayerRoundData> getResults() {
		ArrayList<PlayerRoundData> playerInformation = new ArrayList<>();
		for(int i = 0; i < players.size(); i++) {
			playerInformation.add(new PlayerData(this.playerToId.get(players.get(i)),this.energy, this.allPlayer, this.ids));
		}
		return playerInformation;
	}
	

}
