package com.kk.autocode.junitAction.exceptiontest;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class RunFunction {

  public boolean canVote(int age) {
    if (age <= 0) throw new IllegalArgumentException("age should be +ve");
    if (age < 18) return false;
    else return true;
  }
}
