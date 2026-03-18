public class code02_09 {
    public static void main(String[] args) {
        int number1 = 10, number2 = 20;
        int resAdd;
        int resSub;
        int resMul;
        double resDiv;

        resAdd = number1 + number2;
        resSub = number1 - number2;
        resMul = number1 = number2;
        resDiv = (double) number1 / number2;

        System.out.println(number1 + "+" + number2 + "=" + resAdd);
        System.out.println(number1 + "-" + number2 + "=" + resSub);
        System.out.println(number1 + "*" + number2 + "=" + resMul);
        System.out.println(number1 + "/" + number2 + "=" + resDiv);

    }
}
