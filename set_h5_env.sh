#!/bin/sh

export H5LIB=${PWD}/pkgs/hdf-java-linux32/lib

export CLASSPATH=${H5LIB}/jhdf5.jar:${H5LIB}/jhdf5obj.jar:${H5LIB}/jhdfobj.jar:${PWD}/pkgs/MSongsDB:${PWD}/src

export LD_LIBRARY_PATH=${H5LIB}/linux

if [ -e /usr/bin/haoop ]; then
    export HADOOPCP="`hadoop classpath`"
else
    export HADOOPCP=""
fi