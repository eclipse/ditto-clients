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

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.client.live.commands.base.LiveCommandAnswer;
import org.eclipse.ditto.json.JsonValue;
import org.eclipse.ditto.base.model.signals.commands.CommandResponse;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.exceptions.FeaturePropertyNotAccessibleException;
import org.eclipse.ditto.things.model.signals.commands.query.RetrieveFeaturePropertyResponse;

/**
 * A mutable builder with a fluent API for creating a {@link LiveCommandAnswer} for a
 * {@link RetrieveFeaturePropertyLiveCommand}.
 *
 * @since 2.0.0
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
final class RetrieveFeaturePropertyLiveCommandAnswerBuilderImpl
        extends
        AbstractLiveCommandAnswerBuilder<RetrieveFeaturePropertyLiveCommand,
                RetrieveFeaturePropertyLiveCommandAnswerBuilder.ResponseFactory>
        implements RetrieveFeaturePropertyLiveCommandAnswerBuilder {

    private RetrieveFeaturePropertyLiveCommandAnswerBuilderImpl(final RetrieveFeaturePropertyLiveCommand command) {
        super(command);
    }

    /**
     * Returns a new instance of {@code RetrieveFeaturePropertiesLiveCommandAnswerBuilderImpl}.
     *
     * @param command the command to build an answer for.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     */
    public static RetrieveFeaturePropertyLiveCommandAnswerBuilderImpl newInstance(
            final RetrieveFeaturePropertyLiveCommand command) {
        return new RetrieveFeaturePropertyLiveCommandAnswerBuilderImpl(command);
    }

    @Override
    protected CommandResponse doCreateResponse(
            final Function<ResponseFactory, CommandResponse<?>> createResponseFunction) {
        return createResponseFunction.apply(new ResponseFactoryImpl());
    }

    @ParametersAreNonnullByDefault
    @Immutable
    private final class ResponseFactoryImpl implements ResponseFactory {

        @Nonnull
        @Override
        public RetrieveFeaturePropertyResponse retrieved(final JsonValue propertyValue) {
            return RetrieveFeaturePropertyResponse.of(command.getEntityId(), command.getFeatureId(),
                    command.getPropertyPointer(),
                    propertyValue, command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featurePropertyNotAccessibleError() {
            return errorResponse(command.getEntityId(),
                    FeaturePropertyNotAccessibleException.newBuilder(command.getEntityId(),
                            command.getFeatureId(), command.getPropertyPointer())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }
    }

}
