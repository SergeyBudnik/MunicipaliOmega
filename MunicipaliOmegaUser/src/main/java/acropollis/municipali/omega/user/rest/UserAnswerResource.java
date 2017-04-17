package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import acropollis.municipali.omega.user.rest_service.answer.AnswerRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/answer")
@Api(tags = "Answers", description = " ")
public class UserAnswerResource {
    @Autowired
    private AnswerRestService answerRestService;

    @RequestMapping(value = "/{articleId}/{questionId}", method = RequestMethod.GET)
    public Map<Long, Long> getAnswersStatistics(@PathVariable long articleId, @PathVariable long questionId) {
        return answerRestService.getAnswerStatistics(articleId, questionId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void answer(@RequestBody UserAnswer answer) {
        answerRestService.answer(answer);
    }
}
