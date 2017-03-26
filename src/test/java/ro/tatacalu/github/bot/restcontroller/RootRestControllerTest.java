package ro.tatacalu.github.bot.restcontroller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.util.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link RootRestController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RootRestControllerTest {

    @Mock
    private MateiBotConfigurationProperties configurationPropertiesMock;

    private RootRestController rootRestController;

    @Before
    public void setUp() {
        when(configurationPropertiesMock.getGithubToken()).thenReturn(TestUtils.TEST_GITHUB_TOKEN);

        rootRestController = new RootRestController(configurationPropertiesMock);
    }

    @Test
    public void testGetIndex() {
        String indexResult = rootRestController.index();

        assertThat(indexResult, is(notNullValue()));
        assertThat(indexResult, startsWith("This is matei-bot for Github, current UTC time: "));

        verify(configurationPropertiesMock).getGithubToken();
        verifyNoMoreInteractions(configurationPropertiesMock);
    }
}
