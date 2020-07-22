package org.example;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

  // not to match "-"s used for negative numbers
  private static final String SUBTRACTION_SIGN = ".*[^*/]-.*";
  private static final Pattern FIRST_SUBTRACTION_SIGN = Pattern.compile("[^*/]+-");

  public static double resultOfExpression(String expression) {
    if (expression.contains("cos")) {
      return resultOfFunction(expression, "cos", Math::cos);
    }
    if (expression.contains("(")) {
      return resultOfSubExpression(expression);
    }
    if (expression.contains("+")) {
      return resultOfAddition(expression);
    }
    if (expression.matches(SUBTRACTION_SIGN)) {
      return resultOfSubtraction(expression);
    }
    if (expression.contains("*")) {
      return resultOfMultiplication(expression);
    }
    if (expression.contains("/")) {
      return resultOfDivision(expression);
    }
    return Double.parseDouble(expression);
  }

  private static double resultOfSubExpression(String expression) {
    return resultOfFunction(expression, "", Function.identity());
  }

  private static double resultOfFunction(String expression, String functionName, Function<Double, Double> function) {
    String functionStart = functionName + "(";
    int indexOfFirstFunctionStart = expression.indexOf(functionStart);
    int indexOfMatchingClosingParenthesis = getIndexOfMatchingClosingParenthesis(expression, indexOfFirstFunctionStart + functionStart.length() - 1);
    String startOfExpression = expression.substring(0, indexOfFirstFunctionStart);
    String endOfExpression = expression.substring(indexOfMatchingClosingParenthesis + 1);
    return resultOfExpression(
        startOfExpression
            + function.apply(resultOfExpression(expression.substring(indexOfFirstFunctionStart + functionStart.length(), indexOfMatchingClosingParenthesis)))
            + endOfExpression);
  }

  private static double resultOfDivision(String expression) {
    String[] divisionArguments = expression.split("/");
    double result = resultOfExpression(divisionArguments[0]);
    for (int i = 1; i < divisionArguments.length; i++) {
      result /= resultOfExpression(divisionArguments[i]);
    }
    return result;
  }

  private static double resultOfMultiplication(String expression) {
    int indexOfMultiplication = expression.indexOf("*");
    return resultOfExpression(expression.substring(0, indexOfMultiplication)) *
        resultOfExpression(expression.substring(indexOfMultiplication + 1));
  }

  private static int getIndexOfMatchingClosingParenthesis(String expression, int indexOfFirstStartingParenthesis) {
    int parenthesisCounter = 0;
    char[] charArray = expression.toCharArray();
    int indexOfMatchingClosingParenthesis = indexOfFirstStartingParenthesis;
    for (; indexOfMatchingClosingParenthesis < charArray.length; indexOfMatchingClosingParenthesis++) {
      char c = charArray[indexOfMatchingClosingParenthesis];
      if (c == '(') {
        parenthesisCounter++;
      } else if (c == ')') {
        parenthesisCounter--;
      }
      if (parenthesisCounter == 0) {
        break;
      }
    }
    return indexOfMatchingClosingParenthesis;
  }

  private static double resultOfSubtraction(String expression) {
    Matcher matcher = FIRST_SUBTRACTION_SIGN.matcher(expression);
    matcher.find();
    int indexOfSubtraction = matcher.end() - 1;
    return resultOfExpression(expression.substring(0, indexOfSubtraction)) + resultOfExpression(expression.substring(indexOfSubtraction));
  }

  private static double resultOfAddition(String expression) {
    String[] toAdd = expression.split("\\+");
    double result = 0;
    for (String s : toAdd) {
      result += resultOfExpression(s);
    }
    return result;
  }
}
