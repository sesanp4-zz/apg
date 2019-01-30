/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author centricgateway
 */
public class Test {
    
    
    
    
   
    public static void main(String[] args) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        
        CloseableHttpClient client ;
            //= HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    JsonObject obj;
    
    Dao dao = new Dao();
    
    SSLContext cont;
    
    SSLConnectionSocketFactory sslsf;
    SSLContext sslcontext;    
        
        
                 KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(new File("testclient.FCMBL.test.pfx"));
        ks.load(fis, "M@Dr1d321*".toCharArray());
        
        sslcontext = SSLContexts.custom()
        .loadKeyMaterial(ks, "password".toCharArray()).build();
        
        
        sslsf = new SSLConnectionSocketFactory(
        sslcontext,
        new String[] { "TLSv1" },
        null,
        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); //TODO
        client = HttpClients.custom()
        .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER) //TODO
        .setSSLSocketFactory(sslsf)
        .build();  
        
           post = new HttpPost("https://dev.afripayway.com/api/common/v1/getListCountry");
           post.setHeader("Content-Type", "application/json");
           post.setHeader("auth", "cb7a4d08d9e54121a60713644d054892987aa04fd350a8df5fb54db0d1e0d5b0"); 
           obj= new JsonObject();
           obj.addProperty("continent", "AF");
           StringEntity ent = new StringEntity(obj.toString());
           post.setEntity(ent);
           response=client.execute(post);
           System.out.println(response.getStatusLine());
           String msg = EntityUtils.toString(response.getEntity());
           System.out.println(msg);

        
           }
}
