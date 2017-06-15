package acropollis.municipali.omega.database.db.service.report;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.*;

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
                        config.getImagesReportsLocation().getValue(),
                        id,
                        -1, -1
                );
    }

    @Transactional
    @Override
    public void create(Report report, byte [] reportImage, User user) {
        long id = reportDao.save(ReportDtoConverter.convert(report)).getId();

        if (reportImage != null) {
            EntityImageStorageUtils
                    .saveImages(
                            config.getImagesReportsLocation().getValue(),
                            id,
                            SquareImageAdapter.pack(Collections.singletonMap(-1, reportImage))
                    );
        }
    }

    @Override
    public void delete(long id) throws EntityDoesNotExist {
        reportDao.delete(id);

        EntityImageStorageUtils
                .removeImages(
                        config.getImagesReportsLocation().getValue(),
                        id
                );
    }
}
