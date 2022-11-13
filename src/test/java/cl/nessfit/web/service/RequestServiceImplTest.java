package cl.nessfit.web.service;

import cl.nessfit.web.repository.RequestRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceImplTest {

    @Mock
    private RequestRepositoryInterface requestRepository;
    @InjectMocks
    private RequestServiceImpl requestService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getRequestsBy() {

    }
}