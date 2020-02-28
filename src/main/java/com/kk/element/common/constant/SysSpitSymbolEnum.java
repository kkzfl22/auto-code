package com.kk.element.common.constant;

/**
 * system Spit Symbol
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/14
 */
public enum SysSpitSymbolEnum {

  /** underline,expmale in table name */
  UNDERLINE("___"),

  /** */
  POINT("."),

  /** */
  MINUS("-"),

  PATH("/"),

  /** */
  CAMMA(","),

  BRACKET_START("("),

  BRACKET_END(")");

  private String symbol;

  SysSpitSymbolEnum(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }
}
