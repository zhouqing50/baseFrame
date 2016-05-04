/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author <a href="http://www.christianposta.com/blog">Christian Posta</a>
 */
public class Producer {
   // private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String BROKER_URL = "failover://(tcp://masterhost:61616,tcp://slavehost:61616)?randomize=false";
   

    private static final Boolean NON_TRANSACTED = false;
    private static final int NUM_MESSAGES_TO_SEND = 10;
    private static final long DELAY = 100;
    private static final long TIMEOUT = 20000;

    public static void main(String[] args) {
        String url = BROKER_URL;
        if (args.length > 0) {
            url = args[0].trim();
        }
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "password", url);
        Connection connection = null;
   
        try {

            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("test-queue");
            MessageProducer producer = session.createProducer(destination);
            
            Destination destination1 = session.createQueue("replyTo-queue");
            for (int i = 0; i < NUM_MESSAGES_TO_SEND; i++) {
                TextMessage message = session.createTextMessage("Message #" + i);
                System.out.println("Sending message #" + i+"correlationID--"+i);
                message.setJMSReplyTo(destination1);
                message.setJMSCorrelationID("correlationID--"+i);
                producer.send(message);
                Thread.sleep(DELAY);
            }
            
            MessageConsumer consumer = session.createConsumer(destination1);
            while (true) {
                Message message = consumer.receive(TIMEOUT);
                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("receive -----message: " + text);
                    }
                } else {
                    break;
                }
            }
            consumer.close();
            producer.close();
            session.close();

        } catch (Exception e) {
            System.out.println("Caught exception!");
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    System.out.println("Could not close an open connection...");
                }
            }
        }
    }

}