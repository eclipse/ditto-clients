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

import java.time.Instant;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.client.live.commands.base.LiveCommandAnswer;
import org.eclipse.ditto.base.model.signals.commands.CommandResponse;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.exceptions.FeaturePropertiesNotAccessibleException;
import org.eclipse.ditto.things.model.signals.commands.exceptions.FeaturePropertiesNotModifiableException;
import org.eclipse.ditto.things.model.signals.commands.modify.ModifyFeaturePropertiesResponse;
import org.eclipse.ditto.base.model.signals.events.Event;
import org.eclipse.ditto.things.model.signals.events.FeaturePropertiesCreated;
import org.eclipse.ditto.things.model.signals.events.FeaturePropertiesModified;

/**
 * A mutable builder with a fluent API for creating a {@link LiveCommandAnswer} for a
 * {@link ModifyFeaturePropertiesLiveCommand}.
 *
 * @since 2.0.0
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
final class ModifyFeaturePropertiesLiveCommandAnswerBuilderImpl
        extends
        AbstractLiveCommandAnswerBuilder<ModifyFeaturePropertiesLiveCommand,
                ModifyFeaturePropertiesLiveCommandAnswerBuilder.ResponseFactory,
                ModifyFeaturePropertiesLiveCommandAnswerBuilder.EventFactory>
        implements ModifyFeaturePropertiesLiveCommandAnswerBuilder {

    private ModifyFeaturePropertiesLiveCommandAnswerBuilderImpl(final ModifyFeaturePropertiesLiveCommand command) {
        super(command);
    }

    /**
     * Returns a new instance of {@code ModifyFeaturePropertiesLiveCommandAnswerBuilderImpl}.
     *
     * @param command the command to build an answer for.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     */
    public static ModifyFeaturePropertiesLiveCommandAnswerBuilderImpl newInstance(
            final ModifyFeaturePropertiesLiveCommand command) {
        return new ModifyFeaturePropertiesLiveCommandAnswerBuilderImpl(command);
    }

    @Override
    protected CommandResponse doCreateResponse(
            final Function<ResponseFactory, CommandResponse<?>> createResponseFunction) {
        return createResponseFunction.apply(new ResponseFactoryImpl());
    }

    @Override
    protected Event doCreateEvent(final Function<EventFactory, Event<?>> createEventFunction) {
        return createEventFunction.apply(new EventFactoryImpl());
    }

    @Immutable
    private final class ResponseFactoryImpl implements ResponseFactory {

        @Nonnull
        @Override
        public ModifyFeaturePropertiesResponse created() {
            return ModifyFeaturePropertiesResponse.created(command.getEntityId(), command.getFeatureId(),
                    command.getProperties(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ModifyFeaturePropertiesResponse modified() {
            return ModifyFeaturePropertiesResponse.modified(command.getEntityId(), command.getFeatureId(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featurePropertiesNotAccessibleError() {
            return errorResponse(command.getEntityId(),
                    FeaturePropertiesNotAccessibleException.newBuilder(command.getEntityId(),
                            command.getFeatureId())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featurePropertiesNotModifiableError() {
            return errorResponse(command.getEntityId(),
                    FeaturePropertiesNotModifiableException.newBuilder(command.getEntityId(),
                            command.getFeatureId())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }
    }

    @Immutable
    private final class EventFactoryImpl implements EventFactory {

        @Nonnull
        @Override
        public FeaturePropertiesCreated created() {
            return FeaturePropertiesCreated.of(command.getEntityId(), command.getFeatureId(),
                    command.getProperties(), -1, Instant.now(), command.getDittoHeaders(), null);
        }

        @Nonnull
        @Override
        public FeaturePropertiesModified modified() {
            return FeaturePropertiesModified.of(command.getEntityId(), command.getFeatureId(),
                    command.getProperties(), -1, Instant.now(), command.getDittoHeaders(), null);
        }
    }

}
