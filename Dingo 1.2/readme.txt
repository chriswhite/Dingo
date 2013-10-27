Note that these three directories must be defined as source
folders, within Eclipse or another IDE, in order to ensure
that the TSV and CSV test data files are available to JUnit:

src/main/java
src/test/java
src/test/resources

Eclipse may not define the src/test/resources directory as a
source folder by default; you may need to right-click on that
directory and then click Build Path -> Use as Source Folder