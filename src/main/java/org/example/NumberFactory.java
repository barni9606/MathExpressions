package org.example;

class NumberFactory implements Expression.ExpressionFactory {

  @Override
  public boolean test(String expression) {
    return true;
  }

  @Override
  public Expression of(String expression) {
    return new Number(Double.parseDouble(expression));
  }

  private static class Number implements Expression {
    private final double value;

    private Number(double value) {
      this.value = value;
    }

    @Override
    public double value() {
      return value;
    }
  }
}
