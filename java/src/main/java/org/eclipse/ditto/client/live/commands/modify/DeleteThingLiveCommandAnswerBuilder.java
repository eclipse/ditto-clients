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

import javax.annotation.Nonnull;

import org.eclipse.ditto.client.live.commands.base.LiveCommandAnswerBuilder;
import org.eclipse.ditto.client.live.commands.base.LiveCommandResponseFactory;
import org.eclipse.ditto.client.live.commands.base.LiveEventFactory;
import org.eclipse.ditto.signals.commands.things.ThingErrorResponse;
import org.eclipse.ditto.signals.commands.things.modify.DeleteThing;
import org.eclipse.ditto.signals.commands.things.modify.DeleteThingResponse;
import org.eclipse.ditto.signals.events.things.ThingDeleted;

/**
 * LiveCommandAnswer builder for producing {@code CommandResponse}s and {@code Event}s for {@link DeleteThing}
 * commands.
 *
 * @since 2.0.0
 */
public interface DeleteThingLiveCommandAnswerBuilder extends LiveCommandAnswerBuilder.ModifyCommandResponseStep<
        DeleteThingLiveCommandAnswerBuilder.ResponseFactory, DeleteThingLiveCommandAnswerBuilder.EventFactory> {

    /**
     * Factory for {@code CommandResponse}s to {@link DeleteThing} command.
     */
    interface ResponseFactory extends LiveCommandResponseFactory {

        /**
         * Builds a {@link DeleteThingResponse} using the values of the {@code Command}.
         *
         * @return the response.
         */
        @Nonnull
        DeleteThingResponse deleted();

        /**
         * Builds a {@link ThingErrorResponse} indicating that the Thing was not accessible.
         *
         * @return the response.
         * @see org.eclipse.ditto.signals.commands.things.exceptions.ThingNotAccessibleException
         * ThingNotAccessibleException
         */
        @Nonnull
        ThingErrorResponse thingNotAccessibleError();

        /**
         * Builds a {@link ThingErrorResponse} indicating that the Thing was not deletable.
         *
         * @return the response.
         * @see org.eclipse.ditto.signals.commands.things.exceptions.ThingNotDeletableException
         * ThingNotDeletableException
         */
        @Nonnull
        ThingErrorResponse thingNotDeletableError();
    }

    /**
     * Factory for events triggered by {@link DeleteThing} command.
     */
    @SuppressWarnings("squid:S1609")
    interface EventFactory extends LiveEventFactory {

        /**
         * Creates a {@link ThingDeleted} event using the values of the {@code Command}.
         *
         * @return the event.
         */
        @Nonnull
        ThingDeleted deleted();
    }

}