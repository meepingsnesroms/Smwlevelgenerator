import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class structure {

	void main(String file){
		
		RandomAccessFile mwl;
		FileWriter outfile;
		try {
			mwl = new RandomAccessFile(file, "r");
			//str.substring(0,str.length()-1);
			outfile = new FileWriter(file.substring(0,file.length()-4)+".ums");
			mwl.seek(0xC8+5);
			byte data=0x00;
			byte xcoordwblockid=0x00;
			byte settings=0x00;
			byte map16=0x00;
			byte map16p2=0x00;
			int xout=0;
			int yout=0;
			int lines=0;
			int garbagelines=0;
			int page=0;
			String output[]=new String[1000];
			while(true){
				data=mwl.readByte();
				xcoordwblockid=mwl.readByte();
				settings=mwl.readByte();
				xout=xcoordwblockid&0x0F;
				yout=data&0x1F;
				//System.out.format("%x",data);
				if((data&((byte)0x80))==(byte)0x80)page++;
				if(data==(byte)0xFF)break;
				
				if(((data>>1)&(byte)0x30)+((xcoordwblockid>>4)&(byte)0x0F)==(byte)0x22){
					map16=(byte)0x00;
					map16p2=mwl.readByte();
				}
				if(((data>>1)&(byte)0x30)+((xcoordwblockid>>4)&(byte)0x0F)==(byte)0x23){
					map16=(byte)0x01;
					map16p2=mwl.readByte();
				}
				
				if(((data>>1)&(byte)0x30)+((xcoordwblockid>>4)&(byte)0x0F)==(byte)0x27){
				map16=mwl.readByte();
				map16p2=mwl.readByte();
				}
				
				
				//output[lines]=xout+":"+yout+":"+Integer.toHexString(map16)+Integer.toHexString(map16p2);
				//if(xout<=Integer.parseInt(x)&&yout<=Integer.parseInt(y))
				if(map16!=(byte)0xFF)output[lines]=String.format("%d:%d:%02x%02x",xout+(16*page),yout,map16,map16p2).toUpperCase();
				else garbagelines++;
				
				data=(byte)0x00;
				xcoordwblockid=(byte)0x00;
				settings=(byte)0x00;
				map16=(byte)0xFF;
				map16p2=(byte)0x00;
				xout=0;
				yout=0;
				lines++;
				
			}
			mwl.close();
			PrintWriter print_line = new PrintWriter( outfile );
			print_line.println(lines-garbagelines);
			for(int cows=0;cows!=lines;cows++){
				if(output[cows]!=null)print_line.println(output[cows]);
			}
			outfile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
