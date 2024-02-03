package string;

// StringBuilder 구현하기
public class MyStringBuilder {
    private char[] chars;
    private int size;

    public MyStringBuilder() {
        this.size = 0;
        this.chars = new char[this.size];
    }

    public void append(String str) {
        int strSize = str.length();
        char[] strChars = new char[strSize];
        for (int i = 0; i < strSize; i++) {
            strChars[i] = str.charAt(i);
        }
        char[] bigger = new char[this.size + strSize];
        System.arraycopy(this.chars, 0, bigger, 0, this.size);
        System.arraycopy(strChars, 0, bigger, this.size, strSize);
        this.chars = bigger;
        this.size += strSize;
    }

    public String toString() {
        return new String(this.chars);
    }

    public int length() {
        return this.size;
    }
}
