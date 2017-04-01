package acropollis.municipali.omega.tests.tests.admin;

import acropollis.municipali.omega.tests.steps.admin.AdminCustomerSteps;
import acropollis.municipali.omega.tests.steps.admin.AdminLoginSteps;
import acropollis.municipali.omega.tests.tests.BaseComponentTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminLoginComponentTest extends BaseComponentTest {
    @Autowired
    private AdminLoginSteps adminLoginSteps;
    @Autowired
    private AdminCustomerSteps adminCustomerSteps;

    @Test
    public void testLoginWithExistingUser() throws Exception {
        adminCustomerSteps.createCustomer("login", "password");

        adminLoginSteps.login(getMockMvc(), "login", "password");
    }

    @Test
    public void testLoginWithNotExistingUser() throws Exception {
        adminLoginSteps.failedLogin(getMockMvc(), "login", "password");
    }
}
