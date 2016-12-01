/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package netty.rocketmqNetty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Remoting模块中，服务器与客户端通过传递RemotingCommand来交互
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public class RemotingCommand implements Serializable {
    public static String RemotingVersionKey = "rocketmq.remoting.version";
    private static volatile int ConfigVersion = -1;
    private static AtomicInteger RequestId = new AtomicInteger(0);

    private static final int RPC_TYPE = 0; // 0, REQUEST_COMMAND
    // 1, RESPONSE_COMMAND

    private static final int RPC_ONEWAY = 1; // 0, RPC
    // 1, Oneway

    /**
     * Header 部分
     */
    private int code;
    private int version = 0;
    private int opaque = RequestId.getAndIncrement();
    private int flag = 0;
    private String remark;
    private HashMap<String, String> extFields;

    public static String getRemotingVersionKey() {
        return RemotingVersionKey;
    }

    public static void setRemotingVersionKey(String remotingVersionKey) {
        RemotingVersionKey = remotingVersionKey;
    }

    public static int getConfigVersion() {
        return ConfigVersion;
    }

    public static void setConfigVersion(int configVersion) {
        ConfigVersion = configVersion;
    }

    public static AtomicInteger getRequestId() {
        return RequestId;
    }

    public static void setRequestId(AtomicInteger requestId) {
        RequestId = requestId;
    }

    public static int getRpcType() {
        return RPC_TYPE;
    }

    public static int getRpcOneway() {
        return RPC_ONEWAY;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getOpaque() {
        return opaque;
    }

    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HashMap<String, String> getExtFields() {
        return extFields;
    }

    public void setExtFields(HashMap<String, String> extFields) {
        this.extFields = extFields;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * Body 部分
     */
    private transient byte[] body;


    protected RemotingCommand() {
    }


    public static RemotingCommand createRequestCommand(int code) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        return cmd;
    }


    public static RemotingCommand createResponseCommand(int code, String remark) {
        return createResponseCommand(code);
    }

    public static RemotingCommand createResponseCommand(int code) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        return cmd;
    }

    @Override
    public String toString() {
        return "RemotingCommand{" +
                "code=" + code +
                ", version=" + version +
                ", opaque=" + opaque +
                ", flag=" + flag +
                ", remark='" + remark + '\'' +
                ", extFields=" + extFields +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
