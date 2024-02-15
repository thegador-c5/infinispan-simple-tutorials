package org.infinispan.tutorial.simple.remote;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
    
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;

import static org.infinispan.tutorial.simple.remote.MyTutorialsConnectorHelper.TUTORIAL_CACHE_NAME;

/**
 *
 * Infinispan Server includes a default property realm that requires
 * authentication. Create some credentials before you run this tutorial.
 *
 */
public class MyInfinispanRemoteCache {
    
    public static void main(String[] args) {
        // Connect to the server
        RemoteCacheManager cacheManager = MyTutorialsConnectorHelper.connect();
        // Obtain the remote cache
        RemoteCache<String, String> cache = cacheManager.getCache(TUTORIAL_CACHE_NAME);
        /// Store a value
        cache.put("mykey", "myvalue");
        // Retrieve the value and print it out
        System.out.printf("key = %s\n", cache.get("mykey"));
        // Stop the cache manager and release all resources
        cacheManager.stop();
    }
    
}




/**
 * My Utility class for the simple tutorials in client server mode.
 *
 * @author Katia Aresti, karesti@redhat.com
 */
class MyTutorialsConnectorHelper {

   public static final String USER = "admin";
   public static final String PASSWORD = "password";
   public static final String HOST = "127.0.0.1";
   public static final int SINGLE_PORT = ConfigurationProperties.DEFAULT_HOTROD_PORT;

   public static final String TUTORIAL_CACHE_NAME = "mytest";
   public static final String TUTORIAL_CACHE_CONFIG =
         "<distributed-cache name=\"CACHE_NAME\">\n"
         + "    <encoding media-type=\"application/x-protostream\"/>\n"
         + "</distributed-cache>";

   /**
    * Returns the configuration builder with the connection information
    *
    * @return a Configuration Builder with the connection config
    */
   public static final ConfigurationBuilder connectionConfig() {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer().host(HOST).port(SINGLE_PORT).security()
            .authentication()
            //Add user credentials.
            .username(USER)
            .password(PASSWORD);

      // Docker 4 Mac Workaround. Don't use BASIC intelligence in production
      builder.clientIntelligence(ClientIntelligence.BASIC);

      // Make sure the remote cache is available.
      // If the cache does not exist, the cache will be created
      builder.remoteCache(TUTORIAL_CACHE_NAME)
            .configuration(TUTORIAL_CACHE_CONFIG.replace("CACHE_NAME", TUTORIAL_CACHE_NAME));
      return builder;
   }

   /**
    * Connect to the running Infinispan Server in localhost:11222.
    *
    * This method illustrates how to connect to a running Infinispan Server with a downloaded
    * distribution or a container.
    *
    * @return a connected RemoteCacheManager
    */
   public static final RemoteCacheManager connect() {
      // Return the connected cache manager
      return connect(connectionConfig());
   }

   public static final RemoteCacheManager connect(ConfigurationBuilder builder) {
      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

      // Clear the cache in case it already exists from a previous running tutorial
      cacheManager.getCache(TUTORIAL_CACHE_NAME).clear();

      // Return the connected cache manager
      return cacheManager;
   }

}
