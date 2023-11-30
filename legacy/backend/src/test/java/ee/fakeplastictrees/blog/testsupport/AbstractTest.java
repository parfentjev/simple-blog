package ee.fakeplastictrees.blog.testsupport;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("integration")
@SpringJUnitConfig(classes = MockitoAnnotations.class)
public class AbstractTest {
}
