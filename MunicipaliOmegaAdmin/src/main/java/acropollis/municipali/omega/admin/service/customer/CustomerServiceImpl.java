package acropollis.municipali.omega.admin.service.customer;

import acropollis.municipali.omega.admin.data.converters.customer.CustomerDtoConverter;
import acropollis.municipali.omega.admin.data.converters.customer.CustomerModelConverter;
import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.database.db.dao.CustomerDao;
import acropollis.municipali.omega.common.exceptions.EntityAlreadyExistsException;
import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> getCustomer(String login) {
        return Optional
                .ofNullable(customerDao.findOne(login))
                .map(CustomerModelConverter::convert);
    }

    @Override
    public Optional<Customer> getCustomer(String login, String password) {
        return Optional
                .ofNullable(customerDao.findOneByLoginAndPassword(login, password))
                .map(CustomerModelConverter::convert);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Customer> getAllCustomers() {
        return customerDao
                .findAll()
                .stream()
                .map(CustomerModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    @Override
    public void createCustomer(Customer customer) {
        if (customerDao.exists(customer.getLogin())) {
            throw new EntityAlreadyExistsException("");
        }

        customerDao.save(CustomerDtoConverter.convert(customer));
    }

    @Transactional(readOnly = false)
    @Override
    public void updateCustomer(Customer customer) {
        if (!customerDao.exists(customer.getLogin())) {
            throw new EntityNotFoundException("");
        }

        customerDao.save(CustomerDtoConverter.convert(customer));
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteCustomer(String login) {
        if (!customerDao.exists(login)) {
            throw new EntityNotFoundException("");
        }

        customerDao.delete(login);
    }
}
