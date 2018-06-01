import java.util.Random;

/**
 * The Computer Player
 * @author Xiang Li
 *
 */
public class ComputerPlayer implements Player{
	private static final String NAME = "Xiang ";
	private int state;
	private Random rand;
	private GameConfig game;

	@Override
	public void register(GameConfig game, int key) {
		rand = new Random();
		state = rand.nextInt(256);
		this.game = game;	
	}

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public Move move(boolean[] food, int[] neighbors, int foodleft, int energyleft) {
		if(foodleft > this.game.K() * 2/3) {
			return new Move(0);
		}else {
			for (int i = 0; i < food.length; i++) {
				if (food[i] == true && neighbors[i] == -1) {
					return new Move(5, i, state);
				}
			}
		}
		return new Move(0);
	}

}
