package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.category.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<CategoryModel, Long> {
    CategoryModel findOneByIdAndIsDeleted(long id, boolean isDeleted);
}
