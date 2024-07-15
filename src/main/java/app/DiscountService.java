package app;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DiscountService {
    private final DmnEngine dmnEngine;
    private final DmnDecision discountDmnDecision;
    private final DmnDecision allowanceDmnDecision;

    public DiscountService(
        DmnEngine dmnEngine,
        @Qualifier("discount") DmnDecision discountDmnDecision,
        @Qualifier("allowance") DmnDecision allowanceDmnDecision
    ) {
        this.dmnEngine = dmnEngine;
        this.discountDmnDecision = discountDmnDecision;
        this.allowanceDmnDecision = allowanceDmnDecision;
    }

    public Double getDiscount(Gender gender, Integer age) {
        VariableMap vars = getVars(gender, age);
        DmnDecisionTableResult results = dmnEngine.evaluateDecisionTable(discountDmnDecision, vars);
        Double discount = (Double) results.getFirstResult().get("discount");
        log.info("discount: {}", discount);
        return discount;
    }

    public Double getAllowance(Gender gender, Integer age) {
        VariableMap vars = getVars(gender, age);
        DmnDecisionResult results = dmnEngine.evaluateDecision(allowanceDmnDecision, vars);
        Double allowance = (Double) results.getFirstResult().get("allowance");
        log.info("allowance: {}", allowance);
        return allowance;
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private VariableMap getVars(Gender gender, Integer age) {
        return Variables.
            putValue("gender", gender)
            .putValue("age", age);
    }
}
