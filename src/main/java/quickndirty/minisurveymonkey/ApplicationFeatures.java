package quickndirty.minisurveymonkey;
import org.springframework.context.annotation.Bean;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;

public enum ApplicationFeatures implements Feature {
    @EnabledByDefault
    @Label("Provide graphical display of results for survey responses")
    GRAPHICAL_RESPONSES;
    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}