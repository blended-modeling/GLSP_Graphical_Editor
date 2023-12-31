/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.ChangeRoutingPointsCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GPoint;

public class ChangeRoutingPointsCommandContribution extends NotationCommandContribution {

   public static final String TYPE = "changeRoutingPoints";

   public static CCommand create(final String semanticElementId, final List<GPoint> routingPoints) {
      CCompoundCommand changeRoutingPointsCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      changeRoutingPointsCommand.setType(TYPE);
      changeRoutingPointsCommand.getProperties().put(SEMANTIC_ELEMENT_ID, semanticElementId);

      routingPoints.forEach(point -> {
         CCommand childCommand = CCommandFactory.eINSTANCE.createCommand();
         childCommand.getProperties().put(POSITION_X, String.valueOf(point.getX()));
         childCommand.getProperties().put(POSITION_Y, String.valueOf(point.getY()));
         changeRoutingPointsCommand.getCommands().add(childCommand);
      });

      return changeRoutingPointsCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      CompoundCommand compoundCommand = new CompoundCommand();

      if (command instanceof CCompoundCommand) {

         ((CCompoundCommand) command).getCommands().forEach(changeRoutingPointCommand -> {
            String semanticElementId = changeRoutingPointCommand.getProperties().get(SEMANTIC_ELEMENT_ID);
            List<GPoint> newRoutingPoints = new ArrayList<>();
            ((CCompoundCommand) changeRoutingPointCommand).getCommands().forEach(cmd -> {
               GPoint routingPoint = NotationCommandUtil.getGPoint(
                  cmd.getProperties().get(POSITION_X), cmd.getProperties().get(POSITION_Y));
               newRoutingPoints.add(routingPoint);
            });
            compoundCommand.append(
               new ChangeRoutingPointsCommand(domain, modelUri, semanticElementId, newRoutingPoints));
         });

      }
      return compoundCommand;
   }

}
