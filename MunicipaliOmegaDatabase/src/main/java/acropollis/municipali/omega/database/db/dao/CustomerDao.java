package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<CustomerModel, String> {
    CustomerModel findOneByLoginAndPassword(String login, String password);
}
