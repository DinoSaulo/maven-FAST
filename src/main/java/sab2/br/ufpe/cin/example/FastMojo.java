package sab2.br.ufpe.cin.example;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;
import java.util.Scanner;

/**
 * An example Maven Mojo that resolves the current project's git revision and adds that a new {@code exampleVersion}
 * property to the current Maven project.
 */
@Mojo(name = "version", defaultPhase = LifecyclePhase.INITIALIZE)
public class FastMojo extends AbstractMojo {

    /**
     * The git command used to retrieve the current commit hash.
     */
    @Parameter(property = "alg.name", defaultValue = "FAST-pw")
    private String command;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;
    
    @Inject
    private CmdProvider cmdProvider;
    
    private static boolean checkIfAlgIsValid(String algname)
    {
        if (algname.equals("FAST-pw") || algname.equals("FAST-one") || algname.equals("FAST-log") || algname.equals("FAST-sqrt") || algname.equals("FAST-all")) {
        	return true;
        } else {
        	return false;
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        
    	if (checkIfAlgIsValid(command)) {
    		
    		String cmdReturn = cmdProvider.runCmdCommand(command);
    		
    		getLog().info(cmdReturn);
    		
    	} else {
    		getLog().info("\u001B[31m" + "\033[0;1m" + "Invalid algorithm name" + "\033[0;0m" + "\u001B[0m");
    	}
    	
    }
    
    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	while(true) {
    		String command = scanner.nextLine();
            

    		System.out.println(checkIfAlgIsValid(command));
        	
        	if (checkIfAlgIsValid(command)) {
        		System.out.println(command);
        	} else {
        		System.out.println("\u001B[31m" + "\033[0;1m" + "Invalid algorithm name" + "\033[0;0m" + "\u001B[0m");
        	}
    	}
    	
	}

    
}
