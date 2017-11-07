package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.interfaces.service.GameUrlImageService;
import ar.edu.itba.paw.model.Game;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class PlayerMeController {

    @Autowired
    private GameService gs;

    @Autowired
    private GameUrlImageService guis;

    public Game addGameImage(String gameName) {

        if(gameName == null) {
            gameName = "";
        }
        Game game = gs.findByName(gameName);
        if(game == null) {
            game = gs.create(gameName, true);
            StringBuilder url = new StringBuilder("https://player.me/api/v1/search?sort=popular&order=desc&_limit=1&q=");
            String[] gameWords = gameName.split(" ");
            int size = gameWords.length;
            int i = 0;
            for( ; i < size-1 ; i++) {
                url = url.append(gameWords[i] + "%20");
            }
            url = url.append(gameWords[i]);

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpGet request = new HttpGet(url.toString());
                request.addHeader("content-type", "application/json");
                HttpResponse result = httpClient.execute(request);
                try {
                    String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                    JSONObject obj = new JSONObject(json);

                    if (obj != null) {
                        String title = obj.getJSONArray("results").getJSONObject(0).getString("title");
                        if (title != null) {
                            if (title.equals(gameName)) {
                                String cover = obj.getJSONArray("results").getJSONObject(0).getJSONObject("cover").getString("original");
                                if (cover != null) {
                                    StringBuilder urlImage = new StringBuilder("https:");
                                    String[] arg = cover.split("\\\\");
                                    for (String part : arg) {
                                        urlImage = urlImage.append(part);
                                    }
                                    guis.create(game, urlImage.toString());
                                }
                            }
                        }
                    }
                } catch (org.json.JSONException e) {

                }

            } catch (IOException ex) {

            }
        }
        return game;
    }
}
