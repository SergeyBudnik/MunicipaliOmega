package acropollis.municipali.omega.admin.service.csv.answer;

import acropollis.municipali.omega.common.dto.answer.UserAnswer;
import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.utils.csv.CsvUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminCsvAnswerServiceImpl implements AdminCsvAnswerService {
    @Override
    public String produce(List<UserAnswer> userAnswers) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return CsvUtils.produce(
                Arrays.asList("Name", "Gender", "Email", "Date Of Birth", "Article Id", "Question Id", "Answer Id"),
                userAnswers,
                userAnswer -> {
                    List<String> res = new ArrayList<>();

                    UserDetailsInfo userDetailsInfo = userAnswer.getUser().getUserDetailsInfo();

                    res.add(userDetailsInfo.getName());
                    res.add(String.valueOf(userDetailsInfo.getUserGender()));
                    res.add(userDetailsInfo.getEmail());
                    res.add(userDetailsInfo.getDateOfBirth() != null ? sdf.format(userDetailsInfo.getDateOfBirth()): null);
                    res.add(String.valueOf(userAnswer.getArticleId()));
                    res.add(String.valueOf(userAnswer.getQuestionId()));
                    res.add(String.valueOf(userAnswer.getAnswerId()));

                    return res;
                }
        );
    }
}
