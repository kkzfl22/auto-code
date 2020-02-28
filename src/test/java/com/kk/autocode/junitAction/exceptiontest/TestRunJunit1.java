package com.kk.autocode.junitAction.exceptiontest;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class TestRunJunit1 {

  /** 方法1直接测试 @Test注解有一个可选的参数，”expected”允许你设置一个Throwable的子类 */
  @Test(expected = IllegalArgumentException.class)
  public void canVote_throws_IllegalArgumentException_for_zero_age() {
    RunFunction student = new RunFunction();
    student.canVote(0);
  }


}
