import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Main {

    @Test
    public void main() {

        try {

            HttpResponse response = Request.Get(
                    "https://www.onetwotrip.com/_hotels/api/suggestRequest?query=moscow&limit=7&lang=ru&locale=ru&currency=RUB")
                    .execute()
                    .returnResponse();

            Assert.assertEquals("status code is not 200", 200, response.getStatusLine().getStatusCode());
            System.out.println("status code is 200");

            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            ApiResponseBody apiResponseBody = mapper.readValue(json, ApiResponseBody.class);

            Assert.assertEquals("error is not null", null, apiResponseBody.error);
            System.out.println("error is null");

            Assert.assertTrue("result is null", (apiResponseBody.result.length > 0 && apiResponseBody.result[0].id != null));
            System.out.println("result is not null");

        }
        catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
