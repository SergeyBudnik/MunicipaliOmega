package acropollis.municipali.omega.user.rest_service.report;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.user.data.converter.report.ReportDtoConverter;
import acropollis.municipali.omega.user.data.dto.report.Report;
import acropollis.municipali.omega.database.db.dao.ReportDao;
import acropollis.municipali.omega.user.image.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class ReportRestServiceImpl implements ReportRestService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional(readOnly = false)
    @Override
    public void postReport(Report report, byte[] reportImage) {
        long id = reportDao.save(ReportDtoConverter.convert(report)).getId();

        imageStorageService.saveImages(
                PropertiesConfig.config.getString("images.reports"),
                id,
                Collections.singletonMap(-1, reportImage)
        );
    }
}
