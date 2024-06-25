package org.koreait;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {

        exp = Brackets(exp);
        boolean needToMul = exp.contains("*");
        boolean needToPlus = exp.contains("+") || exp.contains(" - ");
        boolean needToCoper = needToMul && needToPlus;



        if (needToCoper) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits).
                    mapToInt(Calc::run).
                    mapToObj(e -> e + "").
                    collect(Collectors.joining(" + "));

            return run(newExp);
        } else if (needToPlus) {
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

    private static String Brackets(String exp) {
        if(exp.charAt(0) == '(' && exp.charAt(exp.length()-1) == ')'){
            exp = exp.substring(1, exp.length()-1);
        }
        return exp;
    }
}


