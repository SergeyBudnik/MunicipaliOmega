package acropollis.municipali.omega.article;

import acropollis.municipali.omega.AppConfig;
import acropollis.municipali.omega.admin.rest.AdminArticleResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ArticleComponentTest {
    @Autowired
    private AdminArticleResource adminArticleResource;

    @Test
    public void tryIt() {
        adminArticleResource.deleteArticle("asd", 1);
    }
}
