package com.awesome.thesis.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.awesome.thesis.rules.HaveExactlyOneAggregateRoot.HAVE_EXACTLY_ONE_AGGREGATE_ROOT;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.awesome.thesis.logic.domain.model..")
public class AggregatTest {
    @ArchTest
    static final ArchRule oneAggregatRootPerAggregate = slices()
            .matching("..(*)..")
            .should(HAVE_EXACTLY_ONE_AGGREGATE_ROOT);
}
