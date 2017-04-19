package acropollis.municipali.omega.admin.data.request.statistics;

import lombok.Data;

@Data
public class GetQuestionStatisticsRequest {
    private long articleId;
    private long questionId;
    private long from, to;
}
