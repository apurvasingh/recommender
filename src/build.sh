#!/bin/sh
cp ../pkgs/MSongsDB/hdf5_getters.class .
javac -classpath `hadoop classpath`:$CLASSPATH *.java
jar cvf SongDoop.jar *.class
