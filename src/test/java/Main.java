import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Main {

    public static HttpResponse GetSuggests(String query, int limit, String lang, String locale, String currency) throws IOException {
        HttpResponse response = Request.Get(
            String.format("https://www.onetwotrip.com/_hotels/api/suggestRequest?query=%s&limit=%d&lang=%s&locale=%s&currency=%s", query, limit, lang, locale, currency))
            .execute()
            .returnResponse();

        return response;
    }

    @Test
    public void testSuggestsPositive() {
        try {

            HttpResponse response = GetSuggests("moscow", 7, "ru", "ru", "RUB"); //делаем запрос
            String json = EntityUtils.toString(response.getEntity()); //получаем body из респонса и кладем в строку

            //парсим json
            ObjectMapper mapper = new ObjectMapper();
            ApiResponseBody apiResponseBody = mapper.readValue(json, ApiResponseBody.class);

            Assert.assertEquals("status code is not 200", 200, response.getStatusLine().getStatusCode());
            System.out.println("status code is 200");

            Assert.assertEquals("error is not null", null, apiResponseBody.error);
            System.out.println("error is null");

            Assert.assertTrue("result is null", (apiResponseBody.result.length > 0 && apiResponseBody.result[0].id != null));
            System.out.println("result is not null");

        }
        catch (IOException ex) {
            Assert.fail();
        }
    }
}
