'''
This file is part of an ICSE'18 submission that is currently under review. 
For more information visit: https://github.com/icse18-FAST/FAST.
    
This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as 
published by the Free Software Foundation, either version 3 of the 
License, or (at your option) any later version.

This software is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this source.  If not, see <http://www.gnu.org/licenses/>.
'''

import os
import sys

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Wrong input.")
        exit()
    projectPath = sys.argv[1:]

    fastBasePath = "{}/.fast".format(projectPath[0])
    fastPrioritizedSuitePath = "{}/src/test/java/fast".format(projectPath[0])
    fastTestWatcherPath = '{}/src/test/resources/fast'.format(projectPath[0])
    fastPrioritizedSuiteFilePath = "{}/FASTPrioritizedSuite.java".format(fastPrioritizedSuitePath)
    fastTestWatcherFilePath = '{}/FASTTestWatcher.java'.format(fastTestWatcherPath)

    for root, folders, files in os.walk("{}/.fast".format(projectPath[0])):
        for file in files:
            print("Deleting {}/{}".format(root, file))
            os.remove("{}/{}".format(root, file))

    if os.path.exists(fastPrioritizedSuiteFilePath):
        print("Deleting {}".format(fastPrioritizedSuiteFilePath))
        os.remove(fastPrioritizedSuiteFilePath)

        print("Deleting {}".format(fastPrioritizedSuitePath))
        os.rmdir(fastPrioritizedSuitePath)

    if os.path.exists(fastTestWatcherFilePath):
        print("Deleting {}".format(fastTestWatcherFilePath))
        os.remove(fastTestWatcherFilePath)

        print("Deleting {}".format(fastTestWatcherPath))
        os.rmdir(fastTestWatcherPath)
