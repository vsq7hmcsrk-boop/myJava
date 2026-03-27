import java.util.Scanner;
public class code03_10java {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int score;

        System.out.print("필기시험 점수를 입력하세요: ");
        score = s.nextInt();
        System.out.println(score >= 70);

        s.close();
    }
}
