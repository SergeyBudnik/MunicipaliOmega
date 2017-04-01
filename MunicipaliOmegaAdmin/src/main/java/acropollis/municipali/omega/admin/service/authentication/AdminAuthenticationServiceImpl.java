package acropollis.municipali.omega.admin.service.authentication;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerCredentials;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerToken;
import acropollis.municipali.omega.admin.service.customer.AdminCustomerService;
import acropollis.municipali.omega.common.exceptions.CredentialsViolationException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {
    @Data
    private static class CustomerInfoAndToken {
        CustomerInfo customerInfo;
        CustomerToken customerToken;
    }

    @Autowired
    private AdminCustomerService adminCustomerService;

    private Random r = new Random();

    private Map<String, CustomerInfoAndToken> customerInfoAndTokenByToken = new ConcurrentHashMap<>();

    @Override
    public Optional<CustomerToken> login(CustomerCredentials customerCredentials) {
        Optional<Customer> customer = adminCustomerService.getCustomer(
                customerCredentials.getLogin(),
                customerCredentials.getPassword()
        );

        if (!customer.isPresent()) {
            return Optional.empty();
        }

        CustomerInfoAndToken customerInfoAndToken = new CustomerInfoAndToken(); {
            customerInfoAndToken.setCustomerToken(new CustomerToken());

            customerInfoAndToken.getCustomerToken().setToken(generateAuthToken());
            customerInfoAndToken.getCustomerToken().setValidTill(new Date().getTime() + 7 * 24 * 60 * 60 * 1000l);

            customerInfoAndToken.setCustomerInfo(new CustomerInfo());

            customerInfoAndToken.getCustomerInfo().setLogin(customerCredentials.getLogin());
        }

        customerInfoAndTokenByToken.put(
                customerInfoAndToken.getCustomerToken().getToken(), customerInfoAndToken);

        return Optional.of(customerInfoAndToken.getCustomerToken());
    }

    @Override
    public void logoff(String token) {
        customerInfoAndTokenByToken.remove(token);
    }

    @Override
    public Optional<CustomerInfo> getCustomerInfo(String token) {
        return token == null ?
                Optional.empty() :
                Optional
                        .ofNullable(customerInfoAndTokenByToken.get(token))
                        .map(CustomerInfoAndToken::getCustomerInfo);
    }

    @Override
    public CustomerInfo getCustomerInfoOrThrow(String token) {
        return getCustomerInfo(token).orElseThrow(() -> new CredentialsViolationException(""));
    }

    private String generateAuthToken() {
        return new BigInteger(130, r).toString(32);
    }
}
