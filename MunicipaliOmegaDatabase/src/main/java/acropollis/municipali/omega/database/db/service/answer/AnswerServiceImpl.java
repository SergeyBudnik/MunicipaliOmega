package acropollis.municipali.omega.database.db.service.answer;

import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class AnswerServiceImpl implements AnswerService {
    public Optional<Answer> get(Question question, long answerId) {
        return question
                .getAnswers()
                .stream()
                .filter(it -> it.getId().equals(answerId))
                .findAny();
    }

    @Override
    public Optional<byte[]> getIcon(Question question, long answerId, int size) {
        if (!get(question, answerId).isPresent()) {
            return Optional.empty();
        }

        return EntityImageStorageUtils.getImage(config.getString("images.answers"), answerId, size, size);
    }
}
