package org.example;

import static org.example.App.resultOfExpression;

public class DivisionFactory implements Expression.ExpressionFactory {
  @Override
  public boolean test(String expression) {
    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 0; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      } else if (parenthesisCounter == 0 && expression.charAt(i) == '/') {
        return true;
      }
    }
    return false;
  }

  @Override
  public Expression of(String expression) {
    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 0; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      } else if (parenthesisCounter == 0 && expression.charAt(i) == '/') {
        return new Division(resultOfExpression(expression.substring(0, i)), resultOfExpression(expression.substring(i + 1)));
      }
    }
    throw new IllegalArgumentException();
  }

  private static class Division implements Expression {

    private final Expression firstOperand;
    private final Expression secondOperand;

    public Division(Expression firstOperand, Expression secondOperand) {

      this.firstOperand = firstOperand;
      this.secondOperand = secondOperand;
    }

    @Override
    public double value() {
      return firstOperand.value() / secondOperand.value();
    }
  }
}
