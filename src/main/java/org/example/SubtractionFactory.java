package org.example;

import static org.example.App.resultOfExpression;

public class SubtractionFactory implements Expression.ExpressionFactory {
  // not to match "-"s used for negative numbers
  private static final String SUBTRACTION_SIGN = "[^*/+]-.*";

  @Override
  public boolean test(String expression) {
    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 1; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      } else if (parenthesisCounter == 0 && expression.substring(i - 1).matches(SUBTRACTION_SIGN)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Expression of(String expression) {
    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 1; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      } else if (parenthesisCounter == 0 && expression.substring(i - 1).matches(SUBTRACTION_SIGN)) {
        return new Subtraction(resultOfExpression(expression.substring(0, i)), resultOfExpression(expression.substring(i + 1)));
      }
    }
    throw new IllegalArgumentException();
  }

  private static class Subtraction implements Expression {
    private final Expression subtractFrom;
    private final Expression subtractAmount;

    private Subtraction(Expression subtractFrom, Expression subtractAmount) {
      this.subtractFrom = subtractFrom;
      this.subtractAmount = subtractAmount;
    }

    @Override
    public double value() {
      return subtractFrom.value() - subtractAmount.value();
    }
  }
}
