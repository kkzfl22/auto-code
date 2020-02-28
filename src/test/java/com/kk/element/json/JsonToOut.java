package com.kk.element.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class JsonToOut {

  @Test
  public void outJson() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("result", true);
    jsonObject.addProperty("errorCode", 1);
    jsonObject.addProperty("errorMsg", "msg");

    JsonArray array = new JsonArray();

    for (int i = 0; i < 10; i++) {
      JsonObject object = new JsonObject();
      object.addProperty("jid", String.valueOf(i));
      array.add(object);
    }

    jsonObject.add("list", array);

    Gson out = new Gson();

    String outString = out.toJson(jsonObject);
    System.out.println(outString);
  }
}
