import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SlotMachine {
    public static int M=0;
    public static int totalcharge=0;
    public static int bet=0;
    public static String[] pieces = {"BELL","GRAPE","CHERRY"};
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("Please enter the number of coins you want to put into the Slot Machine:");
            try {
                M = scan.nextInt();
                if (M > 0) {
                    break;
                } else {
                    System.out.println("Please enter positive number!");
                }
            } catch (Exception e) {
                System.out.println("Please enter the valid number of coins and try again!!");
            }
        }
        totalcharge=25*M;
        while (true) {
            System.out.println("----------------------");
            System.out.println("Game start!");
            System.out.print("Please enter the number of coins you want to bet in this game (1 to 4 coins per game. Enter 0 if you want to stop playing!):");

                try {
                    bet = scan.nextInt();
                    if (bet > 0 && bet < 5 && M >= bet) {
                        M = M - bet;
                        Random ra = new Random();
                        String first = pieces[ra.nextInt(2)];
                        String second = pieces[ra.nextInt(2)];
                        String third = pieces[ra.nextInt(2)];
                        List<String> result = new ArrayList<>();
                        result.add(first);
                        result.add(second);
                        result.add(third);
                        int payoff = 0;
                        if (first.equals(second) && first.equals(third)) {
                            if (first.equals("BELL")) {
                                payoff = 10;
                            } else if (first.equals("GRAPES")) {
                                payoff = 7;
                            } else {
                                payoff = 5;
                            }
                        } else if ((first.equals(second) && first.equals("CHERRY")) &&
                                (second.equals(third) && second.equals("CHERRY")) &&
                                (third.equals(second) && third.equals("CHERRY"))) {
                            payoff = 3;
                        } else if (first.equals("CHERRY") || second.equals("CHERRY") || third.equals("CHERRY")) {
                            payoff = 1;
                        }
                        System.out.println("Result: " + first + " " + second + " " + third);
                        System.out.println("Payoff rate is " + payoff + " times");
                        System.out.println("Here is your payoff: " + bet * payoff + " coins");
                        M = M + bet * payoff;
                        System.out.println("Remaining coin is " + M + " coins");
                        if(M==0){
                            System.out.println("You dont have enough coin to bet!");
                            System.out.println("Thank you for playing! Your change is " + M + " coins");
                            break;
                        }
                    } else if (M < bet) {
                        System.out.println("You dont have enough coin to bet.");
                    } else if (bet == 0) {
                        System.out.println("Thank you for playing! Your change is " + M + " coins");
                        break;
                    } else {
                        System.out.println("Please enter a number between 0 to 4 and try again!!");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter the valid number of coins and try again!!");
                }

        }
    }
}
