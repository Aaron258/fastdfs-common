package com.aaron.fastdfs.pool;

import com.aaron.fastdfs.FastDFSTemplateFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.csource.fastdfs.StorageClient;

/**
 * 
* @Description: 连接池工厂
* @author: Aaron
* @date: 2017年7月12日 下午6:58:09
 */
public class ConnectionPoolFactory {

	
    private GenericObjectPool<StorageClient> pool;


    public ConnectionPoolFactory(FastDFSTemplateFactory fastDFSTemplateFactory) {
        pool = new GenericObjectPool<>(new ConnectionFactory(fastDFSTemplateFactory));
    }

    public StorageClient getClient() throws Exception {
        return pool.borrowObject();
    }
    
    public void releaseConnection(StorageClient client) {
        try {
            pool.returnObject(client);
        } catch (Exception ignored) {
        }
    }

    /**
     * 
    * @Description: 修改属性
    * @param poolConfig
    * @author: Aaron
    * @date: 2017年7月12日 下午6:59:02
     */
    @SuppressWarnings("unused")
	private void toConfig(PoolConfig poolConfig) {
        pool.setMaxTotal(poolConfig.maxTotal);
        pool.setMaxIdle(poolConfig.maxIdle);
        pool.setMinIdle(poolConfig.minIdle);
        pool.setTestOnBorrow(poolConfig.testOnBorrow);
        pool.setMaxWaitMillis(poolConfig.maxWait);
    }

}