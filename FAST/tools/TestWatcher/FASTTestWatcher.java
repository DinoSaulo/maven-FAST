package fast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.assertj.core.description.Description;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class FASTTestWatcher implements TestWatcher, AfterAllCallback, BeforeAllCallback {

	private List<TestResultStatus> testResultsStatus = new ArrayList<>();

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";

	public static final String BoldOn = "\033[0;1m";
	private static final String BoldOff = "\033[0;0m";

	private static final String ANSI_BOLD_BLUE = BoldOn + ANSI_BLUE;
	private static final String ANSI_BOLD_GREEN = BoldOn + ANSI_GREEN;
	private static final String ANSI_BOLD_YELLOW = BoldOn + ANSI_YELLOW;
	private static final String ANSI_BOLD_RED = BoldOn + ANSI_RED;
	private static final String ANSI_BOLD_RESET = BoldOff + ANSI_RESET;

	private static final String INFO_str = "[" + ANSI_BOLD_BLUE + "INFO" + ANSI_BOLD_RESET + "] ";
	private static final String ERROR_str = "[" + ANSI_BOLD_RED + "ERROR" + ANSI_BOLD_RESET + "] ";
	private static final String WARNING_str = "[" + ANSI_BOLD_YELLOW + "WARNING" + ANSI_BOLD_RESET + "] ";

	private static final String TESTS_GREEN_str = ANSI_BOLD_GREEN + "Tests" + ANSI_BOLD_RESET + " ";
	private static final String TESTS_RED_str = ANSI_BOLD_RED + "Tests" + ANSI_BOLD_RESET + " ";
	private static final String TESTS_YELLOW_str = ANSI_BOLD_YELLOW + "Tests" + ANSI_BOLD_RESET + " ";

	private int SUCCESSFUL_tests = 0;
	private int ABORTED_tests = 0;
	private int FAILED_tests = 0;
	private int DISABLED_tests = 0;

	private boolean isFirstBefore = true;

	private long startTime;

	private enum TestResultStatus {
		SUCCESSFUL, ABORTED, FAILED, DISABLED;
	}


	private String formatClass(ExtensionContext context) {

		String s = context.getRequiredTestClass().toString();

		String formatedClass = s.replace("class ", "").replace(context.getDisplayName(), BoldOn + context.getDisplayName() + BoldOff);

		return formatedClass;
	}

	void inicialize() {
		if(this.isFirstBefore) {
			System.out.println("\n" + 
					"███████╗░█████╗░░██████╗████████╗\n" + 
					"██╔════╝██╔══██╗██╔════╝╚══██╔══╝\n" + 
					"█████╗░░███████║╚█████╗░░░░██║░░░\n" + 
					"██╔══╝░░██╔══██║░╚═══██╗░░░██║░░░\n" + 
					"██║░░░░░██║░░██║██████╔╝░░░██║░░░\n" + 
					"╚═╝░░░░░╚═╝░░╚═╝╚═════╝░░░░╚═╝░░░");
			this.isFirstBefore = false;
		}
	}

	@Override
	public void testDisabled(ExtensionContext context, Optional<String> reason) {
		testResultsStatus.add(TestResultStatus.DISABLED);
		DISABLED_tests++;
	}

	@Override
	public void testSuccessful(ExtensionContext context) {
		testResultsStatus.add(TestResultStatus.SUCCESSFUL);
		SUCCESSFUL_tests++;
	}

	@Override
	public void testAborted(ExtensionContext context, Throwable cause) {
		testResultsStatus.add(TestResultStatus.ABORTED);
		ABORTED_tests++;
	}

	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		testResultsStatus.add(TestResultStatus.FAILED);
		FAILED_tests++;
	}

    protected void starting(Description description) {
    	System.out.println("starting");
    }

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		System.out.println(INFO_str + "Running " + formatClass(context));
		startTime = System.currentTimeMillis();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {

		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000;
		String time = String.format(Locale.ROOT, "%.3f", totalTime);

		if(FAILED_tests != 0) {
			System.out.print(ERROR_str + TESTS_RED_str + BoldOn +"run: "+ testResultsStatus.size() + BoldOff + ", " + ANSI_BOLD_RED + "Failures: " + String.valueOf(FAILED_tests) + ANSI_BOLD_RESET + ", Errors: " + String.valueOf(ABORTED_tests) + ", Skipped: " + String.valueOf(DISABLED_tests) + ", ");
		} else if (ABORTED_tests != 0) {
			System.out.print(ERROR_str + TESTS_RED_str + BoldOn +"run: "+ testResultsStatus.size() + BoldOff + ", Failures: " + String.valueOf(FAILED_tests) + ", " + ANSI_BOLD_RED + "Errors: " + String.valueOf(ABORTED_tests) + ANSI_BOLD_RESET +", Skipped: "+ String.valueOf(DISABLED_tests) + ", ");
		} else if (DISABLED_tests != 0) {
			System.out.print(WARNING_str + TESTS_YELLOW_str + BoldOn +"run: "+ testResultsStatus.size() + BoldOff + ", Failures: " + String.valueOf(FAILED_tests) + ", Errors: "+ String.valueOf(ABORTED_tests) + ", " + ANSI_BOLD_YELLOW + "Skipped: "+ String.valueOf(DISABLED_tests) + ANSI_BOLD_RESET + ", ");
		} else if (SUCCESSFUL_tests == 0 && ABORTED_tests == 0 && FAILED_tests == 0 && DISABLED_tests == 0) {
			System.out.print("No tests found, ");
		} else if (SUCCESSFUL_tests > 0 && ABORTED_tests == 0 && FAILED_tests == 0 && DISABLED_tests == 0) {
			System.out.print(INFO_str + TESTS_GREEN_str + ANSI_BOLD_GREEN +"run: "+ testResultsStatus.size() + ANSI_BOLD_RESET + ", Failures: 0, Errors: 0, Skipped: 0, ");
		}

		System.out.println("Time elapsed: " + time + " sec - in " + formatClass(context) );
	}

}