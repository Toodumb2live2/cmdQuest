//C:\Users\Alex\Documents\Comp Sci 2016_2017\Programming\Tutorial
//groovy cmdQuest

final int MAP_SIZE = 21; //Set the constant value of map size to the bounds of the board

String[][] mapArray = new int[MAP_SIZE][MAP_SIZE]; //declares the main array for the program 

int playerHP = 10; //intialise and declare the players stat values
int playerATK = 2;
int playerCash = 10;

mapArray = intialiseMap(mapArray);
mapArray = placePlayer(mapArray, MAP_SIZE);
mapArray = popMap(mapArray);
printMap(mapArray); //intialise and populate the board


String playerName = System.console().readLine "Hello! Please enter your name: "; //take the name of the player


boolean exit = false; //declare the variable for a failler state

while (!exit) { //loop while player has not lost or exited
    String userInput = System.console().readLine "What would you like to do?";
    exit = userAction(mapArray, userInput, playerHP, playerATK); //takes the user input and sends it to the action function
   
    if (playerHP <= 0) { //is the player dead? 
        exit = false;
    }
    
    if (shopCheck(mapArray) == "T" && exit == false) { //If the player ends the turn near a store then they have a chance to buy from it
        String itemSelect = shopSell(playerCash, playerHP, playerATK); //Takes and declares the users choice in the store
        if (itemSelect == "P") {
            playerHP += 1;
            playerCash -= 3;
            println("Thank you for buying a potion you have " + playerCash + " gold left"); //if the player chooses a potion this sets the correct effects on the player and takes the gold away
        } else if (itemSelect == "S") {
            playerATK += 1;
            playerCash -= 5;
            println("Thank you for buying a sword you have " + playerCash + " gold left"); //ditto above but for the sword
        } else if (itemSelect == "N") { 
            println("Sorry you cant do that"); //if the user cant buy any thing then tell them
        }
    }
}
println("Thanks for playing " + playerName); //if the user leaves then say by

//Checks if the user is near a town and returns a value if they are
def String shopCheck(String[][] mapArray) {
    playerX = findPlayerX(mapArray);
    playerY = findPlayerY(mapArray);

    if (mapArray[playerX + 1][playerY] == "T" || mapArray[playerX - 1][playerY] == "T" || mapArray[playerX][playerY + 1] == "T" || mapArray[playerX][playerY - 1] == "T") {
        return "T";
    }
}

//Gives the user a choice and returns if the player can buy it or not
def String shopSell(int playerCash, int playerHP, int playerATK) {
    println("Hello! Welcome to the town store!");
    println("1: Sword (increse attack by one) 5g");
    println("2: Potion (increse Hp by one) 3g");
    String userInput = System.console().readLine "What would you like to buy?";
    if (userInput == "1") {
        if (playerCash >= 5 && playerATK < 6) {
            return "S";
        } else {
            return "N";
        }
    } else if (userInput == "2") {
        if (playerCash >= 3) {
            return "P";
        } else {
            return "N";
        }
    } else {
        println("Sorry thats not an option!");
    }
}

//Will take the users input and direct it to the correct function to execute
def boolean userAction(String[][] mapArray, String userInput, int playerHP, int playerATK) {

    switch (userInput) {
        case "go north":
            movePlayer(mapArray, "north");
            break;
        case "go east":
            movePlayer(mapArray, "east");
            break;
        case "go south":
            movePlayer(mapArray, "south");
            break;
        case "go west":
            movePlayer(mapArray, "west");
            break;
        case "look":
            playerLook(mapArray);
            break;
        case "exit":
            return true;
            break;
        case "map":
            printMap(mapArray);
            break;
        case "attack north":
            playerHP -= playerAttack(mapArray, "north", playerHP, playerATK);
            break;
        case "attack east":
            playerHP -= playerAttack(mapArray, "east", playerHP, playerATK);
            break;
        case "attack south":
            playerHP -= playerAttack(mapArray, "south", playerHP, playerATK);
            break;
        case "attack west":
            playerHP -= playerAttack(mapArray, "west", playerHP, playerATK);
            break;
        case "help":
            println("All commands are in lowercase");
            println("You can use go/attack and a cardinal direction to either move or attack");
            println("You can do look to look in all 4 cardinal directions");
            break;
        case "save":
            saveToFile(mapArray);
            break;
        case "load":
            mapArray = loadFromFile(mapArray);
            break;
        default:
            println("That is not a command");
            break;
    }
    return false;
}

//the player gives a direction and then it is decied if it can attack or not
def int playerAttack(String[][] mapArray, String direction, int playerHP, int playerATK) {
    
    playerX = findPlayerX(mapArray);
    playerY = findPlayerY(mapArray);
    println(playerX);
    println(playerY);
    switch (direction) {
        case "north":
            String northHolds = mapArray[playerX - 1][playerY];
            if (northHolds == "O") { 
                if ((randomNum(0, 10)) >= playerATK) { //Checks if the random number is less then the players attack
                    println("You attack and kill the Orc! You get one gold!");
                    mapArray[playerX-1][playerY] == " ";
                    return 0;
                } else {
                    println("You attack the Orc but miss! You take one damage!");
                    return 1;
                }
            } else if (northHolds == "G") {
                if ((randomNum(0, 5)) >= playerATK) { //Checks if the random number is less then the players attack
                    println("You attack and kill the Goblin! You get one gold!");
                    mapArray[playerX - 1][playerY ] == " ";
                    return 0;
                } else {
                    println("You attack the Goblin but miss! You take one damage!");
                    return 1;
                }
            } else {
                println("You cant attack that!");
                return 0;
            }
            break;
        case "east":
            String eastHolds = mapArray[playerX][playerY + 1];
            if (eastHolds == "O") {
                if ((randomNum(0, 10)) >= playerATK) {
                    println("You attack and kill the Orc! You get one gold!");
                    mapArray[playerX][playerY + 1] = " ";
                    return 0;
                } else {
                    println("You attack the Orc but miss! You take one damage!");
                    return 1;
                }
            } else if (eastHolds == "G") {
                if ((randomNum(0, 5)) >= playerATK) {
                    println("You attack and kill the Goblin! You get one gold!");
                    mapArray[playerX][playerY + 1] = " ";
                    return 0;
                } else {
                    println("You attack the Goblin but miss! You take one damage!");
                    return 1;
                }
            } else {
                println("You cant attack that!");
                return 0;
            }
            break;
        case "south":
            String southHolds = mapArray[playerX + 1][playerY];
            if (southHolds == "O") {
                if ((randomNum(0, 10)) >= playerATK) {
                    println("You attack and kill the Orc! You get one gold!");
                    mapArray[playerX + 1][playerY] = " ";
                    return 0;
                } else {
                    println("You attack the Orc but miss! You take one damage!");
                    return 1;
                }
            } else if (southHolds == "G") {
                if ((randomNum(0, 5)) >= playerATK) {
                    println("You attack and kill the Goblin! You get one gold!");
                    mapArray[playerX + 1][playerY] = " ";
                    return 0;
                } else {
                    println("You attack the Goblin but miss! You take one damage!");
                    return 1;
                }
            } else {
                println("You cant attack that!");
                return 0;
            }
            break;
        case "west":
            String westHolds = mapArray[playerX][playerY - 1];
            if (westHolds == "O") {
                if ((randomNum(0, 10)) >= playerATK) {
                    println("You attack and kill the Orc! You get one gold!");
                    mapArray[playerX][playerY - 1] = " ";
                    return 0;
                } else {
                    println("You attack the Orc but miss! You take one damage!");
                    return 1;
                }
            } else if (westHolds == "G") {
                if ((randomNum(0, 5)) >= playerATK) {
                    println("You attack and kill the Goblin! You get one gold!");
                    mapArray[playerX][playerY - 1] = " ";
                    return 0;
                } else {
                    println("You attack the Goblin but miss! You take one damage!");
                    return 1;
                }
            } else {
                println("You cant attack that!");
                return 0;
            }
            break;
        default:
            println("Thats not a direction");
            return 0;
            break;
    }
    return 0;
}

//Checks the areas around the player and then tells them whats up
def void playerLook(String[][] mapArray) {
    playerX = findPlayerX(mapArray)
    playerY = findPlayerY(mapArray)

    String northHolds = mapArray[playerX - 1][playerY];
    String eastHolds = mapArray[playerX][playerY + 1];
    String southHolds = mapArray[playerX + 1][playerY];
    String westHolds = mapArray[playerX][playerY - 1];

    println("To the north lies " + contiansDes(northHolds));
    println("To the east lies " + contiansDes(eastHolds));
    println("To the south lies " + contiansDes(southHolds));
    println("To the west lies " + contiansDes(westHolds));

}


//returns the text values of the locations, probably going to be used later more
def String contiansDes(String direction) {
    switch (direction) {
        case "M":
            return "a mountain";
            break;
        case "P":
            return "a plains";
            break;
        case "V":
            return "a vally";
            break;
        case "T":
            return "a town";
            break;
        case "C":
            return "a small camp";
            break;
        case "O":
            return "some orcs";
            break;
        case "G":
            return "a band of goblins";
            break;
        case "F":
            return "a dense forest";
            break;
        case " ":
            return "a wide empty area";
            break;
        default:
            return "some thing beyond otherwordly";
            break;
    }
}

//Takes the players XY and decides if they can move the location specified. if they can then it will move them to the next square
def void movePlayer(String[][] mapArray, String direction) {
    int xMovement = 0;
    int yMovement = 0;
    int playerX = findPlayerX(mapArray);
    int playerY = findPlayerY(mapArray);

    switch (direction) {
        case "north":
            xMovement = -1;
            break;
        case "east":
            yMovement = 1;
            break;
        case "south":
            xMovement = 1;
            break;
        case "west":
            yMovement = -1;
            break;
    }
    String moveTo = mapArray[playerX + xMovement][playerY + yMovement];
    switch (moveTo) {
        case "M":
            println("A large mountain stands in your way and you cannot pass");
            break;
        case "P":
            println("You walk out into a large empty plains");
            mapArray[playerX + xMovement][playerY + yMovement] = mapArray[playerX][playerY];
            mapArray[playerX][playerY] = " ";
            break;
        case "V":
            println("A vast vally lies a head of you and you cannot pass");
            break;
        case "T":
            println("You walk into town and enjoy some music (MORE TO COME LATER)");
            break;
        case "C":
            println("You stumble into a small huter camp (MORE TO COME LATER)");
            break;
        case "O":
            println("An orc stands before you");
            break;
        case "G":
            println("An goblin stands before you ");
            break;
        case "F":
            println("You walk into a dense forest");
            mapArray[playerX + xMovement][playerY + yMovement] = mapArray[playerX][playerY];
            mapArray[playerX][playerY] = " ";
            break;
        case " ":
            println("You walk into a empty area");
            mapArray[playerX + xMovement][playerY + yMovement] = mapArray[playerX][playerY];
            mapArray[playerX][playerY] = " ";
            break;
        default:
            println("You cannot do that");
            break;
    }


}


//will check the array for the x coords for the player
def int findPlayerX(String[][] mapArray) {
    for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
            if (mapArray[i][j] == "£") {
                return i;
            }
        }
    }
    println("An error has occured please cite code 002 to the devleoper");
    return null;
}


//will check the array for the y coords of the player
def int findPlayerY(String[][] mapArray) {
    for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
            if (mapArray[i][j] == "£") {
                return j;
            }
        }
    }
    println("An error has occured please cite code 003 to the devleoper");
    return null;
}

//This function sets the boarders of the map and fill the empty spaces in the array with space charaters
def String[][] intialiseMap(String[][] mapArray) {
    for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
            if (j == 0 || j == mapArray.length - 1) {
                mapArray[i][j] = "|"
            } else if (i == 0 || i == mapArray.length - 1) {
                mapArray[i][j] = "-"
            } else {
                mapArray[i][j] = " ";
            }
        }
    }
    return mapArray;
}

//Displays the map array
def void printMap(String[][] mapArray) {
    for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
            print(mapArray[i][j]);
        }
        println();
    }
}

//Populates the map with a random number of random events in random locations
def String[][] popMap(String[][] mapArray) {
    int numberOfevents = randomNum(5, 15);
    int randomX = 0;
    int randomY = 0;
    int randomType = 0;

    for (int i = 0; i < numberOfevents; i++) {
        randomX = randomNum(1, mapArray.length - 2);
        randomY = randomNum(1, mapArray.length - 2);
        if (mapArray[randomX][randomY] == ' ') {
            randomtype = randomNum(0, 7);
            switch (randomtype) {
                case 0:
                    mapArray[randomX][randomY] = 'M';
                    break;
                case 1:
                    mapArray[randomX][randomY] = 'P';
                    break;
                case 2:
                    mapArray[randomX][randomY] = 'V';
                    break;
                case 3:
                    mapArray[randomX][randomY] = 'T';
                    break;
                case 4:
                    mapArray[randomX][randomY] = 'C';
                    break;
                case 5:
                    mapArray[randomX][randomY] = 'O';
                    break;
                case 6:
                    mapArray[randomX][randomY] = 'G';
                    break;
                case 7:
                    mapArray[randomX][randomY] = 'F';
                    break;
                default:
                    println("An error has occurred please cite 001 to the developer in the error report");
                    break;
            }
        }
    }
    return mapArray;
}

//Places the player token at the center of the map
def String[][] placePlayer(String[][] mapArray, int MAP_SIZE) {
    int x = MAP_SIZE / 2;
    int y = MAP_SIZE / 2;
    mapArray[x][y] = "£";
    return mapArray;
}

//returns a random integer between the min and max range
def int randomNum(int min, int max) {
    Random r = new Random();
    return r.nextInt(max - min) + min;
}

def void saveToFile(String[][] mapArray){
File file = new File("U:\\CSIT1\\Programming\\Tutorial\\cmdQuest\\test.txt");

file.write("");

for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
             file.append(mapArray[i][j]);       
        }
}


println (file.text);
}

def String[][] loadFromFile(String[][] mapArray){
File file = new File("U:\\CSIT1\\Programming\\Tutorial\\cmdQuest\\test.txt");
int pointer1 = 0;
int pointer2 = 1;
String stuff = file.getText();


for (int i = 0; i < mapArray.length; i++) {
        for (int j = 0; j < mapArray[i].length; j++) {
            mapArray[i][j] = stuff.substring(pointer1,pointer2);
            pointer1 += 1
            pointer2 += 1
            
        }
}
return mapArray;


}