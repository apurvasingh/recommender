#
#
#

ALLCLASSES=hdf5_getters.class RecurseSongs.class SongInfo.class \
           SongDoopDriver.class SongDoopMapper.class SongDoopReducer.class \
           SongWritable.class SongInputFormat.class SongReader.class

SongDoop.jar: $(ALLCLASSES)
	jar cvf $@ $(ALLCLASSES)

SongDoopMapper.class: SongDoopMapper.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongDoopMapper.java

SongDoopDriver.class: SongDoopDriver.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongDoopDriver.java

SongDoopReducer.class: SongDoopReducer.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongDoopReducer.java

SongWritable.class: SongWritable.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongWritable.java

SongInputFormat.class: SongInputFormat.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongInputFormat.java

SongReader.class: SongReader.java
	javac -classpath ${HADOOPCP}:${CLASSPATH} SongReader.java

SongInfo.class: SongInfo.java
	javac SongInfo.java

RecurseSongs.class: RecurseSongs.java
	javac RecurseSongs.java

ConvertSongs.class: ConvertSongs.java
	javac ConvertSongs.java

hdf5_getters.class: ../pkgs/MSongsDB/hdf5_getters.class
	cp -v ../pkgs/MSongsDB/hdf5_getters.class .

Recurser: SongInfo.class RecurseSongs.class

Converter: SongInfo.class RecurseSongs.class ConvertSongs.class
