package battleship;

class Player {

	private static int uniqueID = 0;
	private final int id;
	private final Ship[] ships;
	private char[][] battlefield;

	public Player() {
		id = ++uniqueID;
		ships = Ship.values();
		battlefield = new char[10][10];
		for (int i = 0; i < battlefield.length; i++) {
			for (int j = 0; j < battlefield[i].length; j++) {
				battlefield[i][j] = '~';
			}
		}
	}

	public int getId() {
		return id;
	}

	public Ship[] getShips() {
		return ships;
	}

	public char[][] getBattlefield() {
		return battlefield;
	}
}
