package cl.nessfit.web.service;

import cl.nessfit.web.repository.InstallationRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class InstallationServiceImplTest {

    @Mock
    private InstallationRepositoryInterface installationRepository;
    @InjectMocks
    private InstallationServiceImpl installationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
    }

    @Test
    void searchByName() {
    }

    @Test
    void getInstallations() {
    }
}