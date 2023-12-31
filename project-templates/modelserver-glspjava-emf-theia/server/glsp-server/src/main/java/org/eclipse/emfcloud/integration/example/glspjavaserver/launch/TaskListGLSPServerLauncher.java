/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.integration.example.glspjavaserver.launch;

import org.apache.commons.cli.ParseException;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListDiagramModule;
import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPServerModule;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.DefaultCLIParser;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.glsp.server.launch.SocketGLSPServerLauncher;
import org.eclipse.glsp.server.utils.LaunchUtil;

public final class TaskListGLSPServerLauncher {
   private TaskListGLSPServerLauncher() {}

   @SuppressWarnings("uncommentedmain")
   public static void main(final String[] args) {
      String processName = "TaskListGLSPServer";
      try {
         ElkLayoutEngine.initialize(new LayeredMetaDataProvider());
         DefaultCLIParser parser = new DefaultCLIParser(args, processName);
         LaunchUtil.configure(parser);

         int port = parser.parsePort();
         ServerModule tasklistServerModule = new EMSGLSPServerModule()
            .configureDiagramModule(new TaskListDiagramModule());

         GLSPServerLauncher launcher = new SocketGLSPServerLauncher(tasklistServerModule);
         launcher.start("localhost", port);
      } catch (ParseException ex) {
         ex.printStackTrace();
         LaunchUtil.printHelp(processName, DefaultCLIParser.getDefaultOptions());
      }
   }
}
