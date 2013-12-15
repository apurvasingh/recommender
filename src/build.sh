#!/bin/sh
cp ../pkgs/MSongsDB/hdf5_getters.class .
javac *.java
jar cvf SongDoop.jar *.class
