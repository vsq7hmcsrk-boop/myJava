import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. 컬렉션 프레임워크(List)를 활용하여 숫자 저장
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 3, 6, 1, 2, 4));

        // 2. 오름차순(가나다 순) 정렬
        Collections.sort(numbers);

        // 3. 출력을 위한 반복문을 람다식(Lambda Expression)으로 구현
        numbers.forEach(num -> System.out.print(num + " "));
    }
}