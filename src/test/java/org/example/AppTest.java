package org.example;

import org.junit.Test;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class AppTest {
  @Test
  public void shouldParseIntegers() {
    assertThat(App.resultOfExpression("0").value(), is(0d));
    assertThat(App.resultOfExpression("1").value(), is(1d));
    assertThat(App.resultOfExpression("2").value(), is(2d));
  }

  @Test
  public void shouldParseFloats() {
    assertThat(App.resultOfExpression("1.0").value(), is(1.0));
    assertThat(App.resultOfExpression("2.3").value(), is(2.3));
    assertThat(App.resultOfExpression("-1.0").value(), is(-1.0));
  }

  @Test
  public void shouldParseAddition() {
    assertThat(App.resultOfExpression("1+1").value(), is(2d));
    assertThat(App.resultOfExpression("1+2").value(), is(3d));
    assertThat(App.resultOfExpression("3.1+4.5").value(), is(3.1 + 4.5));
    assertThat(App.resultOfExpression("1+1+1").value(), is(3d));
    assertThat(App.resultOfExpression("1+-1+1").value(), is(1d));
  }

  @Test
  public void shouldParseSubtraction() {
    assertThat(App.resultOfExpression("1-1").value(), is(0d));
    assertThat(App.resultOfExpression("1-2").value(), is(-1d));
    assertThat(App.resultOfExpression("2-1").value(), is(1d));
    assertThat(App.resultOfExpression("-2-1").value(), is(-3d));
    assertThat(App.resultOfExpression("2-1-1").value(), is(0d));
    assertThat(App.resultOfExpression("5.6-2.3-1.1").value(), is(5.6 - 2.3 - 1.1));
  }

  @Test
  public void shouldParseAdditionAndSubtraction() {
    assertThat(App.resultOfExpression("1+1-1").value(), is(1d));
    assertThat(App.resultOfExpression("1+2-3+4-5+6-7").value(), is(1d + 2 - 3 + 4 - 5 + 6 - 7));
    assertThat(App.resultOfExpression("-3.6-10+7.2").value(), is(-3.6 - 10 + 7.2));
  }

  @Test
  public void shouldParseParenthesis() {
    assertThat(App.resultOfExpression("(0)").value(), is(0d));
    assertThat(App.resultOfExpression("(1)").value(), is(1d));
    assertThat(App.resultOfExpression("(-1)").value(), is(-1d));
    assertThat(App.resultOfExpression("((-1))").value(), is(-1d));
  }

  @Test
  public void shouldParseExpressionInsideParenthesis() {
    assertThat(App.resultOfExpression("(1+1)").value(), is(2d));
    assertThat(App.resultOfExpression("(5.6-2.3-1.1)").value(), is(5.6 - 2.3 - 1.1));
    assertThat(App.resultOfExpression("(-3.6-10+7.2)").value(), is(-3.6 - 10 + 7.2));
  }

  @Test
  public void shouldParseExpressionWithParenthesis() {
    assertThat(App.resultOfExpression("1+(1)").value(), is(2d));
    assertThat(App.resultOfExpression("(1)+1").value(), is(2d));
    assertThat(App.resultOfExpression("(-1)+1").value(), is(0d));
    assertThat(App.resultOfExpression("(-1)+(1)").value(), is(0d));
    assertThat(App.resultOfExpression("((-1)+(1))").value(), is(0d));
    assertThat(App.resultOfExpression("(-1+(1))").value(), is(0d));
    assertThat(App.resultOfExpression("-1+(1)").value(), is(0d));
    assertThat(App.resultOfExpression("-3.6-(10+7.2)").value(), is(-3.6 - (10 + 7.2)));
    assertThat(App.resultOfExpression("(-3.6+2-(10+7.2))-3").value(), is((-3.6 + 2 - (10 + 7.2)) - 3));
  }

  @Test
  public void shouldParseMultiplication() {
    assertThat(App.resultOfExpression("1*1").value(), is(1d));
    assertThat(App.resultOfExpression("1*2").value(), is(2d));
    assertThat(App.resultOfExpression("3*2").value(), is(6d));
    assertThat(App.resultOfExpression("3*-2").value(), is(-6d));
    assertThat(App.resultOfExpression("3*2*4").value(), is(24d));
  }

  @Test
  public void shouldParseMultiplicationWithAddition() {
    assertThat(App.resultOfExpression("1*1+1").value(), is(2d));
    assertThat(App.resultOfExpression("2+1*1+1").value(), is(4d));
    assertThat(App.resultOfExpression("1.3*4.2+3.6*5").value(), is(1.3 * 4.2 + 3.6 * 5));
  }

  @Test
  public void shouldParseMultiplicationsWithAdditionsSubtractions() {
    assertThat(App.resultOfExpression("3-2*1").value(), is(1d));
    assertThat(App.resultOfExpression("3-2*1-1").value(), is(0d));
    assertThat(App.resultOfExpression("3-2*1-5*3+4").value(), is(3d - 2 * 1 - 5 * 3 + 4));
  }

  @Test
  public void shouldParseMultiplicationWithParenthesis() {
    assertThat(App.resultOfExpression("(3*5)").value(), is(15d));
    assertThat(App.resultOfExpression("(3+5)*2").value(), is(16d));
    assertThat(App.resultOfExpression("(3+5)*2-6").value(), is(10d));
    assertThat(App.resultOfExpression("(3-5)*2-6*2").value(), is((3 - 5) * 2 - 6 * 2d));
    assertThat(App.resultOfExpression("(3-5)*(2-6)*2").value(), is((3 - 5) * (2 - 6) * 2d));
  }

  @Test
  public void shouldParseDivision() {
    assertThat(App.resultOfExpression("1/1").value(), is(1d / 1));
    assertThat(App.resultOfExpression("2/1").value(), is(2d / 1));
    assertThat(App.resultOfExpression("3/2").value(), is(3d / 2));
    assertThat(App.resultOfExpression("3/2/2").value(), is(3d / 2 / 2));
  }

  @Test
  public void shouldParseExpressionWithDivision() {
    assertThat(App.resultOfExpression("3/2+2").value(), is(3d / 2 + 2));
    assertThat(App.resultOfExpression("3/2-2").value(), is(3d / 2 - 2));
    assertThat(App.resultOfExpression("5-3/2-2").value(), is(5 - 3d / 2 - 2));
    assertThat(App.resultOfExpression("5-3*8/2-2").value(), is(5 - 3d * 8 / 2 - 2));
    assertThat(App.resultOfExpression("5-3/-1.5*8/2-2").value(), is(5 - 3d / -1.5 * 8d / 2 - 2));
    assertThat(App.resultOfExpression("5-3/(-1.5*8)/(2-3)").value(), is(5d - 3d / (-1.5d * 8) / (2d - 3)));
    assertThat(App.resultOfExpression("5-3/(-1.5*8)/(2+3*5-2)").value(), is(5 - 3 / (-1.5 * 8) / (2 + 3 * 5 - 2)));
  }

  @Test
  public void shouldParseCos() {
    assertThat(App.resultOfExpression("cos(1)").value(), is(cos(1d)));
    assertThat(App.resultOfExpression("cos(2)").value(), is(cos(2d)));
    assertThat(App.resultOfExpression("cos(3)").value(), is(cos(3d)));
    assertThat(App.resultOfExpression("cos(1+2)").value(), is(cos(3d)));
    assertThat(App.resultOfExpression("cos(5-3/(-1.5*8)/(2+3*5-2))").value(), is(cos(5 - 3 / (-1.5 * 8) / (2 + 3 * 5 - 2))));
    assertThat(App.resultOfExpression("5-3/(-1.5*8)/cos(1+5-3/2-2)*8/2-2").value(), is(5 - 3 / (-1.5 * 8) / cos(1 + 5 - 3d / 2 - 2) * 8 / 2 - 2));
  }

  @Test
  public void shouldParseSin() {
    assertThat(App.resultOfExpression("sin(1)").value(), is(sin(1d)));
    assertThat(App.resultOfExpression("sin(1)*cos(3)*sin(2)").value(), is(closeTo(sin(1) * cos(3) * sin(2), 0.0000001d)));
    assertThat(App.resultOfExpression("sin(1)*cos(3+sin(10))*sin(2)").value(), is(closeTo(sin(1) * cos(3 + sin(10)) * sin(2), 0.0000001d)));
    assertThat(App.resultOfExpression("5-3/(-1.5*8)/sin(1+5-3/2-2)*8/2-2").value(), is(5 - 3 / (-1.5 * 8) / sin(1 + 5 - 3d / 2 - 2) * 8 / 2 - 2));
  }
}
