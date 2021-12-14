# FAST-Maven

This repository is a modification of the [FAST Approaches to Scalable Similarity-based Test Case Prioritization](https://github.com/icse18-FAST/FAST) project, it is based on the following publication:

> Breno Miranda, Emilio Cruciani, Roberto Verdecchia, and Antonia Bertolino. 2018. FAST Approaches to Scalable Similarity-based Test Case Prioritization. In *Proceedings of ICSE’18: 40th International Conference on Software Engineering, Gothenburg, Sweden, May 27-June 3, 2018 (ICSE’18)*, 11 pages. DOI: [10.1145/3180155.3180210](http://dx.doi.org/10.1145/3180155.3180210)


It contains materials needed to perform FAST project prioritization for projects developed using Java in conjunction with [Maven](https://maven.apache.org/)


Project Replication
---------------
In order to replicate the project follow these steps:

### Prerequisites

1. Have Git installed - [Download](https://git-scm.com/downloads)

2. Have Python version 3 installed - [Download](https://www.python.org/downloads/)

3. Have the PIP installed - [How to install pip](https://pip.pypa.io/en/stable/cli/pip_install/)

### Getting started

1. Clone the repository
   ```bash
   git clone https://github.com/DinoSaulo/FAST-parameterized
   ```

2. Install the additional python packages required:
   ```bash
   pip3 install -r requirements.txt
   ```

### Perform prioritization with different FAST algorithms

1. Execute the `prioritize.py` script
   - `python3 py/prioritize.py <subject> <algorithm>`

      Example: `python3 py/prioritize.py /home/user/projects/truth FAST-pw`

      The `<subject>` is the path to the project, some examples are: '/home/user/projects/truth' or '../my-projects/calculator'

      The possible values for `<algorithm>` are: FAST-pw, FAST-one, FAST-log, FAST-sqrt and FAST-all.

   - To view output results access the folder `<subject>/.fast/output/`

   - To see the generated Java prioritization file (FASTPrioritizedSuite.java) access the folder `<subject>/src/test/java/fast`

2. Add the following snippets to the Maven project `pon.xml` file

   ```xml
   <build>
   ...
      <plugins>
         ...
         <!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-plugin -->
         <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.19.1</version>
            <configuration>
               <includes>
                  <include>**/FASTPrioritizedSuite.java</include>
               </includes>
            </configuration>
         </plugin>
         ...
      </plugins>
   ...
   </build>
   ```

   ```xml
   </dependencies>
      ...
      <!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter -->
      <dependency>
         <groupId>org.junit.jupiter</groupId>
         <artifactId>junit-jupiter</artifactId>
         <version>5.8.1</version>
         <scope>test</scope>
      </dependency>
      <!-- https://mvnrepository.com/artifact/org.junit.platform/junit-platform-runner -->
      <dependency>
         <groupId>org.junit.platform</groupId>
         <artifactId>junit-platform-runner</artifactId>
         <version>1.5.2</version>
         <scope>test</scope>
      </dependency>
      ...
   </dependencies>
   ```

3. Access the project repository

   Example: `cd /home/user/projects/truth`

4. Run the project tests

   - `mvn test`

### Project source code instrumentation (OPTIONAL)
   When running the tests prioritized by the `FASTPrioritizedSuite.java` file, the report generated at runtime of the tests displayed by Maven is no longer displayed due to tool settings.

   To make a workarround it is necessary to instrument the project by adding the file [FASTTestWatcher.java](tools/TestWatcher/FASTTestWatcher.java) to the project and adding the following lines to the project's test classes:

   ```java
      import org.junit.jupiter.api.extension.ExtendWith;
      import fast.FASTTestWatcher;

      ...

      @ExtendWith(FASTTestWatcher.class)
   ```

   #### Adding the instrumentation

   To automatically add instrumentation to the project, just run the command
    - `python3 tools/project-instrumentation.py <subject> add_instrumentation_to_the_project`

   Example: `python3 tools/project-instrumentation.py /home/user/projects/truth add_instrumentation_to_the_project`

   #### Removing a instrumentação

   To automatically remove instrumentation from the project, just run the command
    - `python3 tools/project-instrumentation.py <subject> remove_instrumentation_from_the_project`

   Example: `python3 tools/project-instrumentation.py /home/user/projects/truth remove_instrumentation_from_the_project`

### Clean preprocessed input files

 1. Run the script [clean-project.py](/tools/clean-project.py) to clean the environment.

    - `python3 tools/clean-project.py <subject>`

   Example: `python3 tools/clean-project.py /home/user/projects/truth`


Directory Structure
---------------
This is the root directory of the repository. The directory is structured as follows:

    FAST
     .
     |
     |--- py/            Implementation of the algorithms and scripts to execute the project.
     |
     |--- tools/         Util scripts, e.g. clean environment, project instrumentation.
