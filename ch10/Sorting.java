import java.io.*;
import java.util.Scanner;

public class Sorting {

    // 10.1 정렬된 병합: 정렬된 배열 A와 B가 주어진다. A의 끝에는 B를 수용할 만큼의 충분한 여유 공간이 있다.
    // 정렬된 배열 A와 B를 정렬된 상태로 병합하는 메서드를 작성하라.

    // A와 B 모두 정렬된 배열이기 때문에, 두 인덱스를 합한 값의 배열 뒷쪽부터 채워나간다.
    // A와 B의 뒷쪽 값에서 큰 값을 선택해 뒷쪽부터 채운다.
    public void merge(int[] a, int[] b, int lastA, int lastB) {
        // 인덱스는 0부터 시작하기 때문에 -1을 해준다.
        int indexA = lastA - 1;
        int indexB = lastB - 1;
        int indexMerged = lastB + lastA - 1;

        // A와 B의 뒷쪽 값에서 큰 값을 선택해 뒷쪽부터 채운다.
        // B 배열의 원소를 모두 A 배열에 merge 되어야 하므로 B 배열의 원소가 모두 merge 될 때까지 반복
        while (indexB >= 0) {
            if (indexA >= 0 && a[indexA] > b[indexB]) {
                a[indexMerged] = a[indexA];
                indexA--;
            } else {
                a[indexMerged] = b[indexB];
                indexB--;
            }
            indexMerged--;
        }
    }


    // 10.7 빠트린 정수: 음이 아닌 정수 40억개로 이뤄진 파일이 있다. 이 파일에 없는 정수를 생성하는 알고리즘을 작성하라.
    // 단, 사용할 수 있는 메모리는 1GB뿐이다.

    long numberOfInts = ((long) Integer.MAX_VALUE) + 1;
    byte[] bitfield = new byte[(int) (numberOfInts / 8)];
    String filename = "test.txt";

    void findOpenNumber() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(filename));
        while (in.hasNextInt()) {
            int n = in.nextInt();
            // n % 8 : 각 바이트는 8개의 비트를 갖기 때문에 8로 나눠 바이트의 인덱스를 나타냄
            // 10은 두 번째 인덱스의 2번째 비트의 위치가 n이 된다.

            // 1 << (n % 8) : 1을 n % 8만큼 왼쪽으로 이동시킴
            // n이 10일 때, 1 << 2 = 100(2) = 4 가 된다. 즉, 3번째 비트만 1이 되고 나머지는 0인 비트 마스크

            // |= : OR 연산자, 해당 인덱스에 1을 할당
            bitfield[n / 8] |= 1 << (n % 8);
        }

        for (int i = 0; i < bitfield.length; i++) {
            for (int j = 0; j < 8; j++) {
                // 비트가 0인 수를 출력
                // 1 << j : j번째 비트만 1인 비트 마스크
                if ((bitfield[i] & (1 << j)) == 0) {
                    System.out.println(i * 8 + j); // i * 8은 바이트의 인덱스, j는 비트의 인덱스
                    return;
                }
            }
        }
    }

    // 만약 메모리가 10MB라면?
    // 메모리를 분할하고 범위 내에서 찾기 <- 전체 데이터 세트를 메모리에 한 번에 올릴 수 없으므로, 쪼개서 차례대로 처리
    int findOpenNumber(String filename) throws FileNotFoundException {
        // 각 블록의 크기
        int rangeSize = 1 << 20; // 2^20 bits

        // 각 블록에 실제로 몇 개의 정수가 있는지 나타냄
        int[] blocks = getCountPerBlock(filename, rangeSize);

        // 빠진 숫자가 있는 블록을 찾기
        int blockIndex = findBlockWithMissing(blocks, rangeSize);
        if (blockIndex == -1) return -1;

        // 해당 영역에서 사용될 비트 벡터 생성
        byte[] bitfield = getBitfieldForRange(filename, blockIndex, rangeSize);

        // 빠진 숫자를 찾기(비트 벡터에서 0인 위치 찾기)
        int offset = findZero(bitfield);
        if (offset == -1) return -1;

        // 빠진 숫자 계산
        return blockIndex * rangeSize + offset;
    }

    // 각 블록에 포함된 정수 개수를 세고 blocks 배열에 그 카운트 값을 저장
    int[] getCountPerBlock(String filename, int rangeSize) throws FileNotFoundException {
        // 전체 블록의 개수
        int arraySize = Integer.MAX_VALUE / rangeSize + 1;
        int[] blocks = new int[arraySize];

        Scanner in = new Scanner(new FileReader(filename));
        while (in.hasNextInt()) {
            int value = in.nextInt();
            blocks[value / rangeSize]++;
        }
        in.close();
        return blocks;
    }

    // 배열을 순회하며 빠진 숫자가 있는 블록을 찾음
    // rangeSize보다 적은 수의 정수가 포함되어 있으면 해당 블록은 빠진 숫자로 간주
    int findBlockWithMissing(int[] blocks, int rangeSize) {
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] < rangeSize) {
                return i;
            }
        }
        return -1;
    }

    // 찾은 블록의 모든 정수에 대해 비트 벡터 생성
    byte[] getBitfieldForRange(String filename, int blockIndex, int rangeSize) throws FileNotFoundException {
        int startRange = blockIndex * rangeSize;
        int endRange = startRange + rangeSize;
        byte[] bitVector = new byte[rangeSize / Byte.SIZE];

        Scanner in = new Scanner(new FileReader(filename));
        while (in.hasNextInt()) {
            int value = in.nextInt();
            if (startRange <= value && value < endRange) {
                int offset = value - startRange;
                int mask = 1 << (offset % Byte.SIZE);
                bitVector[offset / Byte.SIZE] |= mask;
            }
        }
        in.close();
        return bitVector;
    }

    // 빠진 숫자를 찾기(비트 벡터에서 0인 위치 찾기)
    int findZero(byte b) {
        for (int i = 0; i < Byte.SIZE; i++) {
            int mask = 1 << i;
            if ((b & mask) == 0) {
                return i;
            }
        }
        return -1;
    }


    // 비트 벡터에서 0인 위치 찾기
    int findZero(byte[] bitVector) {
        for (int i = 0; i < bitVector.length; i++) {
            if (bitVector[i] != ~0) { // ~0은 모든 비트가 1인 값
                int bitIndex = findZero(bitVector[i]);
                return i * Byte.SIZE + bitIndex;
            }
        }
        return -1;
    }
}
