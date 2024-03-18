import java.util.ArrayList;

// 199p 8.5 재귀 곱셈 / 200p 8.12 여덟 개의 퀸
public class Recursive {

    // 8.5 재귀 곱셈: * 연산자를 사용하지 않고 양의 정수 2개를 곱하는 재귀 함수를 작성하라.
    // 덧셈, 뺄셈, 비트 시프트 연산자를 사용할 수 있지만 사용 횟수를 최소화해야 한다.

    // 곱셈은 덧셈을 여러 번 반복하는 것임
    // 따라서 a * b = a + a + a + ... + a (b번) 을 재귀적으로 표현

    // 연산 횟수 최소화
    // 3 * 5 = 3 + 3 + 3 + 3 + 3 <- 5번 반복
    // 3 * 5 = 5 + 5 + 5 <- 3번 반복(연산 횟수 최소화)
    int i = 1; // 호출 횟수 체크용
    public int multiply(int a, int b) {
        // 연산 횟수를 최소화하기 위해 a가 b보다 작을 때는 a와 b를 바꿔준다.
        if (a < b) {
            System.out.println("a, b 바꿈");
            return multiply(b, a);
        }

        if (b == 0) {
            i = 0;
            return 0;
        }

        System.out.println(i++ + "번째 호출");
        return a + multiply(a, b - 1);
    }


    // 8.12 8퀸: 8x8 체스판에 퀸 8개를 놓았을 때 서로 공격할 수 없는 위치에 퀸을 놓는 방법을 계산하는 알고리즘을 작성하라.
    // 즉, 퀸은 서로 같은 행, 열, 대각선상에 놓이면 안 된다.
    // 여기서 대각선은 모든 대각선을 의미하는 것으로, 체스판을 양분하는 대각선 두 개로 한정하지 않는다.


    // 서로 같은 행, 열, 대각선상에 놓이지 않으려면, 각 행, 열, 대각선은 딱 1번만 사용되어야 함
    // 즉, 첫 번째 행에 반드시 1개의 퀸이 있어야 함.
    // 첫 번째 행의 퀸의 위치(열)을 0 ~ 7까지 바꿔가며 모든 경우의 수를 확인하며 계산
    public void placeQueens(int row, Integer[] columns, ArrayList<Integer[]> results) {
        if (row == 8) {
            results.add(columns.clone());
        } else {
            for (int col = 0; col < 8; col++) {
                if (checkValid(columns, row, col)) {
                    columns[row] = col; // (row, col) 위치에 퀸을 놓음
                    placeQueens(row + 1, columns, results);
                }
            }
        }
    }

    // [row1, column1] 에 퀸을 놓을 수 있을지 체크
    public boolean checkValid(Integer[] columns, int row1, int column1) {
        for (int row2 = 0; row2 < row1; row2++) {
            int column2 = columns[row2];

            // 같은 열에 있는지 체크
            if (column1 == column2) {
                return false;
            }

            // 대각선상에 있는지 체크, 열 차이와 행 차이가 같으면 대각선상에 있는 것
            int columnDistance = java.lang.Math.abs(column2 - column1);
            int rowDistance = row1 - row2;
            if (columnDistance == rowDistance) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 8.5 재귀 곱셈
        Recursive recursive = new Recursive();
        System.out.println(recursive.multiply(3, 5));
        System.out.println(recursive.multiply(5, 3));


        // 8.12 8퀸
        ArrayList<Integer[]> results = new ArrayList<>();
        Integer[] columns = new Integer[8];
        recursive.placeQueens(0, columns, results);
        int k = 1;
        for (Integer[] result : results) {
            System.out.println(k++ + "번째 결과");
            for (int i = 0; i < result.length; i++) {
                System.out.println(i + ", " + result[i]);
            }
            System.out.println();
        }
    }
}
