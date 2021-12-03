import unittest
import sys
import os
import random
import string
import shutil
import git
import filecmp


sys.path.insert(0, './py')

from prioritize import resetFastPaths, parameterizer

class TestUtilFunctions(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        print("Starting Tests")

    @classmethod
    def tearDownClass(cls):
        print("End Tests")

    def test_create_new_folders(self):

        N = 8

        folderName = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(N))
        folderPath = "../fastTest/{}".format(folderName)

        os.makedirs(folderPath)

        resetFastPaths(folderPath)

        self.assertEqual(os.path.exists(folderPath), True)
        self.assertEqual(os.path.exists('{}/.fast'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/input'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/output'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/output/prioritized'.format(folderPath)), True)

        shutil.rmtree('../fastTest')

    def test_reset_fast_folders(self):

        N = 8

        folderName = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(N))
        folderPath = "../fastTest/{}".format(folderName)

        os.makedirs(folderPath)
        os.makedirs('{}/.fast'.format(folderPath))

        resetFastPaths(folderPath)

        self.assertEqual(os.path.exists(folderPath), True)
        self.assertEqual(os.path.exists('{}/.fast'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/input'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/output'.format(folderPath)), True)
        self.assertEqual(os.path.exists('{}/.fast/output/prioritized'.format(folderPath)), True)

        shutil.rmtree('../fastTest')

    #def test_parametrize_function(self):
    #
    #    folderPath = "../fastTest/commons-math"
    #
    #    os.makedirs(folderPath)
    #
    #    repo = git.Repo.clone_from("https://github.com/DinoSaulo/commons-math", folderPath + '/')
    #
    #    resetFastPaths(folderPath)
    #    parameterizer(folderPath, 'bbox')
    #
    #    indexTestFilesPathsFile = "{}/.fast/input/commons-math-indexTestFilesPaths.txt".format(folderPath)
    #
    #    fileName = "{}/.fast/input/commons-math-bbox.txt".format(folderPath)
    #
    #    self.assertTrue(filecmp.cmp(fileName, "filesToCompare/commons-math-bbox.txt".format(folderPath)))
    #    self.assertTrue(filecmp.cmp(indexTestFilesPathsFile, "filesToCompare/commons-math-indexTestFilesPaths.txt".format(folderPath)))
