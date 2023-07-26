/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.Operation;

import com.google.inject.Inject;

public abstract class EMSOperationHandler<O extends Operation> extends BasicOperationHandler<O> {

   @Inject
   protected EMSModelState modelState;
   @Inject
   protected EMSModelServerAccess modelServerAccess;

   protected abstract void executeOperation(O operation);

   @Override
   public Optional<Command> createCommand(final O operation) {
      // Our command stack is managed by the Model Server and we do not use the functionality of the ModelState's
      // command stack. Hence we execute the operation via the Model Server and do not return commands.
      executeOperation(operation);
      return doNothing();
   }

}
