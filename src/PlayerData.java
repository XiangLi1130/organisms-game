
import java.util.Random;

/**
 * @author Xiang Li
 *
 */
public class PlayerData implements PlayerRoundData{
	Random rand;
	int id;
	int[][]energy;
	Player[][] players;
	int[][] iDs;
	
	public PlayerData(int id, int[][] energy, Player[][] players, int[][] iDs) {
		this.id = id;
		this.energy = energy;
		this.players = players;
		this.iDs = iDs;
	}


	public int getPlayerId() {
		return id;
	}


	public int getEnergy() {
		int totalEnergy = 0;
		//create the loop to find out the all players with the ID
		//same as this player and add all the energy of that player
		//together and get the total energy of that player
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(this.iDs[i][j] == this.id) {
					totalEnergy += energy[i][j];
				}
			}
		}
		return totalEnergy;
	}

	public int getCount() {
		int count = 0;
		//create the loop to find out the all players with the ID
		//same as this player and add one to the count
		//and get the total number of that player
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(this.iDs[i][j] == this.id) {
					count += 1;
				}
			}
		}
		return count;
	}

}
