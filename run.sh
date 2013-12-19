#!/bin/sh
hadoop jar src/SongDoop.jar SongDoopDriver -Ddfs.client.read.shortcircuit=false 'MillionSongSubset/data/*/*/*' test_out7 MillionSongSubset/data/A/U/R/TRAURYF128F147805D.h5 MillionSongSubset/data/A/I/U/TRAIUSY12903CF0C92.h5
