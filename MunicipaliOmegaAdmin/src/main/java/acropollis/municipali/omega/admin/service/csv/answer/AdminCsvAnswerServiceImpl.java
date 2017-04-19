package acropollis.municipali.omega.admin.service.csv.answer;

import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.utils.csv.CsvUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminCsvAnswerServiceImpl implements AdminCsvAnswerService {
    @Override
    public String produce(List<UserAnswerStatisticsCsvRow> rows) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return CsvUtils.produce(
                Arrays.asList("Name", "Gender", "Email", "Date Of Birth", "Article", "Question", "Answer"),
                rows,
                row -> {
                    List<String> res = new ArrayList<>();

                    Optional<UserDetailsInfo> userDetailsInfo = row.getUser().map(User::getUserDetailsInfo);

                    res.add(userDetailsInfo
                            .map(UserDetailsInfo::getName)
                            .flatMap(Optional::ofNullable)
                            .orElse("?")
                    );

                    res.add(userDetailsInfo
                            .map(UserDetailsInfo::getUserGender)
                            .flatMap(Optional::ofNullable)
                            .map(String::valueOf)
                            .orElse("?")
                    );

                    res.add(userDetailsInfo
                            .map(UserDetailsInfo::getEmail)
                            .flatMap(Optional::ofNullable)
                            .orElse("?")
                    );

                    res.add(userDetailsInfo
                            .map(UserDetailsInfo::getDateOfBirth)
                            .flatMap(Optional::ofNullable)
                            .map(sdf::format)
                            .orElse("?")
                    );

                    res.add(row.getArticle().getTranslatedArticle().get(Language.ENGLISH).getTitle());
                    res.add(row.getQuestion().getTranslatedQuestion().get(Language.ENGLISH).getText());
                    res.add(getAnswerText(row.getQuestion(), row.getAnswer()));

                    return res;
                }
        );
    }

    private static String getAnswerText(Question question, Answer answer) {
        switch (question.getAnswerType()) {
            case FIVE_POINTS:
                return getFivePointsAnswerText(question, answer);
            case DYCHOTOMOUS:
            case THREE_VARIANTS:
                return answer.getTranslatedAnswer().get(Language.ENGLISH).getText();
        }

        throw new RuntimeException("Unsupported question answer type");
    }

    private static String getFivePointsAnswerText(Question question, Answer answer) {
        for (int i = 0; i < 5; i++) {
            if (question.getAnswers().get(i).getId().equals(answer.getId())) {
                return String.valueOf(i + 1);
            }
        }

        throw new RuntimeException("Illegal answer id");
    }
}
