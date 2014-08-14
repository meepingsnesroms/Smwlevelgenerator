from gen import make
from gen import start
from array import array
lvltools = make()
starter = start()
false=0
true=1
turtleinlevel=false
cornersx=[]
cornersy=[]
howmany=0
for blocks in range(0,lvltools.getlvlsizex()):
	for otherblocks in range(0,lvltools.getlvlsizey()):
		if(lvltools.getlvldata(blocks,otherblocks)[2]==1 or lvltools.getlvldata(blocks,otherblocks)[3]==0x04):
			turtleinlevel=true

if(turtleinlevel==true):		
	for blocks in range(0,lvltools.getlvlsizex()):
		for otherblocks in range(0,lvltools.getlvlsizey()):
			if(lvltools.getlvldata(blocks,otherblocks)[1] == 0x103 or lvltools.getlvldata(blocks,otherblocks)[0] == -122 and lvltools.freespace(blocks+1,otherblocks-4,8,7)):
				cornersx.append(blocks)
				cornersy.append(otherblocks)
				howmany=howmany+1
	if(howmany>0):
		origin=starter.mrandom(howmany)
		lvltools.placestr(cornersx[origin]+1,cornersy[origin]-4,"secret1key")
		lvltools.placesprite(cornersx[origin]+6,cornersy[origin]-3,0x80)
	else print("cant place no valid area")