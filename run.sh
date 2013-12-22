#!/bin/sh

export HADOOP_CLASSPATH=$CLASSPATH
# don't think I need "-Ddfs.client.read.shortcircuit=false"
hadoop jar src/SongDoop.jar SongDoopDriver \
      -libjars $CLASSPATH \
     'MillionSongSubset/data/*/*/*' test_out9 \
      MillionSongSubset/data/A/U/R/TRAURYF128F147805D.song \
      MillionSongSubset/data/A/I/U/TRAIUSY12903CF0C92.song
