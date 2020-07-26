package org.example;

import java.util.function.Function;

public class FunctionDescription {
  private final Function<Double, Double> function;
  private final String functionName;
  private final String functionNameWithStartingParenthesis;

  public FunctionDescription(Function<Double, Double> function, String functionName) {
    this.function = function;
    this.functionName = functionName;
    this.functionNameWithStartingParenthesis = functionName + "(";
  }

  public Function<Double, Double> getFunction() {
    return function;
  }

  public String getFunctionName() {
    return functionName;
  }

  public String getFunctionNameWithStartingParenthesis() {
    return functionNameWithStartingParenthesis;
  }
}
