import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Base64;

/**
 * Description:
 * User: zhouq
 * Date: 2016/8/22
 */


public class testA {
    public static void main(String [] args) {
//        List<String> appAdminIdsResult = Lists.newArrayList("111","222","333");
//        System.out.println();
//        SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//
//
//
//
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        System.out.println(ts+"======"+ts.getTime());
//        String tsStr = "";
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            //方法一
//            tsStr = sdf.format(ts);
//            System.out.println(tsStr);
//            //方法二
//            tsStr = ts.toString();
//            System.out.println(tsStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //1473066610295   2016-09-05 17:10:10.295
//
//        //String time=String.valueOf("2016-09-02 00:00:00.1");  //1472745600100
//
//        String time=String.valueOf("2016-09-02 23:59:59.1");  //1472831999100
//        Date date = null;
//        try {
//            date = format.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Format To times:"+date);
//
//
//        Timestamp ts2;
//        try {
//            ts2 = Timestamp.valueOf(time);
//            System.out.println(ts2.getTime());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
//     if (false && true){
//         System.out.println(1);
//     }


//        String url = "http://www.fsceshi.com/open/work-order/h5/forward?appId={appId}&fs_nav_title={nav_title}&fs_nav_fsmenu=false&target=http://www.fsceshi.com/fsh5/ithelpdesk/5.3/index.html?fs_nav_fsmenu=false&fs_nav_title={nav_title}#/detail?orderId={orderId}&app=FRONTEND&appId={appId}#FS_OAUTH_CALLBACK_SIG";
//        String url2 = null;
//        try {
//            url2 = url.replaceAll("\\{nav_title\\}", URLEncoder.encode("中国", "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(url2);
//        String srcUrl = url2.substring(0, url2.indexOf("target=")+7);
//        System.out.println(srcUrl);
//        String targetUrl = url2.substring(url2.indexOf("target=")+7);
//        System.out.println(targetUrl);
//        String ss = Base64.getUrlEncoder().encodeToString(targetUrl.getBytes());
//        System.out.println(ss);
//        System.out.println(srcUrl+ss);
//        String aa = new String(Base64.getUrlDecoder().decode("0G60sB6wLm40000ZJ9ZbPIu80eKQP7phx1G0iMlQPC24s8fJOm7966odRUU8vqEot04iZi6zFKyXKWvVLsfp5NmxqTcyUqF3bwRWiQKRBrN6dS12gFfNw0N8V8WSXF4do4WVAoGlDU526XXd39zK1bVbMdJLhuBVg7I2XnHrAviQ8SM1E7cBqG3rtDM8iayBbvOcr0mIL5ozny2wTNy2OIWwpzIzlI1Kw7aWC2"));
//        System.out.println(aa);


        String s = "eyJkZXBhcnRtZW50IjoiZnnlk5ItT09PTyIsImVudGVycHJpc2VBY2NvdW50IjoiZmt0ZXN0IiwiZXhwaXJlZCI6ZmFsc2UsIm1vYmlsZSI6IjE4NTc2NjgzMDg5Iiwib3BlblVzZXJJZCI6IkZTVUlEXzIyMkM4MkVERTIyREU4QTI3OEE2RkNBRDY5MThEQkE1IiwidXBkYXRlVGltZSI6MTQ3NDU5OTcwNjgyNiwidXNlcklkIjoyODk3LCJ1c2VyTmFtZSI6Iuabueeni+iOslJpdGEifQ==";
        byte[] KEY = "fs.user@iT_".getBytes();
        String decode = new String(Base64.getDecoder().decode(s), Charsets.UTF_8);
        byte[] bytes = decode.getBytes(Charsets.UTF_8);
        for (byte b : bytes) {
            for (byte b1 : KEY) {
                b ^= b1;
            }
        }

        User user = JSON.parseObject(bytes, User.class);
        System.out.println(user);
    }

    class User implements Serializable {
        private static final long serialVersionUID = 8947750855364798998L;

        // 缓存失效时间: 1天(单位-毫秒)
        private static final long EXPIRE_MILLS = 24 * 60 * 60 * 1000;

        private Integer userId;
        private String enterpriseAccount;
        private String userName;
        private String department;
        private String mobile;
        private String openUserId;

        // 最后一次更新时间戳
        private long updateTime;

        public boolean isExpired() {
            return System.currentTimeMillis() - updateTime > EXPIRE_MILLS;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getEnterpriseAccount() {
            return enterpriseAccount;
        }

        public void setEnterpriseAccount(String enterpriseAccount) {
            this.enterpriseAccount = enterpriseAccount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getOpenUserId() {
            return openUserId;
        }

        public void setOpenUserId(String openUserId) {
            this.openUserId = openUserId;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("userId", userId)
                    .add("enterpriseAcount", enterpriseAccount)
                    .add("userName", userName)
                    .add("department", department)
                    .add("mobile", mobile)
                    .add("openUserId", openUserId)
                    .add("updateTime", updateTime)
                    .toString();
        }

    }
}
