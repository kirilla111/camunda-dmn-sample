package app;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class DmnConfiguration {
    @Bean
    public DmnEngine createDmnEngine() {
        // create default DMN engine configuration
        DmnEngineConfiguration configuration = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration();

        // build a new DMN engine
        DmnEngine dmnEngine = configuration.buildEngine();
        return dmnEngine;
    }

    @Bean(name = "discount")
    public DmnDecision createDecisionDiscount(DmnEngine dmnEngine, ResourceLoader resourceLoader) {
        return getDmnDecision(resourceLoader, dmnEngine, "discount");
    }

    @Bean(name = "allowance")
    public DmnDecision createDecisionAllowance(DmnEngine dmnEngine, ResourceLoader resourceLoader) {
        return getDmnDecision(resourceLoader, dmnEngine, "allowance");
    }

    private DmnDecision getDmnDecision(ResourceLoader resourceLoader, DmnEngine dmnEngine, String allowance) {
        Resource resource = resourceLoader.getResource("classpath:dmn/discount.dmn");
        InputStream is = null;
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dmnEngine.parseDecision(allowance, is);
    }
}