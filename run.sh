#!/bin/sh

export HADOOP_CLASSPATH=$CLASSPATH
# don't think I need "-Ddfs.client.read.shortcircuit=false"
hadoop jar src/SongDoop.jar SongDoopDriver \
      -libjars $CLASSPATH \
     'MillionSongSubset/data/*/*/*' test_out8 \
      MillionSongSubset/data/A/U/R/TRAURYF128F147805D.h5 \
      MillionSongSubset/data/A/I/U/TRAIUSY12903CF0C92.h5
