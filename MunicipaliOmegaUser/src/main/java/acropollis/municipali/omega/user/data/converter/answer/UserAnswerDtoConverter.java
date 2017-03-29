package acropollis.municipali.omega.user.data.converter.answer;

import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModelId;

public class UserAnswerDtoConverter {
    public static UserAnswerModel convert(UserAnswer userAnswer) {
        UserAnswerModel userAnswerModel = new UserAnswerModel();

        userAnswerModel.setId(new UserAnswerModelId()); {
            userAnswerModel.getId().setUserAuthToken(userAnswer.getUserAuthToken());
            userAnswerModel.getId().setArticleId(userAnswer.getArticleId());
            userAnswerModel.getId().setQuestionId(userAnswer.getQuestionId());
        }
        userAnswerModel.setAnswerId(userAnswer.getAnswerId());

        return userAnswerModel;
    }
}
