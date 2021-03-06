/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.client.live.commands.query;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.eclipse.ditto.client.live.commands.assertions.LiveCommandAssertions.assertThat;

import java.text.MessageFormat;

import org.eclipse.ditto.base.model.common.HttpStatus;
import org.eclipse.ditto.base.model.headers.DittoHeaders;
import org.eclipse.ditto.client.live.TestConstants;
import org.eclipse.ditto.client.live.commands.base.LiveCommandAnswer;
import org.eclipse.ditto.json.JsonPointer;
import org.eclipse.ditto.things.model.Features;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.exceptions.FeaturesNotAccessibleException;
import org.eclipse.ditto.things.model.signals.commands.query.RetrieveFeaturesResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test for {@link org.eclipse.ditto.client.live.commands.query.RetrieveFeaturesLiveCommandAnswerBuilderImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class RetrieveFeaturesLiveCommandAnswerBuilderImplTest {

    @Mock
    private RetrieveFeaturesLiveCommand commandMock;

    private RetrieveFeaturesLiveCommandAnswerBuilderImpl underTest;

    @Before
    public void setUp() {
        Mockito.when(commandMock.getEntityId()).thenReturn(TestConstants.Thing.THING_ID);
        Mockito.when(commandMock.getDittoHeaders()).thenReturn(DittoHeaders.empty());

        underTest = RetrieveFeaturesLiveCommandAnswerBuilderImpl.newInstance(commandMock);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void tryToGetNewInstanceWithNullCommand() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> RetrieveFeaturesLiveCommandAnswerBuilderImpl.newInstance(null))
                .withMessage(MessageFormat.format("The {0} must not be null!", "command"))
                .withNoCause();
    }

    @Test
    public void buildAnswerWithoutResponse() {
        final LiveCommandAnswer liveCommandAnswer = underTest.withoutResponse().build();

        assertThat(liveCommandAnswer)
                .hasNoResponse()
                .hasNoEvent();
    }

    @Test
    public void buildAnswerWithRetrieveFeaturesResponseOnly() {
        final Features features = TestConstants.Feature.FEATURES;

        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(responseFactory -> responseFactory.retrieved(features))
                        .build();

        assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingQueryCommandResponse()
                .hasType(RetrieveFeaturesResponse.TYPE)
                .hasDittoHeaders(DittoHeaders.newBuilder().responseRequired(false).build())
                .hasResourcePath(JsonPointer.of("features"));
    }

    @Test
    public void buildAnswerWithAttributeNotAccessibleErrorResponseOnly() {
        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(
                        RetrieveFeaturesLiveCommandAnswerBuilder.ResponseFactory::featuresNotAccessibleError)
                        .build();

        assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingErrorResponse()
                .withType(ThingErrorResponse.TYPE)
                .withDittoHeaders(DittoHeaders.newBuilder().responseRequired(false).build())
                .withStatus(HttpStatus.NOT_FOUND)
                .withDittoRuntimeExceptionOfType(FeaturesNotAccessibleException.class);
    }

}
