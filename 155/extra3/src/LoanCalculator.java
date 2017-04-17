import java.text.DecimalFormat;
import java.util.Scanner;

public class LoanCalculator {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        double percent=0;
        double loan=0;
        int period=0;
        double monthly=0;
        double unpaid=0;
        while(true) {
            System.out.print("Please enter loan amount:");
            try {
                loan = scan.nextDouble();
                if (loan > 0) {
                    break;
                } else {
                    System.out.println("Please enter positive number!");
                }
            } catch (Exception e) {
                System.out.println("Please enter the valid number and try again!!");
            }
        }
        while(true) {
            System.out.print("Please enter annual interest rate(enter the rate before %.For example, enter 12 for 12% annual interest rate):");
            try {
                percent = scan.nextDouble();
                if (percent > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number!");
                }
            } catch (Exception e) {
                System.out.println("Please enter the valid number and try again!!");
            }
        }
        while(true) {
            System.out.print("Please enter loan period (number in years):");
            try {
                period  = scan.nextInt();
                if (period > 0) {
                    break;
                } else {
                    System.out.println("Please enter positive number!");
                }
            } catch (Exception e) {
                System.out.println("Please enter the valid number and try again!!");
            }
        }
        DecimalFormat df=new DecimalFormat(",###,###.00");
        percent=percent/12/100;
        monthly=loan*percent*Math.pow((1+percent),period*12)/(Math.pow(1+percent,period*12)-1);
        System.out.printf("%7s %9s %8s %14s %22s\n","Payment","Principal","Interest","Unpaid Balance","Total Interest to Date");
        unpaid=loan;
        double princ=0;
        double interest=0;
        double totalinterest=0;
        double totalamount=0;
        for(int i=0;i<period*12;i++){
            interest=unpaid*percent;
            princ=monthly-interest;
            unpaid=unpaid-princ;
            totalinterest=totalinterest+interest;
            System.out.printf("%7d %9s %8s %14s %22s\n",i+1,df.format(princ),df.format(interest),df.format(unpaid),df.format(totalinterest));
        }
        totalamount=totalinterest+loan;
        System.out.println("Total amount paid = "+df.format(totalamount));

    }
}
