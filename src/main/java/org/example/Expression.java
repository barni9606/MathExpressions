package org.example;

public interface Expression {
  double value();

  interface ExpressionFactory {
    boolean test(String expression);

    Expression of(String expression);
  }
}
