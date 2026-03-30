import java.util.Scanner;

public class Exam01 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String a = s.nextLine();
        String b = s.nextLine();

        System.out.println((a + b).toLowerCase().replaceAll(" ", ""));

        s.close();
    }
}
