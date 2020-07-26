package org.example;

import static org.example.App.resultOfExpression;

public class MultiplicationFactory implements Expression.ExpressionFactory {
  @Override
  public boolean test(String expression) {

    int parenthesisCounter = 0;
    for (int i = expression.length() - 1; i >= 0; i--) {
      if (expression.charAt(i) == ')') {
        parenthesisCounter++;
      } else if (expression.charAt(i) == '(') {
        parenthesisCounter--;
      } else if (parenthesisCounter == 0 && expression.charAt(i) == '*') {
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
      } else if (parenthesisCounter == 0 && expression.charAt(i) == '*') {
        return new Multiplication(resultOfExpression(expression.substring(0, i)), resultOfExpression(expression.substring(i + 1)));
      }
    }
    throw new IllegalArgumentException();
  }

  private static class Multiplication implements Expression {
    private final Expression first;
    private final Expression second;

    private Multiplication(Expression first, Expression second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public double value() {
      return first.value() * second.value();
    }
  }
}
