/*
Jose Urbina
Java Project Animal Race
Oct 27, 2023
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        System.out.println("---------WELCOME TO CHAMPION RACING---------");

        // Array to hold passed chars
        char[] chars = character_selection();

        // Displaying the Race
        System.out.println("BANG!! \nTHE RACE BEGINS");

        System.out.println();
        raceway(chars);

    }

    // Method for Character Selection
    public static char[] character_selection(){

        // Character arrays
        String [] char_list = {"Wolf", "Rabbit", "Turtle", "Jaguar", "Cat"};
        char [] characters = new char[3];

        Scanner input = new Scanner(System.in);

        // Output for character list
        System.out.println("\n--CHAMPION ROSTER--\n");
        for (String c: char_list){
            System.out.println(c);
        }

        // Input to select the 3 characters
        System.out.println();
        System.out.println("CHOOSE YOUR 3 CHAMPIONS: ");

        for (int i = 0; i < characters.length; i++){

            System.out.print("CHAMPION " + (i+1) + ": " );
            String user_input = input.nextLine();
            System.out.println();

            if (!user_input.isEmpty()){
                characters[i] = user_input.charAt(0);
            } else {
                System.out.println("Champion selection invalid, enter again: ");
                i--;
            }

            // Validate user entered the correct names
            switch(user_input){

                case "Wolf":
                    break;
                case "Rabbit":
                    break;
                case "Turtle":
                    break;
                case "Jaguar":
                    break;
                case "Cat":
                    break;
                default:
                    System.out.println("Invalid Champion name, try again");
                    i--;
                    break;

            }

        }
        return characters;
    }

    public static void raceway(char[] chars){

        // Array for the racetrack
        char[] racetrack = new char[100];
        char[] clash_chars = new char[2];
        int[] clash_position = new int[2];
        int[] num = new int[chars.length];
        int[] champ_position = {0, 0, 0};

        // Random number for champion position
        Random rand = new Random();

        // Variables
        boolean finished = false;
        int Winner_num = -1, counter = 0;
        char winner = ' ';

        // Array method call from website, fills the array with '_'
        Arrays.fill(racetrack, '_');

        // Loop for the race
        while (!finished){

            // generate a random number and add the chars_position to the racetrack
            for (int i = 0; i < chars.length; i++){

                num[i] = rand.nextInt(10)+1;

                // If statement for the champ position
                if (num[i] >= 1 && num[i] <= 5) {
                    counter = 3;
                    champ_position[i] += counter;
                } else if (num[i] >= 6 && num[i] <= 8) {
                    counter = 1;
                    champ_position[i] += counter;
                } else if (num[i] >= 9 && num[i] <= 10) {
                    counter = 5;
                    champ_position[i] -= counter;
                }

                // if position is negative reset to 0
                if (champ_position[i] < 0) {
                    champ_position[i] = 1;
                }
                if (champ_position[i] >= 99){
                    Winner_num = i;
                    break;
                }

                racetrack[champ_position[i]] = chars[i];

            }

            // Separate every iteration of the race
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }

            // Printing out the track
            for (char c : racetrack) {
                System.out.print(c);
            }
            System.out.println();

            //check and see if any champ_positions are the same
            for (int i = 0; i < champ_position.length - 1; i++){

                for(int j = i + 1; j < champ_position.length; j++){

                    if (champ_position[i] == champ_position[j] && champ_position[i] == 0){
                        break;
                    } else if (champ_position[i] == champ_position[j]){

                        clash_position[0] = champ_position[i];
                        clash_chars[0] = chars[i];

                        clash_position[1] = champ_position[j];
                        clash_chars[1] = chars[j];

                        minigame(clash_chars, clash_position, counter);

                        champ_position[i] = clash_position[0];
                        champ_position[j] = clash_position[1];

                    }
                }
            }

            // Getting the winner and setting finished to true
            if (Winner_num != -1){
                finished = true;
                winner = chars[Winner_num];
            }

            // Resetting the array before next iteration
            Arrays.fill(racetrack, '_');

            // slow down race output
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // output winner
        Winner(winner);

    }

    // Determine the winner
    public static void Winner(char winner){

        switch(winner){
            case 'W':
                System.out.println("\nTHE RACE HAS FINISHED!! \nAND THE WINNER IS: Wolf");
                break;

            case 'R':
                System.out.println("\nTHE RACE HAS FINISHED!! \nAND THE WINNER IS: Rabbit");
                break;

            case 'T':
                System.out.println("\nTHE RACE HAS FINISHED!! \nAND THE WINNER IS: Turtle");
                break;

            case 'J':
                System.out.println("\nTHE RACE HAS FINISHED!! \nAND THE WINNER IS: Jaguar");
                break;

            case 'C':
                System.out.println("\nTHE RACE HAS FINISHED!! \nAND THE WINNER IS: Cat");
                break;
        }
    }

    // Champion Special Abilities and mini game
    public static void minigame(char[] clash_chars, int[] clash_position, int counter){

        // random number dice roll
        Random dice = new Random();
        int num1 = dice.nextInt(6)+1;
        int num2 = dice.nextInt(6)+1;

        // Announcing two champions in the same spot
        System.out.println("-----A CLASH HAS OCCURRED-----\n");
        System.out.println(clash_chars[0] + " vs " + clash_chars[1] + "\n");
        System.out.println(clash_chars[0] + " rolls a " + num1);
        System.out.println(clash_chars[1] + " rolls a " + num2);

        // boost champion up further or go back
        if (num1 > num2){

            // champion 1
            counter = 6;
            clash_position[0] += counter;

            // champion 2
            counter = 8;
            clash_position[1] -= counter;

            System.out.println(clash_chars[0] + " has won! +6 tiles boost");
            System.out.println(clash_chars[1] + " has lost... -8 tiles");

        } else if (num2 > num1){

            // champion 1
            counter = 8;
            clash_position[0] -= counter;

            // champion 2
            counter = 6;
            clash_position[1] += counter;

            System.out.println(clash_chars[1] + " has won! +6 tiles boost");
            System.out.println(clash_chars[0] + " has lost... -8 tiles");

        } else {
            System.out.println("DRAW... \nboth Champions -3 tiles");

            // both -3
            counter = 3;
            clash_position[0] -= counter;
            clash_position[1] -= counter;
        }

        // slow down output
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}