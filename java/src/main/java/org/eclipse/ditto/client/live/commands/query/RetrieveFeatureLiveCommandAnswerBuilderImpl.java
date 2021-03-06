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
import org.eclipse.ditto.things.model.Feature;
import org.eclipse.ditto.base.model.signals.commands.CommandResponse;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.exceptions.FeatureNotAccessibleException;
import org.eclipse.ditto.things.model.signals.commands.query.RetrieveFeatureResponse;

/**
 * A mutable builder with a fluent API for creating a {@link LiveCommandAnswer} for a
 * {@link RetrieveFeatureLiveCommand}.
 *
 * @since 2.0.0
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
final class RetrieveFeatureLiveCommandAnswerBuilderImpl
        extends
        AbstractLiveCommandAnswerBuilder<RetrieveFeatureLiveCommand, RetrieveFeatureLiveCommandAnswerBuilder.ResponseFactory>
        implements RetrieveFeatureLiveCommandAnswerBuilder {

    private RetrieveFeatureLiveCommandAnswerBuilderImpl(final RetrieveFeatureLiveCommand command) {
        super(command);
    }

    /**
     * Returns a new instance of {@code RetrieveFeatureLiveCommandAnswerBuilderImpl}.
     *
     * @param command the command to build an answer for.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     */
    public static RetrieveFeatureLiveCommandAnswerBuilderImpl newInstance(final RetrieveFeatureLiveCommand command) {
        return new RetrieveFeatureLiveCommandAnswerBuilderImpl(command);
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
        public RetrieveFeatureResponse retrieved(final Feature feature) {
            return RetrieveFeatureResponse.of(command.getEntityId(), feature, command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featureNotAccessibleError() {
            return errorResponse(command.getEntityId(),
                    FeatureNotAccessibleException.newBuilder(command.getEntityId(), command.getFeatureId())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }
    }

}
