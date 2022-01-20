import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    // n个整数，找出其中前 k 个大个数，说明算法复杂度
    public List<Integer> getBigNumbers(List<Integer> numbers, int k) {
        List<Integer> result = new ArrayList<>();
        boolean[] selected = new boolean[numbers.size()];
        for (int i = 0; i < k; i++) {
            int index = -1;
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < numbers.size(); j++) {
                if (selected[j]) {
                    continue;
                }
                int temp = numbers.get(j);
                if (numbers.get(j) > max) {
                    max = temp;
                    index = j;
                }
            }
            result.add(max);
            selected[index] = true;
        }
        return result;
    }

    public List<Integer> getBigNumbers(List<Integer> numbers, int k) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            for (int j = 0, n = numbers.size() - 1; j < n; j++) {
                int number1 = numbers.get(j);
                int number2 = numbers.get(j + 1);
                if (number1 > number2) {
                    numbers.set(j, number2);
                    numbers.set(j + 1, number1);
                }
            }
        }
        return numbers.subList(numbers.size() - k, numbers.size());
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Integer[] numbers = new Integer[]{1, 8, 4, 6, 9, 10, 20, 20, 15, 7, 15};
        List<Integer> bigNumbers = solution.getBigNumbers(Arrays.asList(numbers), 0);
        System.out.println(bigNumbers);
    }

}
