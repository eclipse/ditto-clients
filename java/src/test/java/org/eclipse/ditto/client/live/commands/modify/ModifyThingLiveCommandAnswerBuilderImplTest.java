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
package org.eclipse.ditto.client.live.commands.modify;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.text.MessageFormat;

import org.eclipse.ditto.base.model.common.HttpStatus;
import org.eclipse.ditto.base.model.headers.DittoHeaders;
import org.eclipse.ditto.client.live.TestConstants;
import org.eclipse.ditto.client.live.commands.assertions.LiveCommandAssertions;
import org.eclipse.ditto.client.live.commands.base.LiveCommandAnswer;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.exceptions.ThingNotAccessibleException;
import org.eclipse.ditto.things.model.signals.commands.exceptions.ThingNotModifiableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test for {@link org.eclipse.ditto.client.live.commands.modify.ModifyThingLiveCommandAnswerBuilderImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ModifyThingLiveCommandAnswerBuilderImplTest {

    @Mock
    private ModifyThingLiveCommand commandMock;

    private ModifyThingLiveCommandAnswerBuilderImpl underTest;

    @Before
    public void setUp() {
        Mockito.when(commandMock.getEntityId()).thenReturn(TestConstants.Thing.THING_ID);
        Mockito.when(commandMock.getDittoHeaders()).thenReturn(DittoHeaders.empty());
        Mockito.when(commandMock.getThing()).thenReturn(TestConstants.Thing.THING);

        underTest = ModifyThingLiveCommandAnswerBuilderImpl.newInstance(commandMock);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void tryToGetNewInstanceWithNullCommand() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> ModifyThingLiveCommandAnswerBuilderImpl.newInstance(null))
                .withMessage(MessageFormat.format("The {0} must not be null!", "command"))
                .withNoCause();
    }

    @Test
    public void buildAnswerWithModifyThingCreatedResponseOnly() {
        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(ModifyThingLiveCommandAnswerBuilder.ResponseFactory::created)
                        .withoutEvent()
                        .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingModifyCommandResponse();
    }

    @Test
    public void buildAnswerWithModifyThingModifiedResponseOnly() {
        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(ModifyThingLiveCommandAnswerBuilder.ResponseFactory::modified)
                        .withoutEvent()
                        .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingModifyCommandResponse();
    }

    @Test
    public void buildAnswerWithThingNotAccessibleErrorResponseOnly() {
        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(ModifyThingLiveCommandAnswerBuilder.ResponseFactory::thingNotAccessibleError)
                        .withoutEvent()
                        .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingErrorResponse()
                .withType(ThingErrorResponse.TYPE)
                .withDittoHeaders(DittoHeaders.newBuilder().responseRequired(false).build())
                .withStatus(HttpStatus.NOT_FOUND)
                .withDittoRuntimeExceptionOfType(ThingNotAccessibleException.class);
    }

    @Test
    public void buildAnswerWithThingNotModifiableErrorResponseOnly() {
        final LiveCommandAnswer liveCommandAnswer =
                underTest.withResponse(ModifyThingLiveCommandAnswerBuilder.ResponseFactory::thingNotModifiableError)
                        .withoutEvent()
                        .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoEvent()
                .hasThingErrorResponse()
                .withType(ThingErrorResponse.TYPE)
                .withDittoHeaders(DittoHeaders.newBuilder().responseRequired(false).build())
                .withStatus(HttpStatus.FORBIDDEN)
                .withDittoRuntimeExceptionOfType(ThingNotModifiableException.class);
    }

    @Test
    public void buildAnswerWithThingCreatedEventOnly() {
        final LiveCommandAnswer liveCommandAnswer = underTest.withoutResponse()
                .withEvent(ModifyThingLiveCommandAnswerBuilder.EventFactory::created)
                .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoResponse()
                .hasThingModifiedEvent();
    }

    @Test
    public void buildAnswerWithThingModifiedEventOnly() {
        final LiveCommandAnswer liveCommandAnswer = underTest.withoutResponse()
                .withEvent(ModifyThingLiveCommandAnswerBuilder.EventFactory::modified)
                .build();

        LiveCommandAssertions.assertThat(liveCommandAnswer)
                .hasNoResponse()
                .hasThingModifiedEvent();
    }

}
