package gen;

public class header {
	byte bytes[]=new byte[5];
	
	void setvalue(String what,int val){
		switch(what){
		case "tileset":
			bytes[4]=(byte)((byte)val&0x0F|(bytes[4]&0xF0));
		break;
		case "spriteset":
			bytes[2]=(byte)((byte)val&0x0F|(bytes[2]&0xF0));
		break;
		case "vscroll":
			bytes[4]=(byte)((byte)val<<4&0x30|(bytes[4]&0xCF));
		break;
		case "itemmem":
			bytes[4]=(byte)((byte)val<<6&0xC0|(bytes[4]&0x3F));
		break;
		case "layer3":
			bytes[2]=(byte)((byte)val<<7&0x80|(bytes[2]&0x7F));
		break;
		case "levelsize":
			bytes[0]=(byte)((byte)val&0x1F|(bytes[0]&0xE0));
		break;
		case "levelmode":
			bytes[1]=(byte)((byte)val&0x1F|(bytes[1]&0xE0));
		break;
		case "bgpalette":
			bytes[0]=(byte)((byte)val<<5&0xE0|(bytes[0]&0x1F));
		break;
		case "bgcolor":
			bytes[1]=(byte)((byte)val<<5&0xE0|(bytes[1]&0x1F));
		break;
		case "music":
			bytes[2]=(byte)((byte)val<<4&0x8F|(bytes[2]&0x70));
		break;
		case "fgpalette":
			bytes[3]=(byte)((byte)val&0x07|(bytes[3]&0xF8));
		break;
		case "spritepalette":
			bytes[3]=(byte)((byte)val<<3&0x38|(bytes[3]&0xC7));
		break;
		case "time":
			bytes[3]=(byte)((byte)val<<6&0xC0|(bytes[3]&0x3F));
		break;
		}

		
	}
	
	void setheader(byte inarr[]){
		for(int loop=0;loop<5;loop++){
			bytes[loop]=inarr[loop];
		}
	}
	
	
	byte[] getheader(){
		return(bytes);
	}
	
	
	
}
