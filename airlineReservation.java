//Name: Jourdan Rampoldi
//Date: 11/ 13/ 2022
//Project: 8a (Airline Reservation program)



import java.util.Scanner;
public class airlineReservation 
{
    public static void main(String[] args)
    {   
        Scanner userInput = new Scanner(System.in);
        String[][] currentSeatingArray = new String[15][4];
        String[] flightWaitingList = new String[60]; //flight waiting list as big as there are seats in the flight
        boolean quitMenu = false;
        
        //Give user option to start test or create new flight plan
        System.out.print("Press T to start system test, otherwise press enter: ");
        if (userInput.nextLine().equalsIgnoreCase("t"))
            testingSystem(currentSeatingArray, flightWaitingList);

            while (!quitMenu)
            {//loop through menu until user wants to quit
            int userSelection = displayMenu();
            switch (userSelection)
            {
                case 1: System.out.println(); addPassenger(currentSeatingArray, flightWaitingList);break;
                
                case 2:System.out.println(); removePassenger(currentSeatingArray, flightWaitingList);break;

                case 3:System.out.println(); displayCurrentSeating(currentSeatingArray);break;

                case 4: quitMenu = true; System.out.println("Goodbye...");
            }//end of switch statement menu selection 
        }//end of menu While loop
    }//end of airline Reservation main method

    public static int displayMenu()
    {//displays user options then returns choice in int format
    
        Scanner userInput = new Scanner(System.in);
        int userResponse;
        
        System.out.println();
        System.out.printf("%18s\n", "MENU");
        System.out.printf("%18s\n", "----");
        System.out.println();
        System.out.println("Please select one of the following:");
        System.out.printf("%19s\n", "1. Add passenger");
        System.out.printf("%22s\n", "2. Remove passenger");
        System.out.printf("%21s\n", "3. Display seating");
        System.out.printf("%15s\n", "4. Quit Menu");

        System.out.print("Enter Value: ");
        userResponse = userInput.nextInt();

        while (userResponse <= 0 || userResponse > 4)
        {//check for correct entry
            System.out.println("ERROR: Not a valid entry... ");
            System.out.print("Please select a value from 1 - 4: ");
            userResponse = userInput.nextInt();
        }
        
        return userResponse;
    }

    public static void addPassenger(String[][] currentSeatingArray, String[] flightWaitingList)
    {//add passenger method takes in a current seating array and flight waiting list argument

        int seatsAvailable = 0;
        String colLetter = "";
        String colLetterLower;
        int colIndex = 0;
        int rowIndex = 0;

        Scanner userInput = new Scanner(System.in);
        System.out.println("You have selected add a passenger."); //verifying user choice
        String passengerName;
        System.out.print("Please enter the passenger's name: ");
        passengerName = userInput.nextLine();
        displayCurrentSeating(currentSeatingArray); //calls the display seating method
        
        for (int row = 0; row < currentSeatingArray.length; row++)
        {
            for (int col = 0; col < 4;  col++)
            {//loops through array to check for seats available
                if (currentSeatingArray[row][col] == null)
                    seatsAvailable += 1;
            }
        }
        if (seatsAvailable > 1)
        {//if there are more than one seat, allow user to choose their seat.
            System.out.println("There are " + seatsAvailable + " seats available!");
            System.out.print("Please choose a row number: ");
            rowIndex = userInput.nextInt();
            rowIndex -= 1;
            userInput.nextLine();
            System.out.print("Please choose a seat letter: ");
            colLetter = userInput.nextLine();
            colLetterLower = colLetter.toLowerCase();

            while (((rowIndex < 0) || (rowIndex > 14)) || ((!colLetterLower.equals("a")) && (!colLetterLower.equals("b")) && (!colLetterLower.equals("c")) && (!colLetterLower.equals("d"))))
            {
                System.out.println("ERROR: Not a valid entry... ");
                System.out.println("Acceptable values range from (1 - 15) and (A - D) ");
                System.out.print("Please choose a row number: ");
                rowIndex = userInput.nextInt();
                userInput.nextLine(); //skip newline character from int user input
                System.out.print("Please choose a seat letter: ");
                colLetter = userInput.nextLine();
                colLetterLower = colLetter.toLowerCase();
            }
            switch(colLetterLower)
            {//switch statement for user friendly choice
                case "a": colIndex = 0;break;
                case "b": colIndex = 1; break;
                case "c": colIndex = 2; break;
                case "d": colIndex = 3;
            }

            while (currentSeatingArray[rowIndex][colIndex] != null)
            {//Lets user know when the seat they have chosen is taken
                System.out.println(currentSeatingArray[rowIndex][colIndex]);

                System.out.println("ERROR: Seat not available...");
                System.out.print("Please choose a row number: ");
                rowIndex = userInput.nextInt();
                userInput.nextLine(); //skip newline character from int user input
                System.out.print("Please choose a seat letter: ");
                colLetter = userInput.nextLine();
                colLetterLower = colLetter.toLowerCase();
                switch(colLetterLower)
                {
                    case "a": colIndex = 0;break;
                    case "b": colIndex = 1; break;
                    case "c": colIndex = 2; break;
                    case "d": colIndex = 3;
                }
            }
            


        }//end of user seat selection if statement
        else if (seatsAvailable == 1)
        {//automatically gives user seat value 
            for (int i = 0; i < currentSeatingArray.length; i++)
                for(int j = 0; j < 4; j++)
                {
                    if (currentSeatingArray[i][j] == null)
                    {
                        rowIndex = i;
                        colIndex = j;
                    }
                }

            switch(colIndex)
            {
                case 0: colLetter = "a"; break;
                case 1: colLetter = "b"; break;
                case 2: colLetter = "c"; break;
                case 3: colLetter = "d"; 
            }

            System.out.println("Your seat is " + rowIndex + " " + colLetter);
        }// end of last seat available else if statement
        else if (seatsAvailable == 0)
        {
            System.out.println("There are no seats available at this time.");
            for (rowIndex = 0; rowIndex < 60; rowIndex++)
            {//loops through to find available waitlist position
            
                if (flightWaitingList[rowIndex] == null)
                {//assigns available position to passenger
                    flightWaitingList[rowIndex] = passengerName;
                }
            
                    
            }
            System.out.println("You will be put on the wait list until something opens up");
        }// end of else statement to put user on the waitlist
    }//end of add passenger method

    public static void removePassenger(String[][] currentSeatingArray, String[] flightWaitingList)
    {//remove passenger from either flight or waitinglist and shift up waitinglist
        Scanner userInput = new Scanner(System.in);
        String passengerName;
        int rowIndex = 0;
        int colIndex = 0;
        boolean isOnWait = false;
        
        System.out.print("What is the name of the passenger being removed? ");
        passengerName = userInput.nextLine();
        System.out.println("Searching...");
                
        for(int i = 0; i < currentSeatingArray.length; i++)
        {
            for(int j = 0; j < currentSeatingArray[i].length; j++)
            {//loops through to find user on wait list or flight plan
                if (currentSeatingArray[i][j] != null){
                    if (currentSeatingArray[i][j].equalsIgnoreCase(passengerName))
                    {
                        isOnWait = false;
                        rowIndex = i;
                        colIndex = j;
                    }
                }
            }
        }
        for (int i = 0; i < flightWaitingList.length; i++)
        {
            if (flightWaitingList[i] != null){
                if (flightWaitingList[i].equalsIgnoreCase(passengerName))
                {
                    isOnWait = true;
                    rowIndex = i;
                }
            }
        }
        if (isOnWait)
        {//if is on wait then loops throught to remove passenger and move each person up
            System.out.println("Removing [ "+ passengerName + " ] from waitlist...");
            flightWaitingList[rowIndex] = null;
            for (int i = 0; i < flightWaitingList.length - 1; i++)
            {
                if(flightWaitingList[i] == null)
                {
                    flightWaitingList[i] = flightWaitingList[i + 1];
                    flightWaitingList[i + 1] = null;
                }
            }
        }

        else if(!isOnWait)
        {//if on flight then loops through to remove passenger then put person from wait list on and move everyone up waitlist
            System.out.println("Removing [" + passengerName + "] from flight...");
            currentSeatingArray[rowIndex][colIndex] = null;
            currentSeatingArray[rowIndex][colIndex] = flightWaitingList[0];
            flightWaitingList[0] = null;
            for (int i = 0; i < flightWaitingList.length - 1; i++)
            {
                if(flightWaitingList[i] == null)
                {
                    flightWaitingList[i] = flightWaitingList[i + 1];
                    flightWaitingList[i + 1] = null;
                }
            }
        }
        System.out.println("[ " + passengerName + " ] has been removed.");

    }//end of remove passenger method

    public static void displayCurrentSeating(String[][] currentSeatingArray)
    {
        
        String[] columnLabel = {"A", "B", "C", "D"};
        System.out.printf("%25s\n", "Current Seating");
        System.out.println("1  2  3  4  5  6  7  8  9  10 11 12 13 14 15");
        for (int col = 3; col >= 0; col--)
        {
            for (int row = 0; row < currentSeatingArray.length; row++)
            {
                if (currentSeatingArray[row][col] == null)
                System.out.print("-  ");
                else
                System.out.print("X  ");
            }
            System.out.print("  " + columnLabel[col]);
            System.out.println("");
        }

    }//end of display current seating method
    
    public static void testingSystem(String[][] currentSeatingArray, String[] flightWaitingList)
    {//method tests functionality of flight program
        String[][] testSystemSeating = 
        {//provides random names to place on flight 
            {"Ann", "Tom", "Rich", "Dawson"},
            {"Elise", "Jourdan", "Mike", "Chris"},
            {"Chad", "Aiden", "Nik", "Paul"},
            {"Brooke", "Eden", "Kate", "Tina"},
            {"Erin", "Ethan", "Dennis", "Stacy"},
            {"Luca", "Luci", "James", "Candace"},
            {"Dallas", "Deana", "Kay", "Whitney"},
            {"Caelen", "Skylar", "Cricket", "Walter"},
            {"Elizabeth", "Merideth", "Robert", "John"},
            {"William", "Williamth", "Willth", "Will"},
            {"Peter", "Steve", "Sosuke", "Howl"},
            {"Neptr", "Devin", "Greg", "Marc"},
            {"Lindsey", "Derek", "Susan", "Daniel"},
            {"Ted", "Gerorge", "Princeton", "Poodle"},
            {"Mary", "Judas", "Jesus", "Tim"}
        }; 
        String[] testSystemWaiting = 
        {//provides random names to put on waiting list
            "Jacob", "Beth", "Casey", "Doe"
        };

        for(int rowIndex = 0; rowIndex < currentSeatingArray.length; rowIndex++)
        {
            for(int colIndex = 0; colIndex < currentSeatingArray[rowIndex].length; colIndex++)
            {
                currentSeatingArray[rowIndex][colIndex] = testSystemSeating[rowIndex][colIndex];
            }
        }
        for (int rowIndex = 0; rowIndex < 4; rowIndex++)
        {
            flightWaitingList[rowIndex] = testSystemWaiting[rowIndex];
        }
        System.out.println("System is ready for testing...");
        System.out.println();
        System.out.println("Here are some names to use on flight:\n\tJourdan\n\tAnn\n\tGreg\n\tDennis");
        System.out.println();
        System.out.println("Waiting list has four people: \n\tJacob\n\tBeth\n\tCasey\n\tDoe");
    }

    
}//end of airlineReservation class


/* Airline seating visualization:
 * 
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 *  [0, 0, 0, 0]
 * 
 */