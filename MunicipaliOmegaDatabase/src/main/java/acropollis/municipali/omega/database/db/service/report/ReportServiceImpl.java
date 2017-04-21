package acropollis.municipali.omega.database.db.service.report;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils;
import acropollis.municipali.omega.common.utils.storage.SquareImageAdapter;
import acropollis.municipali.omega.database.db.converters.report.ReportDtoConverter;
import acropollis.municipali.omega.database.db.converters.report.ReportModelConverter;
import acropollis.municipali.omega.database.db.dao.ReportDao;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

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

    @Override
    public Optional<byte []> getPhoto(long id) {
        return EntityImageStorageUtils
                .getImage(
                        PropertiesConfig.config.getString("images.reports"),
                        id,
                        -1, -1
                );
    }

    @Override
    public void create(Report report, byte [] reportImage, User user) {
        long id = reportDao.save(ReportDtoConverter.convert(report)).getId();

        EntityImageStorageUtils
                .saveImages(
                        PropertiesConfig.config.getString("images.reports"),
                        id,
                        SquareImageAdapter.pack(Collections.singletonMap(-1, reportImage))
                );
    }

    @Override
    public void delete(long id) throws EntityDoesNotExist {
        reportDao.delete(id);

        EntityImageStorageUtils
                .removeImages(
                        PropertiesConfig.config.getString("images.reports"),
                        id
                );
    }
}
