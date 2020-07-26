package org.example;

import static org.example.App.resultOfExpression;

public class FunctionFactory implements Expression.ExpressionFactory {
  private final FunctionDescription functionDescription;

  public FunctionFactory(FunctionDescription functionDescription) {
    this.functionDescription = functionDescription;
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

  @Override
  public boolean test(String expression) {
    return expression.startsWith(functionDescription.getFunctionNameWithStartingParenthesis()) && getIndexOfMatchingClosingParenthesis(expression, functionDescription.getFunctionName().length()) == expression.length() - 1;
  }

  @Override
  public Expression of(String expression) {
    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 0; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      }
      if (parenthesisCounter == 0 && expression.length() > i + functionDescription.getFunctionNameWithStartingParenthesis().length() && expression.substring(i, i + functionDescription.getFunctionNameWithStartingParenthesis().length()).equals(functionDescription.getFunctionNameWithStartingParenthesis())) {
        return new Function(resultOfExpression(expression.substring(i + functionDescription.getFunctionNameWithStartingParenthesis().length(),
                getIndexOfMatchingClosingParenthesis(expression, i + functionDescription.getFunctionName().length()))),
                functionDescription);
      }
    }
    throw new IllegalArgumentException();
  }

  private static class Function implements Expression {
    private Expression subExpression;
    private FunctionDescription functionDescription;

    public Function(Expression subExpression, FunctionDescription functionDescription) {
      this.subExpression = subExpression;
      this.functionDescription = functionDescription;
    }

    @Override
    public double value() {
      return functionDescription.getFunction().apply(subExpression.value());
    }
  }
}
