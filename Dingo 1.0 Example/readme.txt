Note that these three directories must be defined as source
folders, within Eclipse or another IDE, in order to ensure
that the TSV and CSV test data files are available to JUnit:

src/main/java
src/test/java
src/test/resources

Eclipse may not define the src/test/resources directory as a
source folder by default; you may need to right-click on that
directory and then click Build Path -> Use as Source Folder

Additionally, the libraries required to run the tests for this
example are all provided in the lib directory. Your IDE should
automatically add all these JARs to your build path so that you
can run the tests and see Dingo in action!