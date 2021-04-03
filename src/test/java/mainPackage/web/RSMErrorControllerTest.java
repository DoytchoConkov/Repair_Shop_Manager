package mainPackage.web;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RSMErrorControllerTest {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    public void returnsView404WhenPageIsNotFound() throws Exception {
        RSMErrorController rsmErrorController = new RSMErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(404);
        Assert.assertEquals(rsmErrorController.handleError(request), "errors/error-404");


    }

    @Test
    public void returnsView403WhenThereIsAnError() throws Exception {
        RSMErrorController rsmErrorController = new RSMErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(403);
        Assert.assertEquals(rsmErrorController.handleError(request), "errors/error-403");


    }

    @Test
    public void returnsView500ForAnyOtherError() throws Exception {
        RSMErrorController rsmErrorController = new RSMErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(400);
        Assert.assertEquals(rsmErrorController.handleError(request), "errors/error-page");
    }
    @Test
    public void getErrorPath() throws Exception {
        RSMErrorController rsmErrorController = new RSMErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(400);
        Assert.assertEquals(rsmErrorController.getErrorPath(), "/error");
    }
}