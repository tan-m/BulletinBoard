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
	Coordinator.java \
	CoordinatorInterface.java 

default: classes
	$(JC) $(JFLAGS) *.java

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class

