/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.notation.commands;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;

public class RemoveNotationElementCommand extends NotationElementCommand {

   protected NotationElement shapeToRemove;

   public RemoveNotationElementCommand(final EditingDomain domain, final URI modelUri, final String semanticElementId) {
      super(domain, modelUri);
      this.shapeToRemove = NotationCommandUtil.getNotationElement(modelUri, domain, semanticElementId,
         NotationElement.class);
   }

   @Override
   protected void doExecute() {
      notationDiagram.getElements().remove(shapeToRemove);
   }

}
