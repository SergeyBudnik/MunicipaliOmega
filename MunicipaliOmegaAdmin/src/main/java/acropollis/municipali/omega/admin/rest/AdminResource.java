package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.exceptions.HttpCredentialsViolationException;
import acropollis.municipali.security.client.admin.MunicipaliSecurityAdminClient;
import acropollis.municipali.security.client.admin.MunicipaliSecurityAdminClientImpl;
import acropollis.municipali.security.client.exceptions.MunicipaliSecurityCredentialsViolationException;
import acropollis.municipali.security.client.exceptions.MunicipaliSecurityNetworkException;
import acropollis.municipali.security.client.user.MunicipaliSecurityUserClient;
import acropollis.municipali.security.client.user.MunicipaliSecurityUserClientImpl;
import acropollis.municipali.security.common.dto.MunicipaliUser;
import acropollis.municipali.security.common.dto.MunicipaliUserCredentials;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import acropollis.municipali.security.common.dto.MunicipaliUserToken;

import java.util.List;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

public abstract class AdminResource {
    private final MunicipaliSecurityUserClient securityUserClient =
            new MunicipaliSecurityUserClientImpl();

    private final MunicipaliSecurityAdminClient securityAdminClient =
            new MunicipaliSecurityAdminClientImpl();

    MunicipaliUserToken login(MunicipaliUserCredentials credentials) {
        try {
            return securityUserClient.login(
                    config.getSecurityServiceRootUrl(),
                    credentials
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    void logoff(String authToken) {
        try {
            securityUserClient.logout(
                    config.getSecurityServiceRootUrl(),
                    authToken
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    MunicipaliUserInfo getUserInfo(String authToken) {
        try {
            return securityUserClient.getUserInfo(
                    config.getSecurityServiceRootUrl(),
                    authToken
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    List<MunicipaliUserInfo> getAllCustomers(String authToken) {
        try {
            return securityAdminClient.getAllUsers(
                    config.getSecurityServiceRootUrl(),
                    authToken
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    MunicipaliUserInfo getCustomer(String authToken, String login) {
        try {
            return securityAdminClient.getUser(
                    config.getSecurityServiceRootUrl(),
                    authToken,
                    login
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    void createCustomer(String authToken, MunicipaliUser user) {
        try {
            securityAdminClient.createClient(
                    config.getSecurityServiceRootUrl(),
                    authToken,
                    user
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    void editCustomer(String authToken, MunicipaliUser user) {
        try {
            securityAdminClient.editClient(
                    config.getSecurityServiceRootUrl(),
                    authToken,
                    user
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }

    void deleteCustomer(String authToken, String login) {
        try {
            securityAdminClient.deleteClient(
                    config.getSecurityServiceRootUrl(),
                    authToken,
                    login
            );
        } catch (MunicipaliSecurityNetworkException e) {
            throw new RuntimeException(); // ToDo
        } catch (MunicipaliSecurityCredentialsViolationException e) {
            throw new HttpCredentialsViolationException("");
        }
    }
}
