/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.ProcessRunner;


public class Make {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		List<String> command = new ArrayList<String>();
		command.add("make");
		command.add("CC=goto-cc '-Dfabs(x)=((x)<0?-(x):(x))' '-Dsqrt(x)=((x)<0?-(x):(x))'" );
		Appendable buf = new StringBuffer();
		File srcFolder = new File( "E:\\\\workspaceVTTDemo\\Analysis\\BCT\\BCT_DATA\\BCT\\CBMC.SRC.ORIGINAL" );
		ProcessRunner.run(command, buf, buf, 0, srcFolder);

		System.out.println(buf);
	}

}
