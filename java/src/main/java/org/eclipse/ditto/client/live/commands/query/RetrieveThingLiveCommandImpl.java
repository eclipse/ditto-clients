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

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.base.model.headers.DittoHeaders;
import org.eclipse.ditto.base.model.signals.commands.Command;
import org.eclipse.ditto.things.model.signals.commands.query.RetrieveThing;

/**
 * An immutable implementation of {@link RetrieveThingLiveCommand}.
 *
 * @since 2.0.0
 */
@ParametersAreNonnullByDefault
@Immutable
final class RetrieveThingLiveCommandImpl extends AbstractQueryLiveCommand<RetrieveThingLiveCommand,
        RetrieveThingLiveCommandAnswerBuilder> implements RetrieveThingLiveCommand {

    private RetrieveThingLiveCommandImpl(final RetrieveThing command) {
        super(command);
    }

    /**
     * Returns an instance of {@code RetrieveThingLiveCommandImpl}.
     *
     * @param command the command to base the result on.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     * @throws ClassCastException if {@code command} is not an instance of {@link RetrieveThing}.
     */
    @Nonnull
    public static RetrieveThingLiveCommandImpl of(final Command<?> command) {
        return new RetrieveThingLiveCommandImpl((RetrieveThing) command);
    }

    @Override
    public RetrieveThingLiveCommand setDittoHeaders(final DittoHeaders dittoHeaders) {
        final RetrieveThing twinCommand = RetrieveThing.getBuilder(getEntityId(), dittoHeaders)
                .withSelectedFields(getSelectedFields().orElse(null))
                .build();
        return of(twinCommand);
    }

    @Nonnull
    @Override
    public RetrieveThingLiveCommandAnswerBuilder answer() {
        return RetrieveThingLiveCommandAnswerBuilderImpl.newInstance(this);
    }

    @Nonnull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + super.toString() + "]";
    }

}
