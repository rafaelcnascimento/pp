
package cliente;

import java.io.IOException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Cliente {

   
    public static void main(String[] args) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        
        json.put("nome", "Rafael");    
        json.put("email", "r");    
        json.put("cc", "1111222233334444");    
        json.put("senha", "1");    

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("http://localhost:8082/pp/web/cadastro");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        // handle response here...
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            httpClient.close();
        }
    }
    
}
