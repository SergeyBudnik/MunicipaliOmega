package acropollis.municipali.omega.database.db.converters.answer;

import acropollis.municipali.omega.database.db.converters.user.UserModelConverter;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;

public class UserAnswerModelConverter {
    public static UserAnswer convert(UserAnswerModel userAnswerModel, UserModel userModel) {
        UserAnswer userAnswer = new UserAnswer();

        userAnswer.setArticleId(userAnswerModel.getArticleId());
        userAnswer.setQuestionId(userAnswerModel.getQuestionId());
        userAnswer.setAnswerId(userAnswerModel.getAnswerId());
        userAnswer.setUser(UserModelConverter.convert(userModel));

        return userAnswer;
    }
}
