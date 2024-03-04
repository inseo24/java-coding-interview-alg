public class Bit {

    // 5.1 삽입: 두 개의 32비트 수 N과 M이 주어지고, 비트 위치 i와 j가 주어졌을 때, M을 N에 삽입하는 메서드를 구현하라.
    // 예) N = 10000000000, M = 10011, i = 2, j = 6
    // 출력: N = 10001001100
    public int insert(int N, int M, int i, int j) {
        int allOnes = ~0;

        int left = allOnes << (j + 1);
        int right = (1 << i) - 1;
        int mask = left | right;

        int nCleared = N & mask;
        int mShifted = M << i;

        return nCleared | mShifted;
    }

    // 5.2 2진수를 문자열로: 0.72 와 같은 실수가 [0, 1] 범위 안에 있다고 가정했을 때, 그 값을 2진수 형태로 출력
    // 32자리 이내에 표현할 수 없다면 "ERROR"를 출력하는 코드를 작성하라.
    public String binaryToString(double num) {
        if (num >= 1 || num <= 0) {
            return "ERROR";
        }

        StringBuilder binary = new StringBuilder();
        binary.append(".");

        while (num > 0) {
            if (binary.length() >= 32) {
                return "ERROR";
            }

            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            } else {
                binary.append(0);
                num = r;
            }
        }

        return binary.toString();
    }

    // 5.3 비트 뒤집기: 어떤 정수가 주어졌을 때, 이 정수의 비트를 뒤집는 메서드를 작성하라.
    // 이때 1이 연속으로 나올 수 있는 가장 긴 길이를 구하는 코드를 작성하라.
    // 0을 1로 바꾸는 것을 한 번 허용한다고 가정한다.
    public int flipBit(int num) {
        if (~num == 0) {
            return Integer.BYTES * 8;
        }

        int currentLength = 0;
        int previousLength = 0;
        int maxLength = 1;

        while (num != 0) {
            if ((num & 1) == 1) {
                currentLength++;
            } else if ((num & 1) == 0) {
                previousLength = (num & 2) == 0 ? 0 : currentLength;
                currentLength = 0;
            }

            maxLength = Math.max(previousLength + currentLength + 1, maxLength);
            num >>>= 1;
        }

        return maxLength;
    }

    // 5.4 다음 숫자: 양의 정수가 하나 주어졌다. 이 숫자를 2진수로 표기했을 때 1비트의 개수가 같은 수 중에서 가장 작은 수와 가장 큰 수를 구하라.
    public int[] nextNumber(int n) {
        int[] result = new int[2];
        result[0] = getNext(n);
        result[1] = getPrev(n);
        return result;
    }

    private int getNext(int n) {
        int c = n;
        int c0 = 0;
        int c1 = 0;

        while (((c & 1) == 0) && (c != 0)) {
            c0++;
            c >>= 1;
        }

        while ((c & 1) == 1) {
            c1++;
            c >>= 1;
        }

        if (c0 + c1 == 31 || c0 + c1 == 0) {
            return -1;
        }

        int p = c0 + c1;
        n |= (1 << p);
        n &= ~((1 << p) - 1);
        n |= (1 << (c1 - 1)) - 1;

        return n;
    }

    private int getPrev(int n) {
        int temp = n;
        int c0 = 0;
        int c1 = 0;

        while ((temp & 1) == 1) {
            c1++;
            temp >>= 1;
        }

        if (temp == 0) {
            return -1;
        }

        while (((temp & 1) == 0) && (temp != 0)) {
            c0++;
            temp >>= 1;
        }

        int p = c0 + c1;
        n &= ((~0) << (p + 1));
        int mask = (1 << (c1 + 1)) - 1;
        n |= mask << (c0 - 1);

        return n;
    }


    // 5.5 디버거: 다음 코드가 하는 일을 설명하라.
    // ((n & (n - 1)) == 0)
    // n이 2의 거듭제곱인지 0인지 확인하는 코드


    // 5.6 변환: 정수 A와 B를 비트로 표현했을 때, A를 B로 바꾸기 위해 뒤바꿔야 하는 비트의 개수를 구하는 메서드를 작성하라.
    public int bitSwapRequired(int a, int b) {
        int count = 0;
        for (int c = a ^ b; c != 0; c = c & (c - 1)) {
            count++;
        }
        return count;
    }

    // 5.7 쌍끼리 맞바꾸기: 명령어를 가능한 적게 사용해서 주어진 정수의 짝수 번째 비트의 값과 홀수 번째 비트의 값을 바꾸는 코드를 작성하라.
    public int exchangeBits(int x) {
        return ((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1);
    }

    // 5.8 선 그리기: 흑백 모니터 화면은 하나의 바이트 배열에 저장되는데, 인접한 픽셀 8개를 하나의 바이트로 묶어서 저장한다.
    // 화면의 너비 w는 8의 배수라고 가정했을 때, (x1, y)에서 (x2, y)까지의 수평선을 그리는 메서드를 구현하라.
    public void drawLine(byte[] screen, int width, int x1, int x2, int y) {
        int start_offset = x1 % 8;
        int first_full_byte = x1 / 8;
        if (start_offset != 0) {
            first_full_byte++;
        }

        int end_offset = x2 % 8;
        int last_full_byte = x2 / 8;
        if (end_offset != 7) {
            last_full_byte--;
        }

        for (int b = first_full_byte; b <= last_full_byte; b++) {
            screen[(width / 8) * y + b] = (byte) 0xFF;
        }

        byte start_mask = (byte) (0xFF >> start_offset);
        byte end_mask = (byte) ~(0xFF >> (end_offset + 1));

        if ((x1 / 8) == (x2 / 8)) {
            byte mask = (byte) (start_mask & end_mask);
            screen[(width / 8) * y + (x1 / 8)] |= mask;
        } else {
            if (start_offset != 0) {
                int byte_number = (width / 8) * y + first_full_byte - 1;
                screen[byte_number] |= start_mask;
            }
            if (end_offset != 7) {
                int byte_number = (width / 8) * y + last_full_byte + 1;
                screen[byte_number] |= end_mask;
            }
        }
    }

}
