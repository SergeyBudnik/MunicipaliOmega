package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<CustomerModel, String> {
    public CustomerModel findOneByLoginAndPassword(String login, String password);
}
