import java.util.Scanner;

public class bodymassindex {
    public static void main(String[] args) {
        double bmi=0;
        double weight=0;
        double height=0;
        int feet=0;
        double inch=0;
        Scanner scan = new Scanner(System.in);
        bmi=704*weight/Math.pow(height,2);
        while(true){
            System.out.print("Enter height using feet space inches (e.g., 5 6.25):");
            String input = scan.nextLine();
            String[] newinput= input.split(" ");
            boolean fb=false;
            boolean ib=false;
            try{
                feet=Integer.parseInt(newinput[0]);
                if(feet>0){
                    break;
                }
                else{
                    System.out.println("Invalid feet value. Must be positive.");
                }
            }
            catch (Exception e){
                System.out.println("Please enter a valid number(only integer will be accepted) and try again!");
            }
            try{
                inch=Double.parseDouble(newinput[1]);
                if(inch>0){
                    break;
                }
                else{
                    System.out.println("Please enter a positive number and try again!");
                }
            }
            catch (Exception e){
                System.out.println("Please enter a valid number(only integer will be accepted) and try again!");
            }
        }
    }
}
