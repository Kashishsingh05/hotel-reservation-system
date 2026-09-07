import java.util.*;
public class ExceptionDemo {
    public static void main(String[]args){
        /*Scanner sc = new Scanner(System.in);
        int dividend = sc.nextInt();
        int divisor = sc.nextInt();
        try {
            int result = dividend / divisor;

            System.out.println("Your answer is:" + result);
        }catch(ArithmeticException e){
            System.out.println(e.getMessage() +" Divisor cannot be zero!!");
        } */

        int arr[] = new int[5];
        try{
            //arr[6] = 10;
            arr[6]= 10/0;
        }catch(ArithmeticException e){
            System.out.println(e.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        //catch(ArithmeticException | ArrayIndexOutOfBoundsException e){ ---> both catch condition in one statement
    }

    //Throw and throws Exception

    /*public static void divisionDemo(int dividend,int divisor) throws ArithmeticException{
        System.out.println("The result is:" +dividend/divisor );
    }
    public static void main(String [] args){
        divisionDemo(10,0);
     }*/
}
