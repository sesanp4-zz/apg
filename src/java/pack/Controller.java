/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Status;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("v1")
public class Controller {

    @Context
    private UriInfo context;
    
    @Context HttpHeaders header;
    
    static CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    JsonObject obj;
    
    Dao dao = new Dao();
    
    SSLContext cont;
    
    SSLConnectionSocketFactory sslsf;
    SSLContext sslcontext;
    
    /*
    url :https://dev.afripayway.com/api/common/v1/getListCountry
    "consumerId", "IB8XRXFB53DMIA44"
    "secretKey", ".#E;W+!IW;+7!@9[:U6XR-1!KE&-%=R+"
    for staging
    */
    
    Logger mylog = Logger.getLogger("Controller.class");

    /**
     * Creates a new instance of Controller
     */
    public Controller() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, FileNotFoundException, IOException, CertificateException, UnrecoverableKeyException {
 
//         sslcontext = SSLContexts.custom().useSSL().build();
//        sslcontext.init(null, new X509TrustManager[]{new ServertTrustManager()}, new SecureRandom());
//        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        client = HttpClients.custom().setSSLSocketFactory(factory).build();
        
    }

    /**
     * Retrieves representation of an instance of pack.Controller
     * @return an instance of java.lang.String
     */
    
//        public static HttpClient getHttpsClient() throws Exception {
//
//        if (client != null) {
//            return client;
//        }
//        SSLContext sslcontext = SSLContexts.custom().useSSL().build();
//        sslcontext.init(null, new X509TrustManager[]{new ServertTrustManager()}, new SecureRandom());
//        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        client = HttpClients.custom().setSSLSocketFactory(factory).build();
//
//        return client;
//    }
    
    
         @POST
         @Path("get/countrylist")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String GetCountry(String req) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException{
     
      /*   sslcontext = SSLContexts.custom().useSSL().build();
        sslcontext.init(null, new X509TrustManager[]{new ServertTrustManager()}, new SecureRandom());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        client = HttpClients.custom().setSSLSocketFactory(factory).build();
             
         */
      
           //post = new HttpPost("https://dev.afripayway.com/api/common/v1/getListCountry");
           post = new HttpPost("https://prewservices.afripayway.com/api/common/v1/getListCountry");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("auth",header.getHeaderString("Authorization") );          
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
           System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"=================Calling Get Country List=====================");
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;             
         }
    
    
         @GET
         @Path("/check")
         @Produces(MediaType.TEXT_PLAIN)
         public String testapgac() throws IOException{
            
             System.out.println("********************Starting ACCESS test*********************");
             get = new HttpGet("https://prewservices.afripayway.com/api/gateway/operations/v1/checkservice");
             response = client.execute(get);
             String msg = EntityUtils.toString(response.getEntity());
             System.out.println("********  MSG********"+msg);
             return msg;
         }
         
         @POST
         @Path("/status")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String postStatus(Status status) throws IOException{
          obj = new JsonObject();
          obj.addProperty("message", dao.postStatus(status));
          mylog.log(Level.INFO,"================Calling status endpoint======================");
           mylog.logp(Level.INFO, "Controller.class", "Response", obj.toString());
          return obj.toString();
         }
         
         @POST
         @Path("/token")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String getapgtoken() throws IOException{          
           post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/authenticate");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("consumerId", "EWFA3RGL4GE6U2P1");
           post.setHeader("secretKey", ".P:T(%W@.WLUU!+)3+P[&@#@+BXF+7=+");      
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
          @POST
         @Path("/Bank/To/Cash")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String BankToCash(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("auth",header.getHeaderString("Authorization") ); 
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
         
          @POST
         @Path("/bank/To/bank")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String BankToBank(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
          post.setHeader("auth",header.getHeaderString("Authorization") );    
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
         
          @POST
         @Path("/bank/To/wallet")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String BankToWallet(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
            post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("auth",header.getHeaderString("Authorization") );  
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
         @POST
         @Path("wallet/To/bank")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String WalletToBank(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
          post.setHeader("auth",header.getHeaderString("Authorization") ); 
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
           System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;             
         }
         
         
          @POST
         @Path("/bank/To/card")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String BankToCard(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/api/gateway/operations/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
          post.setHeader("auth",header.getHeaderString("Authorization") );        
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
         @POST
         @Path("get/balance")
         @Produces(MediaType.APPLICATION_JSON)
         public String getBalance() throws IOException, NoSuchAlgorithmException, KeyManagementException{
             
             
         /*    sslcontext = SSLContexts.custom().useSSL().build();
        sslcontext.init(null, new X509TrustManager[]{new ServertTrustManager()}, new SecureRandom());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        client = HttpClients.custom().setSSLSocketFactory(factory).build();
          */
         
           //obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/api/common/v1/getPartnerBalance");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("auth",header.getHeaderString("Authorization") );        
           //StringEntity ent = new StringEntity(req);
           //post.setEntity(ent);
           response=client.execute(post);
             System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           //mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
         
         
         @POST
         @Path("card/To/bank")
         @Consumes(MediaType.APPLICATION_JSON)
         @Produces(MediaType.APPLICATION_JSON)
         public String CardToBank(String req) throws IOException{
           obj = new JsonParser().parse(req).getAsJsonObject();          
           post = new HttpPost("https://prewservices.afripayway.com/card/switch/cardmanagement/v1/sendOperation");
           post.setHeader("Content-Type", "application/json");
          post.setHeader("auth",header.getHeaderString("Authorization") );      
           StringEntity ent = new StringEntity(req);
           post.setEntity(ent);
           response=client.execute(post);
           System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           mylog.log(Level.INFO,"======================================");
           mylog.log(Level.INFO,"Request", req);
           mylog.logp(Level.INFO, "Controller.class", "Response", msg);
           return msg;

             
         }
         
    
}
