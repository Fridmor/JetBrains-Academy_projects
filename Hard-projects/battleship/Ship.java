package battleship;

enum Ship {
	AIRCRAFT_CARRIER("Aircraft Carrier", 5), BATTLESHIP("Battleship", 4), SUBMARINE("Submarine", 3),
	CRUISER("Cruiser", 3), DESTROYER("Destroyer", 2);

	private final String name;
	private final int size;
	private final int[][] coordinates;
	private boolean isActive;

	Ship(String name, int size) {
		this.name = name;
		this.size = size;
		this.coordinates = new int[size][2];
	}

	String getName() {
		return name;
	}

	int getSize() {
		return size;
	}

	int[][] getCoordinates() {
		return coordinates;
	}

	boolean isActive() {
		return isActive;
	}
}
