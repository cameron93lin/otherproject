import java.text.DecimalFormat;
import java.util.Scanner;

public class bodymassindex {
    public static void main(String[] args) {
        double bmi=0;
        double weight=0;
        double height=0;
        int feet=0;
        double inch=0;
        DecimalFormat df=new DecimalFormat("#.0");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter height using feet space inches (e.g., 5 6.25):");
        boolean secondtime=false;
        while(true){
            if(secondtime){
                System.out.print("Re-enter height using feet space inches (e.g., 5 6.25):");
            }
            String input = scan.nextLine();
            String[] newinput= input.split(" ");
            boolean fb=false;
            boolean ib=false;
            try{
                feet=Integer.parseInt(newinput[0]);
                if(feet>0){
                    fb=true;
                }
                else{
                    System.out.println("Invalid feet value. Must be positive.");
                }
            }
            catch (Exception e){
                System.out.println("Invalid feet value. Must be an integer.");
            }
            try{
                inch=Double.parseDouble(newinput[1]);
                if(inch>=0){
                    ib=true;
                }
                else{
                    System.out.println("Invalid feet value. Must be positive or 0.");
                }
            }
            catch (Exception e){
                System.out.println("Invalid inches value. Must be a decimal number.");
            }
            if(fb&&ib){
                break;
            }
            else{
                secondtime=true;
            }
        }
        System.out.print("Enter weight in pounds:");

        boolean valid=true;
        boolean second=false;
        while(valid){
            try{
                if(second){
                    System.out.print("Re-Enter weight in pounds:");
                }
                String input = scan.nextLine();
                weight=Double.parseDouble(input);
                if(weight>0){
                    valid=false;
                }
                else{
                    System.out.println("Invalid pounds value. Must be positive.");
                    second=true;
                }
            }catch (Exception e){
                System.out.println("Invalid pounds value. Must be  a decimal number.");
            }
        }
        System.out.println();
        System.out.println("height = "+feet+"'-"+inch+"\"");
        System.out.println("weight = "+weight+" pounds");
        height=12*feet+inch;
        bmi=704*weight/Math.pow(height,2);
        System.out.println("body mass index = "+df.format(bmi));

    }
}
