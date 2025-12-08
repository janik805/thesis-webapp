package com.awesome.thesis.architecture;

import com.awesome.thesis.annotations.AggregateEntity;
import com.awesome.thesis.annotations.AggregateRoot;
import com.awesome.thesis.annotations.AggregateValue;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.awesome.thesis.rules.HaveExactlyOneAggregateRoot.HAVE_EXACTLY_ONE_AGGREGATE_ROOT;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.awesome.thesis.logic.domain.model..")
public class AggregateTest {
    @ArchTest
    static final ArchRule oneAggregatRootPerAggregate = slices()
            .matching("..(*)..")
            .should(HAVE_EXACTLY_ONE_AGGREGATE_ROOT);

    @ArchTest
    static final ArchRule allEntitiesExceptRootArePrivate = classes()
            .that()
            .areMetaAnnotatedWith(AggregateEntity.class)
            .and()
            .areNotAnnotatedWith(AggregateRoot.class)
            .should()
            .notBePublic()
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule allClassesNeedToBeValueOrEntity = classes()
            .should()
            .beMetaAnnotatedWith(AggregateValue.class)
            .orShould()
            .beMetaAnnotatedWith(AggregateEntity.class);

    @ArchTest
    static final ArchRule aggregateRootShouldBePublic = classes()
            .that()
            .areAnnotatedWith(AggregateRoot.class)
            .should()
            .bePublic();

    @ArchTest
    static final ArchRule entitiesCantBeValues = classes()
            .that()
            .areMetaAnnotatedWith(AggregateEntity.class)
            .should()
            .notBeAnnotatedWith(AggregateValue.class);
}
