package org.koreait;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {

        exp = exp.trim(); //공백 제거
        exp = Brackets(exp); //괄호 처리
        if (!exp.contains(" ")) return Integer.parseInt(exp);

        boolean needToMul = exp.contains("*"); // 곱셈 연산자 여부 확인
        boolean needToPlus = exp.contains("+") || exp.contains(" - "); // 덧셈 또는 뺄셈 연산자 여부 확인
        boolean needToCoper = needToMul && needToPlus; // 곱셈과 덧셈/뺄셈 모두 포함 여부 확인
        boolean needToSplit = exp.contains("(") && exp.contains(")"); // 괄호 여부 확인

        if (needToSplit) {
            int splitPointIndex = findSplitPointIndex(exp);
            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex + 1);

            char operator = exp.charAt(splitPointIndex);
            exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp); //부분 문자열 계산
            return Calc.run(exp);
        } else if (needToCoper) {
            String[] bits = exp.split(" \\+ "); //문자열 bits의 +를 무시한다.

            String newExp = Arrays.stream(bits). //문자열 newExp에 bits문자열을 넣는다.
                    mapToInt(Calc::run).         //Calc 함수에 정수 문자를 받아온다.
                    mapToObj(e -> e + "").       // 문자를 띄어서 새롭게 변환
                    collect(Collectors.joining(" + ")); //지정된 +문자를 사용하여 결합한다.

            return run(newExp); //재귀함수로 run메서드에 newExp 값을 넣어서 실행
        } else if (needToPlus) {
            exp = exp.replaceAll("- ", "+ -"); //-기호를 + - 로 대체한다.
            String[] bits = exp.split(" \\+ "); //문자열 bits의 +를 무시한다.
            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]); //bits 문자열 0번째 부터 마지막 번째까지 더한다.
            }
            return sum;
        } else if (needToMul) {
            String[] bits = exp.split(" \\* ");
            int mul = 1;
            for (int i = 0; i < bits.length; i++) {
                mul *= Integer.parseInt(bits[i]); //bits 문자열 0번째 부터 마지막 번째까지 곱한다.
            }
            return mul;
        }


        throw new RuntimeException("해석불가");
    }
    private static int findSplitPointIndex(String exp) {
        int index = findSplitPointIndexBy(exp, '+');

        if (index >= 0) return index;

        return findSplitPointIndexBy(exp, '*');
    }

    private static int findSplitPointIndexBy(String exp, char findChar) {
        int brackesCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                brackesCount++;
            } else if (c == ')') {
                brackesCount--;
            } else if (c == findChar) {
                if (brackesCount == 0) return i;
            }
        }
        return -1;
    }


    private static String Brackets(String exp) {

        int bracketsCount = 0;
        while (exp.charAt(bracketsCount) == '(' && //계산식 앞에 괄호가 있을 때 and
                exp.charAt(exp.length() - 1 - bracketsCount) == ')') // 계산식 맨뒤에 괄호가 있을 때
        {
            bracketsCount++; //괄호가 있을 때 마다 값을 증가시킨다.
        }
        if (bracketsCount == 0) return exp; //괄호가 없으면 그대로 출력
        return exp.substring(bracketsCount, exp.length() - bracketsCount);
        // 괄호가 있으면 괄호 잘라서 출력 substring(시작 +1 부터, 끝 사이의 문자열 리턴)
    }
}



