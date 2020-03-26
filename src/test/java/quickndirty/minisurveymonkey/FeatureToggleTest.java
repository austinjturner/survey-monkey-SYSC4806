package quickndirty.minisurveymonkey;

import org.junit.Rule;
import org.junit.Test;
import org.togglz.junit.TogglzRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeatureToggleTest {
    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(ApplicationFeatures.class);

    @Test
    public void testDefaultEnabledFeature() {
        assertTrue(ApplicationFeatures.GRAPHICAL_RESPONSES.isActive());
    }

    @Test
    public void testDefaultDisabledFeature() {
        togglzRule.disable(ApplicationFeatures.GRAPHICAL_RESPONSES);
        assertFalse(ApplicationFeatures.GRAPHICAL_RESPONSES.isActive());
    }
}