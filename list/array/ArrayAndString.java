package list.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayAndString {

    // 1.1 문자열 중복이 없는지 확인하기
    // 문자열에 같은 문자가 중복되어 등장하는지 확인하는 알고리즘을 구현하라.
    // 자료구조를 추가로 사용하지 않고 풀 수 있는 알고리즘 또한 고민하라.

    // 자료구조를 사용하는 경우
    // Set 자료구조를 활용하면 중복된 문자가 있는지 확인 가능
    public Boolean isUnique(String str) {
        Set<Character> set = new HashSet<>();
        for (char c : str.toCharArray()) {
            if (set.contains(c)) return false;
            set.add(c);
        }
        return true;
     }


     // 자료구조를 사용하지 않는 경우
    // 문자열을 정렬한 후, 인접한 문자가 같은지 확인
    public Boolean isUnique2(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i+1]) return false;
        }
        return true;
    }


    // 1.2 문자열 순열 확인하기
    // 문자열 두 개가 주어졌을 때 이 둘이 서로 순열 관계에 있는지 확인하는 메서드를 작성하라.
    // 순열 관계란 두 문자열이 알파벳의 구성은 같지만 그 순서가 다른 경우를 말한다.
    // 예를 들어 "abcd"와 "dabc"는 서로 순열 관계다.

    // 1) 정렬하여 비교하기
    public Boolean isPermutation1(String str1, String str2) {
        if (str1.length() != str2.length()) return false;

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        Arrays.sort(chars1);
        Arrays.sort(chars2);

        return Arrays.equals(chars1, chars2);
    }

    // 2) 각 문자열의 문자 개수를 비교하기
    public Boolean isPermutation2(String str1, String str2) {
        if (str1.length() != str2.length()) return false;

        int[] letters = new int[128];
        for (char c : str1.toCharArray()) {
            letters[c]++;
        }

        for (int i = 0; i < str2.length(); i++) {
            int c = str2.charAt(i);
            letters[c]--;
            if (letters[c] < 0) return false;
        }

        return true;
    }

    // 1.3 URL화
    // 문자열에 들어 있는 모든 공백을 '%20'으로 바꿔주는 메서드
    public static String encode(String str, int size) {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            if (str.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }


    // 1.4 회문 순열(Palindrome Permutation)
    // 주어진 문자열이 회문의 순열인지 아닌지 확인하는 함수
    // 문자열의 길이가 짝수일 때 각 문자의 개수가 짝수개여야 하고, 홀수일 때는 한 문자만 홀수개여야 한다.
    // 고로 홀수 문자의 개수가 2개 이상이면 회문의 순열이 될 수 없다.
    // 대소문자는 구분하지 않으며, 공백은 무시한다.
    public static Boolean isPalindrome(String str) {
        int[] letters = new int[128];
        int oddCount = 0;
        str = str.toLowerCase(); // 모든 문자를 소문자로 변환
        for (char c : str.toCharArray()) {
            if (c == ' ') continue;
            letters[c]++;
            if (letters[c] % 2 == 1) {
                oddCount++;
            } else {
                oddCount--;
            }
        }
        return oddCount <= 1;
    }

    // 1.5 하나 빼기
    // 문자열을 편집하는 방법 세 가지에 대해 논한다.
    // 문자 삽입, 문자 삭제, 문자 교체
    // 문자열 두 개가 주어졌을 때, 문자열을 같게 만들기 위한 편집 횟수가 1회 이내인지 확인하는 함수
    // 문자열의 길이가 같을 때는 문자 교체, 길이가 1만큼 차이가 날 때는 문자 삽입 또는 문자 삭제
    public static Boolean isOneEdit(String str1, String str2) {
        if (str1.length() == str2.length()) {
            return isOneReplace(str1, str2);
        } else if (str1.length() + 1 == str2.length()) {
            return isOneInsert(str1, str2);
        } else if (str1.length() - 1 == str2.length()) {
            return isOneInsert(str2, str1);
        }
        return false;
    }

    // 문자열 길이가 같을 때 : 문자 교체, 2개 이상의 문자가 다르면 false
    public static Boolean isOneReplace(String str1, String str2) {
        boolean foundDifference = false;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) { // 문자가 다른 경우
                if (foundDifference) { // 이미 다른 문자가 나온 적이 있는 경우
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }

    // 문자열 길이가 1만큼 차이가 날 때
    public static Boolean isOneInsert(String shorter, String longer) {
        int shorterStrIdx = 0;
        int longerStrIdx = 0;
        while (shorterStrIdx < shorter.length() && longerStrIdx < longer.length()) { // 두 문자열의 길이가 같아질 때까지 반복
            if (shorter.charAt(shorterStrIdx) != longer.charAt(longerStrIdx)) { // 문자가 다른 경우
                if (shorterStrIdx != longerStrIdx) { // 이미 다른 문자가 나온 적이 있는 경우
                    return false;
                }
                longerStrIdx++; // 문자열 길이가 긴 문자열의 인덱스만 증가
            } else {
                shorterStrIdx++;
                longerStrIdx++;
            }
        }
        return true;
    }

    // 1.6 문자열 압축
    // 반복되는 문자의 개수를 세는 방식의 기본적인 문자열 압축 메서드를 작성하라.
    public static String compress(String str) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count++;
            if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i+1)) {
                sb.append(str.charAt(i));
                sb.append(count);
                count = 0;
            }
        }

        return sb.length() < str.length() ? sb.toString() : str;
    }

    // 1.7 행렬 회전
    // 이미지를 표현하는 N x N 행렬이 있다.
    // 이미지의 각 픽셀은 4바이트로 표현된다.
    // 이때, 이미지를 90도 회전시키는 메서드를 작성하라.
    // 부가적인 행렬을 사용하지 않고서도 할 수 있겠는가?
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) { // 행렬을 회전시키는 데 필요한 반복 횟수
            int first = layer; // 회전시킬 행렬의 첫 번째 행
            int last = n - 1 - layer; // 회전시킬 행렬의 마지막 행
            for (int i = first; i < last; i++) { // 회전시킬 행렬의 첫 번째 행부터 마지막 행까지 반복
                int offset = i - first; // 회전시킬 행렬의 첫 번째 행부터 현재 행까지의 거리
                int top = matrix[first][i]; // 회전시킬 행렬의 첫 번째 행의 현재 열의 값
                matrix[first][i] = matrix[last - offset][first]; // 회전시킬 행렬의 첫 번째 행의 현재 열의 값을 회전시킬 행렬의 마지막 행의 현재 열의 값으로 변경
                matrix[last - offset][first] = matrix[last][last - offset]; // 회전시킬 행렬의 마지막 행의 현재 열의 값을 회전시킬 행렬의 마지막 행의 마지막 열의 값으로 변경
                matrix[last][last - offset] = matrix[i][last]; // 회전시킬 행렬의 마지막 행의 마지막 열의 값을 회전시킬 행렬의 현재 행의 마지막 열의 값으로 변경
                matrix[i][last] = top; // 회전시킬 행렬의 현재 행의 마지막 열의 값을 회전시킬 행렬의 첫 번째 행의 현재 열의 값으로 변경
            }
        }
    }

    // 1.8 0 행렬
    // M x N 행렬의 한 원소가 0일 경우, 해당 원소가 속한 행과 열의 모든 원소를 0으로 설정하는 알고리즘을 작성하라.
    public static void setZeros(int[][] matrix) {
        boolean[] row = new boolean[matrix.length]; // 행의 원소가 0인지 아닌지를 저장하는 배열
        boolean[] column = new boolean[matrix[0].length]; // 열의 원소가 0인지 아닌지를 저장하는 배열

        // 행렬의 각 원소가 0인지 아닌지를 확인하여 row와 column 배열에 저장
        for (int i = 0; i < matrix.length; i++) { // 행
            for (int j = 0; j < matrix[0].length; j++) { // 열
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    column[j] = true;
                }
            }
        }

        // 행렬의 각 원소가 0인지 아닌지를 확인하여 0인 경우 해당 행과 열의 모든 원소를 0으로 설정
        for (int i = 0; i < matrix.length; i++) { // 행
            for (int j = 0; j < matrix[0].length; j++) { // 열
                if (row[i] || column[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // 1.9 문자열 회전
    // 한 단어가 다른 문자열에 포함되어 있는지 판별하는 isSubstring이라는 메서드가 있다고 하자.
    // s1과 s2의 두 문자열이 주어졌을 때, s2가 s1을 회전시킨 결과인지 판별하는 코드를 isSubstring을 한 번만 호출하도록 하여 작성하라.
    public static Boolean isRotation(String s1, String s2) {
        int len = s1.length();
        if (len == s2.length() && len > 0) {
            String s1s1 = s1 + s1; // s1을 두 번 이어 붙인 문자열
            return isSubstring(s1s1, s2);
        }
        return false;
    }

    // 문자열 s2가 문자열 s1에 포함되어 있는지 확인하는 메서드
    public static Boolean isSubstring(String s1, String s2) {
        return s1.contains(s2);
    }


    public static void main(String[] args) {
        String str = "Mr John Smith";
        int size = 13;
        System.out.println(encode(str, size));

        System.out.println("===============================");

        System.out.println(isPalindrome("Tact Coa"));

        System.out.println("===============================");

        System.out.println(isOneEdit("pale", "ple"));
        System.out.println(isOneEdit("pales", "pale"));
        System.out.println(isOneEdit("pale", "bale"));
        System.out.println(isOneEdit("pale", "bake"));

        System.out.println("===============================");
        System.out.println(compress("aabcccccaaa"));

        System.out.println("===============================");
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        rotate(matrix);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("===============================");
        int[][] matrix2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        setZeros(matrix2);
        for (int[] row : matrix2) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("===============================");
        System.out.println(isRotation("waterbottle", "erbottlewat"));
    }
}
