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
