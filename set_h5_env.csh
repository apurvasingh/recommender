#!/bin/csh -f

setenv H5LIB ${PWD}/pkgs/hdf-java-linux64/lib

setenv CLASSPATH ${H5LIB}/jhdf5.jar:${H5LIB}/jhdf5obj.jar:${H5LIB}/jhdfobj.jar:${PWD}/pkgs/MSongsDB:${PWD}/src

setenv LD_LIBRARY_PATH ${H5LIB}/linux

if ( -e /usr/bin/hadoop ) then
    export HADOOPCP="`hadoop classpath`"
else
    export HADOOPCP=""
endif
