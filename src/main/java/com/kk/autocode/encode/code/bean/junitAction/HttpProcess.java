package com.kk.autocode.encode.code.bean.junitAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 进行网存下载操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HttpProcess {

  private Logger logger = LoggerFactory.getLogger(HttpProcess.class);

  /** 指定contexttype信息 */
  private static final String CONTEXT_TYPE_VALUE = "application/json;charset=UTF-8";

  private static final String DATA_TYPE_VALUE = "application/json";

  /** 下载网页的实例对象信息 */
  public static final HttpProcess INSTANCE = new HttpProcess();

  /**
   * 根据网页地址下载网页信息
   *
   * @param url
   * @return
   */
  public String sendPostProcessJson(String url, String param) {

    CloseableHttpClient client = HttpClients.createDefault();

    HttpPost post = new HttpPost(url);
    // 使用addHeader方法添加请求头部,诸如User-Agent, Accept-Encoding等参数.
    post.setHeader("Content-Type", CONTEXT_TYPE_VALUE);

    try {
      // 组织数据
      StringEntity se = new StringEntity(param);
      // 设置编码格式
      se.setContentEncoding(StandardCharsets.UTF_8.name());
      // 设置数据类型
      se.setContentType(DATA_TYPE_VALUE);
      // 对于POST请求,把请求体填充进HttpPost实体.
      post.setEntity(se);

      HttpResponse response = client.execute(post);

      if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("HttpProcess downloadHtml IOException", e);
    } finally {
      if (null != client) {
        try {
          client.close();
        } catch (IOException e) {
          e.printStackTrace();
          logger.error("HttpProcess downloadHtml close IOException", e);
        }
      }
    }

    // 翻放内存对象
    client = null;

    return StringUtils.EMPTY;
  }
}
