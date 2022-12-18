package cl.nessfit.web.service;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.Request;
import cl.nessfit.web.model.User;
import cl.nessfit.web.repository.RequestRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestServiceImplTest {
    @Mock
    private RequestRepositoryInterface requestRepository;
    @InjectMocks
    private RequestServiceImpl requestService;
    private Request request;
    private Installation installation;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        this.request = new Request();
        this.installation = new Installation();
        this.installation.setName("Instalacion test");
        this.user = new User();
        this.user.setRut("12345678K");

        this.request.setInstallation(this.installation);
        this.request.setUser(this.user);
    }

    @Test
    void getRequestsBy() {
        when(requestRepository.findRequestsByInstallation_Name(this.installation.getName())).thenReturn(Arrays.asList(request));
        assertNotNull(requestService.getRequestsByInstallation(this.installation.getName()));
    }
}