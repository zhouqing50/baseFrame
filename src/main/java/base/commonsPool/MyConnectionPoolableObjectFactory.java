package base.commonsPool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * User: zhouq
 * Date: 2016/5/23
 */


public class MyConnectionPoolableObjectFactory implements PoolableObjectFactory {
    private static Logger logger = LoggerFactory.getLogger(MyConnectionPoolableObjectFactory.class);

    private static int count = 0;

    public Object makeObject() throws Exception {
        MyConnection myConn = new MyConnection(generateName());
        logger.info(myConn.getName());
        myConn.connect();
        return myConn;
    }

    public void activateObject(Object obj) throws Exception {
        MyConnection myConn = (MyConnection)obj;
        logger.info(myConn.getName());
    }

    public void passivateObject(Object obj) throws Exception {
        MyConnection myConn = (MyConnection)obj;
        logger.info(myConn.getName());
    }

    public boolean validateObject(Object obj) {
        MyConnection myConn = (MyConnection)obj;
        logger.info(myConn.getName());
        return myConn.isConnected();
    }

    public void destroyObject(Object obj) throws Exception {
        MyConnection myConn = (MyConnection)obj;
        logger.info(myConn.getName());
        myConn.close();
    }

    private synchronized String generateName() {
        return "conn_" + (++count);
    }
}
