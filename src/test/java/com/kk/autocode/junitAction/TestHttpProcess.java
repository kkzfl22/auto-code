package com.kk.autocode.junitAction;

import com.kk.autocode.encode.code.bean.junitAction.HttpProcess;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试http消息响应的发送
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHttpProcess {

  @Test
  public void testSendPostHttp() {
    String url = "http://10.100.63.183:8080/infodt-ingest/htDcJobConfActionWeb/insert.action";
    String json =
        "{\"jid\":3,\"multi\":3,\"dspNum\":3,\"srcUpstream\":3,\"srcDbId\":\"V4\",\"srcSchema\":\"V\",\"srcTable\":\"V\",\"destHiveDb\":\"V\",\"destTable\":\"V\",\"destDir\":\"V\",\"partitionType\":\"D\",\"dateFlag\":1,\"selectCondition\":\"V\",\"enabled\":1,\"redoInterval\":1,\"redoTimes\":1,\"judgement\":1,\"judgeRecords\":1,\"fileFormat\":\"D\",\"mapOpt\":\"V\",\"columnList\":\"V\",\"columnListOrig\":\"V\",\"tableCreated\":1,\"copyNeeded\":1,\"validFlag\":\"V\",\"createId\":\"V\",\"createTime\":\"2019-01-01 02:00:00\",\"modifyId\":\"V\",\"modifyTime\":\"2019-01-01 02:00:00\"}";

    String rsp = HttpProcess.INSTANCE.sendPostProcessJson(url, json);
    System.out.println(rsp);
    Assert.assertNotNull(rsp);
  }
}
