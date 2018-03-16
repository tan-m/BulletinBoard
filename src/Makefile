JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Article.java \
	Client.java \
	ClientInterface.java \
	Server.java \
	StartServer.java \
	IPAndPort.java \
	Coordinator.java \
	CoordinatorInterface.java \
	TestClass.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class

