import java.util.Random;
import java.util.Scanner;

/**
 * the human player
 * @author Xiang Li
 *
 */
public class HumanPlayer implements Player{
	
	private static final String NAME = "  You ";
	private int state;
	private Random rand;
	private GameConfig game;
	Scanner scanner;

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
		System.out.println("here shows the direction array: { STAYPUT, WEST, EAST, NORTH, SOUTH }");
		System.out.println("the food condition on the neighboring aquare is that: ");
		for(int i = 0; i < food.length; i++) {
		    System.out.print(food[i]+" ");
		}
		System.out.println("  ");
		System.out.println("whether there is another organism on a neighboring square\n-1 means no and any >=0 number means yes: ");
		for(int i = 0; i < neighbors.length; i++) {
		    System.out.print(neighbors[i]+" ");
		}
		System.out.println("");
		System.out.println("the food left on this square is: "+foodleft);
		System.out.println("the energy left for this organism is: "+energyleft);
		System.out.println("energy consumed in staying put: " + this.game.s());
		System.out.println("energy consumed in moving or reproducing: " + this.game.v());
		System.out.println("energy per unit of food: " + this.game.u());
		System.out.println("maximum energy per organism: " + this.game.M());
		System.out.println("maximum food units per cell: "+ this.game.K());
		System.out.println("what kind of move you'd like to take?\n0for Stayput, 1 for west, 2 for east, 3 for north, 4 for south, 5 for reproduce");

		scanner = new Scanner(System.in);
		while (!(scanner.hasNextInt())) {
			System.out.println("illegal input,please input integer");
			System.out.println("what kind of move you'd like to take?\n 0for Stayput, 1 for west, 2 for east, 3 for north, 4 for south, 5 for reproduce");
			scanner = new Scanner(System.in);
		}
		int direction = scanner.nextInt();
		while (direction < 0 || direction > 5) {
			System.out.println("illegal input,please input integer 0 to 5");
			System.out.println("what kind of move you'd like to take?\n0for Stayput, 1 for west, 2 for east, 3 for north, 4 for south, 5 for reproduce");
			scanner = new Scanner(System.in);
			while (!(scanner.hasNextInt())) {
				System.out.println("illegal input,please input integer");
				System.out.println("what kind of move you'd like to take?\n 0for Stayput, 1 for west, 2 for east, 3 for north, 4 for south, 5 for reproduce");
				scanner = new Scanner(System.in);
			}
			direction = scanner.nextInt();
		}
		if(direction < 5) {
			return new Move(direction);
		} else {
			System.out.println("you choose to reproduce, in which direction you want the child to be reproduced\n1 for west, 2 for east, 3 for north, 4 for south");
			scanner = new Scanner(System.in);
			while (!(scanner.hasNextInt())) {
				System.out.println("illegal input,please input integer");
				System.out.println("in which direction you want the child to be reproduced\n1 for west, 2 for east, 3 for north, 4 for south");
				scanner = new Scanner(System.in);
			}
			int childDirection = scanner.nextInt();
			while (childDirection < 1 || childDirection > 4) {
				System.out.println("illegal input,please input integer 1 to 4");
				System.out.println("what kind of move you'd like to take?\n1 for west, 2 for east, 3 for north, 4 for south");
				scanner = new Scanner(System.in);
				while(!(scanner.hasNextInt())) {
					System.out.println("illegal input,please input integer");
					System.out.println("in which direction you want the child to be reproduced\n1 for west, 2 for east, 3 for north, 4 for south");
					scanner = new Scanner(System.in);
				}
				childDirection = scanner.nextInt();
			}
			return new Move(direction,childDirection,state);
			
		}
	}

	

}
