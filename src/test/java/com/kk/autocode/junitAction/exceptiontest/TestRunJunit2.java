package com.kk.autocode.junitAction.exceptiontest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 如果要使用JUnit框架中的ExpectedException类，需要声明ExpectedException异常。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class TestRunJunit2 {

  @Rule public ExpectedException thrown = ExpectedException.none();

  /** 可以使用更加简单的方式验证预期的异常。 */
  @Test
  public void canVote_throws_IllegalArgumentException_for_zero_age() {
    RunFunction student = new RunFunction();
    thrown.expect(IllegalArgumentException.class);
    student.canVote(0);
  }

  /** 可以设置预期异常的属性信息。 */
  @Test
  public void canVote_throws_IllegalArgumentException_for_zero_age2() {
    RunFunction student = new RunFunction();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("age should be +ve");
    student.canVote(0);
  }
}
