#!/bin/sh
javac ServerTest.java
javah -jni ServerTest
javap -s -p ServerTest
gcc -o ServerTest -I /usr/lib/jvm/java-7-openjdk-amd64/include -I /usr/lib/jvm/java-7-openjdk-amd64/include/linux -L /usr/bin/java -L /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/server bserverd.c -ljvm
export LD_LIBRARY_PATH=/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/server
