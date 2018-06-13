package acropollis.municipali.omega.database.db.service.report;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.database.db.converters.report.ReportDtoConverter;
import acropollis.municipali.omega.database.db.converters.report.ReportModelConverter;
import acropollis.municipali.omega.database.db.dao.ReportDao;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static java.lang.String.format;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ImageService imageService;

    @Override
    public List<Report> getAll() {
        return reportDao
                .findAll()
                .stream()
                .map(ReportModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Report> get(long id) {
        return Optional
                .ofNullable(reportDao.findOne(id))
                .map(ReportModelConverter::convert);
    }

    @Transactional
    @Override
    public void create(Report report, byte [] reportImage, User user) {
        long id = reportDao.save(ReportDtoConverter.convert(report)).getId();

        if (reportImage != null) {
            imageService.addImage(
                    config.getImagesReportsLocation(), format("%d", id), "report", reportImage
            );
        }
    }

    @Override
    public void delete(long id) throws EntityDoesNotExist {
        reportDao.delete(id);

        imageService.runInFtp(config.getImagesReportsLocation(), ftpClient ->
            imageService.removeAllImagesRemoveDirectory(ftpClient, format("%d", id))
        );
    }
}
