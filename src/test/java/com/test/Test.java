package com.test;

public class Test {

  String name; // 姓名

  float hp; // 血量

  float armor; // 护甲

  int moveSpeed; // 移动速度

  public Test() {}

  public Test(String name, float hp) {
    this.name = name;
    this.hp = hp;
  }

  // 复活
  public void revive(Test h) {
    h = new Test("提莫", 383);
    // h.hp = 1000;
    System.out.println(h.hp);
  }

  public static void main(String[] args) {
    Test teemo = new Test("提莫", 383);

    // 受到400伤害，挂了
    teemo.hp = teemo.hp - 400;

    teemo.revive(teemo);

    // 问题： System.out.println(teemo.hp); 输出多少？ 怎么理解？
    System.out.println(teemo.hp);
  }
}
