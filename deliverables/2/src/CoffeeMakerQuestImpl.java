import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {
	
	LinkedList<Room> rooms = new LinkedList<Room>();
	Player player;
	int inRoom = -1;
	boolean gameIsOver = false;
	
	CoffeeMakerQuestImpl() {
		// TODO
		
	}
	
	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		return gameIsOver;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		// TODO
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		if (!rooms.isEmpty() || room == null) return false;
		rooms.add(room);
		System.out.println("romsize is " + rooms.size());
		System.out.println("1st room added " + room + "\n" + room.getDescription());
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		// TODO
		if (room == null || northDoor == null || southDoor == null || rooms.isEmpty()) return false;
		for (int i = 0; i < rooms.size(); i++) {
			if (room.getFurnishing().equals(rooms.get(i).getFurnishing()) || room.getAdjective().equals(rooms.get(i).getAdjective())) return false;
		}
		rooms.get(rooms.size()-1).setNorthDoor(northDoor);
		rooms.add(room);
		room.setSouthDoor(southDoor);
		System.out.println("romsize is " + rooms.size());
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		// TODO
		if (inRoom == -1) return null;
		System.out.println("players in " + rooms.get(inRoom));
		System.out.println("inRoom2 is " + inRoom);
		return rooms.get(inRoom);
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		// TODO
		if (rooms.contains(room)) {
			inRoom = rooms.indexOf(room); 
			System.out.println("inRoom is " + inRoom);
			return true;
		}
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		// TODO
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		// TODO
		cmd = cmd.toUpperCase();
		String res = "";
		switch (cmd) {
			case "N": 
				inRoom++;
				if (inRoom >= rooms.size()) {
					res = "A door in that direction does not exist.\n";
					break;
				}
				res = "\n";
				break;
			case "S": 
				inRoom--;
				if (inRoom < 0) {
					res = "A door in that direction does not exist.\n";
					break;
				}
				res = "\n";
				break;
			case "L":
				Item item;
				item = rooms.get(inRoom).getItem();
				if (item == Item.NONE) {
					res = "You don't see anything out of the ordinary.\n";
							break;
				} else if (item == Item.COFFEE) {
					player.addItem(item);
					res = "There might be something here...\nYou found some caffeinated coffee!\n";
					break;
				} else if (item == Item.COFFEE) {
					player.addItem(item);
					res = "There might be something here...\nYou found some caffeinated coffee!\n";
					break;
				} else if (item == Item.CREAM) {
					player.addItem(item);
					res = "There might be something here...\nYou found some caffeinated coffee!\n";
					break;
				} else if (item == Item.SUGAR) {
					player.addItem(item);
					res = "There might be something here...\nYou found some sweet sugar!\n";
					break;
				}
			case "D":
				if (player.checkCoffee() && player.checkCream() && player.checkSugar()) {
					res = player.getInventoryString() + "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n";
				} else if (!player.checkCoffee() && player.checkCream() && player.checkSugar()) {
					res = player.getInventoryString() + "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
				} else if (player.checkCoffee() && !player.checkCream() && player.checkSugar()) {
					res = player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";;
				} else if (player.checkCoffee() && player.checkCream() && !player.checkSugar()) {
					res = player.getInventoryString() + "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
				} else if (!player.checkCoffee() && !player.checkCream() && player.checkSugar()) {
					res = player.getInventoryString() + "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
				} else if (!player.checkCoffee() && player.checkCream() && !player.checkSugar()) {
					res = player.getInventoryString() + "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
				} else if (player.checkCoffee() && !player.checkCream() && !player.checkSugar()) {
					res = player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
				} else if (!player.checkCoffee() && !player.checkCream() && !player.checkSugar()) {
					res = player.getInventoryString() + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
				} 
				gameIsOver = true;
				break;
			case "I":
				res = player.getInventoryString();
				break;
			case "H": 
				res = "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
				break;
			default: 
				res = "What?\n";
		}
		return res;
	}
	
}
