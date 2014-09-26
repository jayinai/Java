import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Full version of Sink Dotcom.
 */

class Helper {
	private static final String alphabet = "abcdefg";
	private int gridLength = 7;
	private int gridSize = 49;
	private int[] grid = new int[gridSize];
	private int comCount = 0;

	public String getUserInput(String prompt) {
		String inputLine = null;
		System.out.println(prompt + " ");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			inputLine = br.readLine();
			if (inputLine.length() == 0) return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine.toLowerCase();
	}
	
	public ArrayList<String> placeDotCom(int comSize) {
		ArrayList<String> alphaCells = new ArrayList<String>();
		String [] alphacoords = new String [comSize];
		String temp = null;
		int [] coords = new int[comSize];
		int attempts = 0;
		boolean success = false;
		int location = 0;
		
		comCount++;
		int incr = 1;
		if ((comCount % 2 ) == 1) {
			incr = gridLength;
		}
		
		while (!success & attempts ++ < 200) {
			location = (int) (Math.random() * gridSize);
			int x = 0;
			success = true;
			while (success && x < comSize) {
				if (grid[location] == 0) {
					coords[x++] = location;
					location += incr;
					if (location >= gridSize) {
						success = false;
					}
					if (x > 0 && (location % gridLength == 0)) {
						success = false;
					}
				} else {
					success = false;
				}
			}
		}
		
		int x = 0;
		int row = 0;
		int column = 0;
		while (x < comSize) {
			grid[coords[x]] = 1;
			row = (int) (coords[x] / gridLength);
			column = coords[x] % gridLength;
			temp = String.valueOf(alphabet.charAt(column));
			
			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;
		}
		
		return alphaCells;
	}
	
}


class DotCom {

	private ArrayList<String> locationCells;
	private String name;

	public void setLocationCells(ArrayList<String> loc) {
		locationCells = loc;
	}

	public void setName(String name) {
		this.name = name;
	}

	// return a string represents whether user gets it or not
	public String checkYourself(String userInput) {
		String result = "miss";

		// return index if in the ArrayList; -1 otherwise
		int index = locationCells.indexOf(userInput);

		// if index >= 0, the userInput is definitely in the ArrayList
		if (index >= 0) {
			locationCells.remove(index);

			if (locationCells.isEmpty()) {
				result = "kill";
				System.out.println("Ouch! you sunk " + name + " : (");
			} else
				result = "hit";
		}
		return result;
	}

}

public class DotComGame {

	// =============== Variable Declarations ==================

	// declare and instantiate the Helper instance variable
	private Helper helper = new Helper();

	// declare and instantiate an ArrayList to hold the list of DotComs
	private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();

	// declare an int variable to hold the number of guess
	private int numOfGuesses = 0;

	// =============== Method Declarations ==================

	// declare a setUpGame() method to create and initialize the DotCom objects
	// with names and locations.
	private void setUpGame() {
		// make three DotCom objects, give 'em names and stick 'em in ArrayList
		DotCom one = new DotCom();
		one.setName("Pets.com");
		DotCom two = new DotCom();
		one.setName("eToys.com");
		DotCom three = new DotCom();
		one.setName("Go2.com");
		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);

		// print brief instructions for user
		System.out.println("Your goal is to sink three dot coms.");
		System.out.println("Pets.com, eToys.com, Go2.com");
		System.out
				.println("Try to sink them all in the fewest number of guesses");

		// for each DotCom in the list, ask the helper for a location and set
		// the location
		for (DotCom dotComToSet : dotComsList) {
			ArrayList<String> newLocation = helper.placeDotCom(3);
			dotComToSet.setLocationCells(newLocation);
		}
	}

	// declare a startPlaying() method that asks the player for guesses and
	// calls the checkUserGuess() method until all the DotCom objects are
	// removed from play.
	private void startPlaying() {
		while (!dotComsList.isEmpty()) {
			String userGuess = helper.getUserInput("Enter a guess");
			checkUserGuess(userGuess);
		}
		finishGame();
	}

	// declare a checkUserGuess() method that loops through all remaining DotCom
	// objects and calls each DotCom object's checkYourself() method.
	private void checkUserGuess(String userGuess) {
		numOfGuesses++;
		String result = "miss";

		for (DotCom dotComToTest : dotComsList) {
			result = dotComToTest.checkYourself(userGuess);
			if (result.equals("hit")) {
				break;
			}
			if (result.equals("kill")) {
				dotComsList.remove(dotComToTest);
				break;
			}
		}
		System.out.println(result);
	}

	// declare a finishGame() method that prints a message about the user's
	// performance, based on how many guesses it took to sink all the DotCom
	// objects.
	public void finishGame() {
		System.out
				.println("All Dot Coms are dead! Your stock is now worthless");
		if (numOfGuesses <= 18) {
			System.out.println("It only took you" + numOfGuesses + "guesses.");
			System.out.println("You got out before your options sank.");
		} else {
			System.out.println("Took you long enough." + numOfGuesses
					+ "guesses.");
			System.out.println("Fish are dancing with your options.");
		}

	}

	public static void main(String[] args) {
		DotComGame game = new DotComGame();
		game.setUpGame();
		game.startPlaying();
	}
}
