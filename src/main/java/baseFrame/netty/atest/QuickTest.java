/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package baseFrame.netty.atest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class QuickTest {

    public static void main(String[] args) throws Exception {
    	
    	for (int i = 0; i < 100000; i++) {
    		CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://127.0.0.1:8080");
                CloseableHttpResponse response1 = httpclient.execute(httpGet);
                
                long startTime = System.nanoTime();
                System.out.println(response1.getStatusLine());
                HttpEntity entity = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
              //内容类型  
               System.out.println(entity.getContentType());  
               long endTime = System.nanoTime();
               System.out.println("耗时-----"+(endTime-startTime));
                
            }finally{
            	  httpclient.close();
            }

		}
    	
    	
       /* CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
              //内容类型  
               System.out.println(entity.getContentType());  
               //内容的编码格式  
                System.out.println(entity.getContentEncoding());  
                 //内容的长度  
                  System.out.println(entity.getContentLength());  
                //把内容转成字符串  
                 System.out.println(EntityUtils.toString(entity));  
               //内容转成字节数组  
                //System.out.println(EntityUtils.toByteArray(entity).length);  
                //还有个直接获得流  
               //entity.getContent();  
                EntityUtils.consume(entity);
            } finally {
                response1.close();
            }

            HttpPost httpPost = new HttpPost("http://targethost/login");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }*/
    }

}
