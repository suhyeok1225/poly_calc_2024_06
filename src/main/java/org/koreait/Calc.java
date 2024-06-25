package org.koreait;

public class Calc {
    public static int run(String exp) {

        boolean needToMul = exp.contains("*");
        boolean needToPlus = exp.contains("+");
        boolean needToCoper = needToMul && needToPlus;

        if (needToCoper) {
            String[] bits = exp.split(" \\+ ");

            return Integer.parseInt(bits[0]) + run(bits[1]);
        }
        if (needToPlus) {
            exp = exp.replaceAll("- ", "+ -");
            String[] bits = exp.split(" \\+ ");
            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }
            return sum;
        } else if (needToMul) {
            String[] bits = exp.split(" \\* ");
            int mul = 1;
            for (int i = 0; i < bits.length; i++) {
                mul *= Integer.parseInt(bits[i]);
            }
            return mul;
        }
        throw new RuntimeException("해석불가");
    }
}


