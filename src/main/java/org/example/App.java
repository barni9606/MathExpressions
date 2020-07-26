package org.example;

import java.util.function.Function;

public class App {

  private static final NumberFactory NUMBER_FACTORY = new NumberFactory();
  private static final AdditionFactory ADDITION_FACTORY = new AdditionFactory();
  private static final SubtractionFactory SUBTRACTION_FACTORY = new SubtractionFactory();
  private static final MultiplicationFactory MULTIPLICATION_FACTORY = new MultiplicationFactory();
  private static final DivisionFactory DIVISION_FACTORY = new DivisionFactory();
  private static final FunctionFactory PARENTHESIS_FACTORY = new FunctionFactory(new FunctionDescription(Function.identity(), ""));
  private static final FunctionFactory COS_FACTORY = new FunctionFactory(new FunctionDescription(Math::cos, "cos"));
  private static final FunctionFactory SIN_FACTORY = new FunctionFactory(new FunctionDescription(Math::sin, "sin"));

  public static Expression resultOfExpression(String expression) {
    if (SIN_FACTORY.test(expression)) {
      return SIN_FACTORY.of(expression);
    }
    if (COS_FACTORY.test(expression)) {
      return COS_FACTORY.of(expression);
    }
    if (PARENTHESIS_FACTORY.test(expression)) {
      return PARENTHESIS_FACTORY.of(expression);
    }
    if (ADDITION_FACTORY.test(expression)) {
      return ADDITION_FACTORY.of(expression);
    }
    if (SUBTRACTION_FACTORY.test(expression)) {
      return SUBTRACTION_FACTORY.of(expression);
    }
    if (MULTIPLICATION_FACTORY.test(expression)) {
      return MULTIPLICATION_FACTORY.of(expression);
    }
    if (DIVISION_FACTORY.test(expression)) {
      return DIVISION_FACTORY.of(expression);
    }
    return NUMBER_FACTORY.of(expression);
  }

}
