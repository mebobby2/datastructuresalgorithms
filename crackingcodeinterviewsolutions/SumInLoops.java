import java.util.Scanner;

public class SumInLoops {
    public static void main(String[] args) {
        int n;
        String pair;
        
        Scanner scan = new Scanner(System.in);
        
        n = scan.nextInt();

        System.out.println("n = "n);
        
        int[] results = new int[n];
        
        for (int i = 0; i < n; i++) {
            pair = scan.next();
            System.out.println("pair = "pair);
            String first = pair.split(" ")[0];
            String last = pair.split(" ")[1];
            
            results[i] = Integer.parseInt(first) + Integer.parseInt(last);
        }
        
        System.out.println(results);
    }
}