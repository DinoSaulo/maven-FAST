package sab2.br.ufpe.cin.example;

import java.io.BufferedReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

import javax.inject.Named;

import org.apache.maven.plugin.MojoExecutionException;

import com.google.inject.Singleton;


@Named
@Singleton
public class RuntimeExecCmdProvider implements CmdProvider {
	
	public String runCmdCommand(String command) throws MojoExecutionException {
		
     	String cmd = "python3 ./FAST/py/prioritize.py ~/tcc/projects-with-tests/commons-lang3 " + command;
		ProcessBuilder processBuilder = new ProcessBuilder();

		// -- Linux --

		// Run a shell command
		processBuilder.command("bash", "-c", cmd);
		
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				return output.toString();
			} else {
				return "Error " + output.toString();
			}

		} catch (IOException | InterruptedException e) {
            throw new MojoExecutionException("Execution of command '" + command + "' failed", e);
        }
    }

}
