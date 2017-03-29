package acropollis.municipali.omega.user_notification.async;

import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.dao.UserDao;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import acropollis.municipali.omega.user_notification.converter.article.ArticleModelConverter;
import acropollis.municipali.omega.user_notification.dto.article.Article;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class ReleasedArticlesNotificationJob {
    private static final String SERVER_KEY = "AAAAKOSHdJU:APA91bFw9odWdNDl1sUJZygtClYqrgbQTjVDVDALGV2AHITEYPSRpBX5kMxgflutZUzoSr3NO6jl8VyAFq6lYoswzdpX6jeE523vOfB1X_7v1e2xNQ0KoQ2gyg1vUMFmHYofpseEskp9";

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao; /* Read from cache */

    private long lastReloadDate = -1;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
//        long currentDate = new Date().getTime();
//
//        if (lastReloadDate != -1) {
//            Collection<String> gmsTokens =
//                    userDao.findAll().stream().map(UserModel::getGmsToken).collect(Collectors.toList());
//
//            articleDao
//                    .findByReleaseDateLessThanEqualAndReleaseDateGreaterThanEqualAndIsDeleted(lastReloadDate, currentDate, false)
//                    .stream()
//                    .map(ArticleModelConverter::convert)
//                    .forEach(it ->
//                            gmsTokens
//                                    .forEach(gmsToken -> sendPush(it, gmsToken))
//                    );
//        }
//
//        lastReloadDate = currentDate;
    }

    private void sendPush(Article article, String gmsToken) {
        try {
            WebResource resource = Client.create().resource("https://gcm-http.googleapis.com/gcm/send");

            resource
                    .header("Authorization", "key=" + SERVER_KEY)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsString(article));
        } catch (IOException e) {
            /* Do nothing */
        }
    }
}
