from gen import make
from gen import start
lvltools = make()
start = start();
for blocks in range(0,lvltools.getlvlsizex()):
	for otherblocks in range(0,lvltools.getlvlsizey()):
		if otherblocks<7:
			if otherblocks>2:
				if start.mrandom(1000)==500:
					lvltools.placestr(blocks,otherblocks,"mooncloud")
		