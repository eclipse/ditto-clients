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
package org.eclipse.ditto.client.live.commands.assertions;

import org.eclipse.ditto.base.model.signals.commands.assertions.AbstractCommandAssert;
import org.eclipse.ditto.client.live.commands.base.LiveCommand;

/**
 * An Assert for {@link org.eclipse.ditto.client.live.commands.base.LiveCommand}s.
 */
public class LiveCommandAssert extends AbstractCommandAssert<LiveCommandAssert, LiveCommand<?,?>> {

    /**
     * Constructs a new {@code LiveCommandAssert} object.
     *
     * @param actual the command response to be checked.
     */
    public LiveCommandAssert(final LiveCommand actual) {
        super(actual, LiveCommandAssert.class);
    }

}
