package com.kk.autocode.junitAction.exceptiontest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class TestRunJunit3 {

  @Test
  public void canVote_throws_IllegalArgumentException_for_zero_age() {
    RunFunction student = new RunFunction();
    try {
      student.canVote(0);
    } catch (IllegalArgumentException ex) {
      Assert.assertThat(ex.getMessage(), CoreMatchers.containsString("age should be +ve"));
    }
    Assert.fail("expected IllegalArgumentException for non +ve age");
  }
}
