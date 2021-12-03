import glob
import sys
import shutil
import os

FASTTestWatcherPath = 'tools/TestWatcher/FASTTestWatcher.java'

def find_element(my_list, value):
    try:
        return my_list.index(value)
    except:
        return False

def getLastImportLine(linesOfJavaTestFile):

    last_index = False

    for element in linesOfJavaTestFile:
        if 'class' in element:
            break
        elif 'import' in element:
            last_index = linesOfJavaTestFile.index(element)
    return last_index

def createFolderIfNotExists(folderPath):
    if not os.path.exists(folderPath):
        os.makedirs(folderPath)

def createTestWatcher():
        ROOT_DIR = os.path.dirname(os.path.abspath(__file__))
        fastTestWatcherFileDir = '{}/TestWatcher/FASTTestWatcher.java'.format(ROOT_DIR)

        projectFastDir = '{}/src/test/resources/fast'.format(projectPath)
        projectFastFileDir = projectFastDir + '/FASTTestWatcher.java'

        if os.path.exists(projectFastFileDir):
            print('The file "FASTTestWatcher.java" already exists in the project.')
        else:
            createFolderIfNotExists(projectFastDir)

            shutil.copyfile(fastTestWatcherFileDir, projectFastFileDir)

def removeTestWatcher():
    projectFastFileDir = '{}/src/test/resources/fast/FASTTestWatcher.java'.format(projectPath)

    if os.path.exists(projectFastFileDir):
        os.remove(projectFastFileDir)
    else:
        print('File "FASTTestWatcher.java" does not exist.')

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Wrong input.")
        exit()
    projectPath, operation = sys.argv[1:]

    imports_testWacher = '''import org.junit.jupiter.api.extension.ExtendWith;\nimport fast.FASTTestWatcher;\n'''
    extendWith_testWatcher = "@ExtendWith(FASTTestWatcher.class)\n"

    baseMavenTestPath = "/src/test/java"

    # https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven-filter-test-class-names
    arr1 = glob.glob(projectPath + baseMavenTestPath + "/**/Test*.java", recursive = True)
    arr2 = glob.glob(projectPath + baseMavenTestPath  + "/**/*Test.java", recursive = True)
    arr3 = glob.glob(projectPath + baseMavenTestPath  + "/**/*Tests.java", recursive = True)
    arr4 = glob.glob(projectPath + baseMavenTestPath  + "/**/*TestCase.java", recursive = True)

    arr = arr1 + arr2 + arr3 + arr4

    if operation == 'add_instrumentation_to_the_project':
        for fileTest in arr:

            fileTestName = fileTest.split('/')[-1].replace('.java','')

            f = open(fileTest, "r")
            lines = f.readlines()

            testCaseIndex = 0

            lastImportLine = getLastImportLine(lines)
            lines.insert(lastImportLine+1, imports_testWacher)

            while testCaseIndex < len(lines):
                if lines[testCaseIndex].find("class " + fileTestName)!=-1 :
                    lines.insert(testCaseIndex, extendWith_testWatcher)
                    break
                testCaseIndex = testCaseIndex + 1

            a_file = open(fileTest, "w+")
            a_file.writelines(lines)
            a_file.close()

        createTestWatcher()

    elif operation == 'remove_instrumentation_from_the_project':
        for fileTest in arr:

            fileTestName = fileTest.split('/')[-1].replace('.java','')

            f = open(fileTest, "r")
            lines = f.readlines()

            import1 = "import org.junit.jupiter.api.extension.ExtendWith;\n"
            index1 = find_element(lines, import1)
            if index1 :
                lines.remove(import1)

            import2 = "import fast.FASTTestWatcher;\n"
            index2 = find_element(lines, import2)
            if index2 :
                lines.remove(import2)

            import3 = "@ExtendWith(FASTTestWatcher.class)\n"
            index3 = find_element(lines, import3)
            if index3 :
                lines.remove(import3)

            a_file = open(fileTest, "w+")
            a_file.writelines(lines)
            a_file.close()

        removeTestWatcher()

    else:
        print('Operation not available.\n')
        print('Available operations:')
        print('\tadd_instrumentation_to_the_project -> Adds the instrumentation needed to run the tests by displaying the run')
        print('\tremove_instrumentation_from_the_project -> Removes the instrumentation needed to run the tests by displaying the run')
