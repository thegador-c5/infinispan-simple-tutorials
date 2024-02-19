package org.infinispan.tutorial.simple.remote;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
    
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;

import static org.infinispan.tutorial.simple.remote.MyTutorialsConnectorHelper.TUTORIAL_CACHE_NAME;

import org.infinispan.client.hotrod.configuration.SaslQop;
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

   public static final String OCP_USER = "developer";
   public static final String OCP_PASSWORD = "eQWIAItdhDGHWgqG";
   public static final String OCP_REALM = "default";
   public static final String OCP_HOST = 
      "example-infinispan-external-openshift-operators.apps.cluster-5pltp.5pltp.sandbox1298.opentlc.com";
   public static final String OCP_TRUSTSTORE = 
      "/home/thegador/Data/technical/Data-Grid/smbc/mytruststore.jks";
   // "/home/thegador/Data/technical/Data-Grid/smbc/tls.crt";

   public static final String OCP_SERVICE_HOSTNAME = 
      "example-infinispan.openshift-operators.svc";
   // "example-infinispan.openshift-operators.svc.cluster.local";


   //public static final int SINGLE_PORT = ConfigurationProperties.DEFAULT_HOTROD_PORT;
   public static final int SINGLE_PORT = 80;

   public static final String TUTORIAL_CACHE_NAME = "mytest2";
   public static final String TUTORIAL_CACHE_CONFIG =
         "<distributed-cache name=\"CACHE_NAME\">\n"
         + "    <encoding media-type=\"application/x-protostream\"/>\n"
         + "</distributed-cache>";

   /*
    * Custom method to connect to OpenShift
    */
   public static final ConfigurationBuilder ocpConnectionConfig() {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      
      System.out.println(builder.toString());

      /*
      builder.addServer().host(OCP_HOST).port(SINGLE_PORT).security()
            .authentication()
            //Add user credentials.
            .username(OCP_USER)
            .password(OCP_PASSWORD);
       */

      // OCP Route only works with BASIC
      builder.clientIntelligence(ClientIntelligence.BASIC)
       .addServer()
       .host("example-infinispan-external-openshift-operators.apps.cluster-5pltp.5pltp.sandbox1298.opentlc.com")
       .port(443)
       .security().authentication()
       .username(OCP_USER)
       .password(OCP_PASSWORD)
       .saslMechanism("SCRAM-SHA-512")
       .ssl()
       // disable hostname velidation when DG uses operator-generated cert
       .hostnameValidation(false)
       // get truststore from one of the Infinispan pods
       // oc exec <podname> -- cat /var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt > /tmp/service-ca.crt
       .trustStoreFileName("/tmp/service-ca.crt")
       .trustStoreType("pem");

      // Docker 4 Mac Workaround. Don't use BASIC intelligence in production
      //builder.clientIntelligence(ClientIntelligence.BASIC);

      // Make sure the remote cache is available.
      // If the cache does not exist, the cache will be created
      builder.remoteCache(TUTORIAL_CACHE_NAME)
            .configuration(TUTORIAL_CACHE_CONFIG.replace("CACHE_NAME", TUTORIAL_CACHE_NAME));
      System.out.print(builder.toString());
      return builder;
   }


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
      // return connect(connectionConfig());

      // Use custom OCP connection method
      return connect(ocpConnectionConfig());

   }

   public static final RemoteCacheManager connect(ConfigurationBuilder builder) {
      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

      System.out.print(builder.toString());

      // Clear the cache in case it already exists from a previous running tutorial
      cacheManager.getCache(TUTORIAL_CACHE_NAME).clear();

      // Return the connected cache manager
      return cacheManager;
   }

}
