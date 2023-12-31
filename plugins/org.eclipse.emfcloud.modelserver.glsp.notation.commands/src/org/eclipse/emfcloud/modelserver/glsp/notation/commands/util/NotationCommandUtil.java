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
package org.eclipse.emfcloud.modelserver.glsp.notation.commands.util;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;

public final class NotationCommandUtil {

   public final static String FILE_EXTENSION = "notation";

   private NotationCommandUtil() {}

   public static GPoint getGPoint(final String propertyX, final String propertyY) {
      GPoint gPoint = GraphUtil.point(
         propertyX.isEmpty() ? 0.0d : Double.parseDouble(propertyX),
         propertyY.isEmpty() ? 0.0d : Double.parseDouble(propertyY));
      return gPoint;
   }

   public static GDimension getGDimension(final String width, final String height) {
      GDimension gDimension = GraphUtil.dimension(
         height.isEmpty() ? 0.0d : Double.parseDouble(width),
         width.isEmpty() ? 0.0d : Double.parseDouble(height));
      return gDimension;
   }

   public static Diagram getDiagram(final URI modelUri, final EditingDomain domain) {
      Resource notationResource = domain.getResourceSet()
         .getResource(modelUri.trimFileExtension().appendFileExtension(FILE_EXTENSION),
            false);
      EObject notationRoot = notationResource.getContents().get(0);
      if (!(notationRoot instanceof Diagram)) {}
      return (Diagram) notationRoot;
   }

   public static NotationElement getNotationElement(final URI modelUri, final EditingDomain domain,
      final String semanticElementId) {
      Optional<NotationElement> notationElement = getDiagram(modelUri, domain).getElements().stream()
         .filter(el -> el.getSemanticElement().getElementId().equals(semanticElementId)).findFirst();
      return notationElement.orElse(null);
   }

   public static <C extends NotationElement> C getNotationElement(final URI modelUri, final EditingDomain domain,
      final String semanticElementId, final Class<C> clazz) {
      NotationElement element = getNotationElement(modelUri, domain, semanticElementId);
      return clazz.cast(element);
   }

}
