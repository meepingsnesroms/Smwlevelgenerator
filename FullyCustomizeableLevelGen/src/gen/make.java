package gen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.mojang.mario.level.Level;

public class make {
	static int externalsprites[][]=new int[2000][2000];
	static ScriptEngine python = new ScriptEngineManager().getEngineByName("python");
	static Level levelin;
	public static String leveltype;
	int arraystart=5;
	byte[] array=new byte[50000];
	boolean set=false;
	byte spritedata[]=new byte[20000];
	int spritetrack=1;
	boolean spriteenabled=false;
	
	
	void clearall(){
		levelin=null;
		leveltype=null;
		spritedata=new byte[20000];
		spritetrack=1;
		spriteenabled=false;
		arraystart=5;
		array=new byte[50000];
		set=false;
		
	}
	
	public void placestr(int x,int y,String struct){
		levelin=addstructure(levelin,struct,x,y);
	}
	
	public int getlvlsizex(){
		return(levelin.map.length);
	}
	
	public int getlvlsizey(){
		return(levelin.map[0].length);
	}
	
	public int[] getlvldata(int x,int y){
		int array[]=new int[4];
		array[0]=(int)levelin.map[x][y];
		array[1]=levelin.data[x][y];
		array[2]=levelin.spriteTemplates[x][y];
		array[3]=externalsprites[x][y];
		return(array);
	}
	
	public void setlvldata(int x,int y,int val,int what){
		if(what==1)levelin.map[x][y]=(byte)val;
		if(what==2)levelin.data[x][y]=val;
		if(what==3)levelin.spriteTemplates[x][y]=val;
	}
	
	public void placesprite(int x,int y,int what){
		levelin.spriteTemplates[x][y]=10;
		externalsprites[x][y]=(byte)what;
	}
	public boolean freespace(int x,int y,int xleng,int yleng){
		for(int squrrel=0;squrrel<xleng;squrrel++){
			for(int cat=0;cat<yleng;cat++){
				if(levelin.map[x+squrrel][y+cat]!=0)return(false);
			}
		}
		return(true);
	}
	
	Level propertyscript(String file,Level in){
		try {
			levelin=in;
			python.eval(new FileReader(file+".py"));
			Beta.txtpnErrorDialog.setText(Beta.txtpnErrorDialog.getText()+file+".py ran successfuly"+"\n");
		} catch (FileNotFoundException e) {
			Beta.txtpnErrorDialog.setText(Beta.txtpnErrorDialog.getText()+file+".py is not a file"+"\n");
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		Level after=levelin;
		levelin=null;
		return(after);
	}
	
	Level rotatevertical(Level input){
		Level export = new Level(input.height,input.width);
		export.height=input.width;
		export.width=input.height;
		for(int f2=0;f2<input.height;f2++){
			for(int f=0;f<input.width;f++){
				export.map[f2][f]=input.map[f][f2];
				export.data[f2][f]=input.data[f][f2];
				export.spriteTemplates[f2][f]=input.spriteTemplates[f][f2];
			}
		}
		return(export);
	}
		
	Level addstructure(Level input,String file,int coordx,int coordy){
		try {
			RandomAccessFile structure = new RandomAccessFile(file+".ums", "r");
			int lines=Integer.parseInt(structure.readLine());
			int x,y,map16;
			String varibles[];
			for(int getlines=0;getlines<lines;getlines++){
				varibles=structure.readLine().split(":");
				x=Integer.parseInt(varibles[0]);
				y=Integer.parseInt(varibles[1]);
				map16=Integer.decode("0x"+varibles[2]);
				input.map[x+coordx][y+coordy]=(byte)0xFF;
				input.data[x+coordx][y+coordy]=map16;
			}
			structure.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return(input);
	}
	
	String leveltostring(Level in){
		String output="";
		for(int f2=0;f2<in.height;f2++){
			for(int f=0;f<in.width;f++){
			output=output+in.map[f][f2];
			output=output+"*";
			}
			output=output+"\n";
		}
		return(output);
	}
	
	void mwlmanager(Level convert,boolean vertical){
		int sizex=0,sizey=16;
		if(vertical){
			sizex=32;
		}
		else {
			sizex=26;
		}
		int array[][]=new int[sizey][sizex];
		int exobjects[][]=new int[sizey][sizex];
		int map16map [][]=new int[sizey][sizex];
		int map16extend [][]=new int[sizey][sizex];
     boolean out=false;
		int mult=0;
		boolean first=true;
	while(true){
		for(int f2=0;f2<sizex;f2++){
			for(int f=0;f<sizey;f++){
				try{
				
				switch(convert.map[f+(mult*sizey)][f2]){
				case 34:
					array[f][f2]=0x05;
					exobjects[f][f2]=0x00;
				break;
				case 0:
					array[f][f2]=0x00;
					exobjects[f][f2]=0x00;
				break;
				case 16:
					array[f][f2]=0x09;
					exobjects[f][f2]=0x00;
				break;
				case 21:
				case 18:
					array[f][f2]=0x0A;
					exobjects[f][f2]=0x00;
				break;
				case 22:
					array[f][f2]=0x00;
					exobjects[f][f2]=0x30;
				break;
				case -111:
				case -99:
				case -103:
				case -107:
					array[f][f2]=0x06;
					exobjects[f][f2]=0x00;
				break;
				case -120:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x45;
				break;
				case -127:
				case -119:
				case -123:
				case -115:
					array[f][f2]=0x14;
					exobjects[f][f2]=0x00;
				break;
				case 10:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x33;
				break;
				case 11:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x34;
				break;
				case 26:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x35;
				break;
				case 27:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x36;
				break;
				case -125:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xE2;
				break;
				case -108:
					array[f][f2]=0x22;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x40;
				break;
				case -106:
					array[f][f2]=0x22;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x41;
				break;
				case -112:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4B;
				break;
				case -128:
				case -116:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x45;
				break;
				case -126:
				case -118:
				case -114:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x48;
				break;
				case -110:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4C;
				break;
				case -109:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xE4;
				break;
				case -122:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x03;
				break;
				case -124:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x01;
				break;
				case -76:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x02;
				break;
				case -74:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x04;
				break;
				case -87:
				case -83:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4E;
				break;
				case 17:
					array[f][f2]=0x00;
					exobjects[f][f2]=0x2D;
				break;
				case -88:
				case -84:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4D;
				break;
				case -104:
				case -100:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4B;
				break;
				case -102:
				case -98:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4C;
				break;
				case -86:
				case -82:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x4F;
				break;
				case 14:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x41;
					spriteinsert(mult,f,f2,0xC9);
				break;
				case 30:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x42;
				break;
				case 46:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0x43;
				break;
				case -97:
				case -101:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xE4;
				break;
				case -113:
				case -117:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xE2;
				break;
				case -65:
				case -69:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xEC;
				break;
				case -81:
				case -85:
					array[f][f2]=0x23;
					exobjects[f][f2]=0x00;
					map16map[f][f2]=0xED;
				break;
				case (byte)0xFF:
					if(convert.data[f+(mult*sizey)][f2]<0x100)array[f][f2]=0x22;
					else if (convert.data[f+(mult*sizey)][f2]<0x200)array[f][f2]=0x23;
					else array[f][f2]=0x27;
					exobjects[f][f2]=0x00;
					ByteBuffer move2 = ByteBuffer.allocate(4);
					move2.putInt(convert.data[f+(mult*sizey)][f2]);
					if(array[f][f2]==0x27){
					map16map[f][f2]=move2.get(2);
					map16extend[f][f2]=move2.get(3);
					}
					else map16map[f][f2]=move2.get(3);
				break;	
				default:
					array[f][f2]=0x0D;
					exobjects[f][f2]=0x00;
					System.out.print(convert.map[f+(mult*sizey)][f2]);
				}
				
				
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==1)spriteinsert(mult,f,f2,0x04);
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==2)spriteinsert(mult,f,f2,0x0F);
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==3)spriteinsert(mult,f,f2,0x13);
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==4)spriteinsert(mult,f,f2,0x05);
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==5)spriteinsert(mult,f,f2,0x4F);
				if(convert.spriteTemplates[f+(mult*sizey)][f2]==10)spriteinsert(mult,f,f2,(byte)externalsprites[f+(mult*sizey)][f2]);
				if(!vertical&&f+(mult*sizey)==convert.xExit&&f2==convert.yExit)spriteinsert(mult,f,f2-1,0x7B);
				
				}
				catch(ArrayIndexOutOfBoundsException e){
					out=true;
					
				}
				if(out==true)break;
				
			}
			if(out==true)break;
		}
		if(out==true)break;
		
		if(first==true){
			arraytomwlpage(array,false,false,exobjects,map16map,map16extend);
			first=false;
		}
		else if(out==true)arraytomwlpage(array,true,false,exobjects,map16map,map16extend);
		else arraytomwlpage(array,false,true,exobjects,map16map,map16extend);
		
		
		mult++;
	}
		
	
		export();
		
		
		
	}

	void mwlmanagersmall(Level convert,boolean vertical){
		int sizex=0,sizey=16;
		if(vertical){
			sizex=32;
		}
		else {
			sizex=26;
		}
		int array[][]=new int[sizey][sizex];
		int exobjects[][]=new int[sizey][sizex];
		int map16map [][]=new int[sizey][sizex];
		int map16extend [][]=new int[sizey][sizex];
     boolean out=false;
		int mult=0;
		boolean first=true;
	while(true){
		for(int f2=0;f2<sizex;f2++){
			for(int f=0;f<sizey;f++){
				try{
					switch(convert.map[f+(mult*sizey)][f2]){
					case 1:
					array[f][f2]=0x27;
					exobjects[f][f2]=0x00;
					ByteBuffer move2 = ByteBuffer.allocate(4);
					move2.putInt(convert.data[f+(mult*sizey)][f2]);
					map16map[f][f2]=move2.get(2);
					map16extend[f][f2]=move2.get(3);
					if(convert.spriteTemplates[f+(mult*sizey)][f2]!=0)spriteinsert(mult,f,f2,(byte)convert.spriteTemplates[f+(mult*sizey)][f2]);
					break;
					case 2:
						array[f][f2]=convert.data[f+(mult*sizey)][f2];
						exobjects[f][f2]=0x00;
					break;	
					case 3:
						array[f][f2]=0x00;
						exobjects[f][f2]=convert.data[f+(mult*sizey)][f2];
					break;	
					}
				}
				catch(ArrayIndexOutOfBoundsException e){
					out=true;
					
				}
				if(out==true)break;
				
			}
			if(out==true)break;
		}
		if(out==true)break;
		
		if(first==true){
			arraytomwlpage(array,false,false,exobjects,map16map,map16extend);
			first=false;
		}
		else if(out==true)arraytomwlpage(array,true,false,exobjects,map16map,map16extend);
		else arraytomwlpage(array,false,true,exobjects,map16map,map16extend);
		
		
		mult++;
	}
		export();
	}
	
	void customheader(byte bytesin[]){
		for(int g=0;g<5;g++)array[g]=bytesin[g];
	}
	
	byte[] headerbase(int comp){
    	byte startingheader[]=new byte[5];
    	switch(comp){
    	case 0:
    		startingheader[0]=(byte)0x33;
    		startingheader[1]=(byte)0x40;
    		startingheader[2]=(byte)0x08;
    		startingheader[3]=(byte)0x80;
    		startingheader[4]=(byte)0x27;
    	break;
		case 1:
			startingheader[0]=(byte)0xD3;
			startingheader[1]=(byte)0x60;
			startingheader[2]=(byte)0x13;
			startingheader[3]=(byte)0xE7;
			startingheader[4]=(byte)0x23;
		break;
		case 2:
			startingheader[0]=(byte)0x73;
			startingheader[1]=(byte)0x60;
			startingheader[2]=(byte)0x31;
			startingheader[3]=(byte)0xCB;
			startingheader[4]=(byte)0x11;
		break;
    	}
    	return(startingheader);
	}
	
	
	void arraytomwlpage(int in[][],boolean end,boolean newscreen,int extend[][],int map16[][],int moremap16[][]){
			boolean minobject=false;
			for(int f2=0;f2<in[0].length;f2++){
				for(int f=0;f<in.length;f++){
				if(in[f][f2]!=0||extend[f][f2]!=0){
					byte x,y;
					minobject=true;
							  
					y=(byte)f2;
					x=(byte)f;
					if(newscreen==true){
						array[arraystart]=(byte)((0x80|y)|((in[f][f2]<<1)&0x60));
						newscreen=false;
					}
					else array[arraystart]=(byte)((0x00|y)|((in[f][f2]<<1)&0x60));
					array[(arraystart+1)]=(byte)((byte)in[f][f2]<<4|x);
					if(extend[f][f2]!=0)array[(arraystart+2)]=(byte)extend[f][f2];
					else array[(arraystart+2)]=(byte)0x00;
					arraystart+=3;
					if(in[f][f2]==0x22||in[f][f2]==0x23){
						array[arraystart]=(byte)map16[f][f2];
						arraystart++;
					}
					if(in[f][f2]==0x27){
						array[arraystart]=(byte)map16[f][f2];
						array[arraystart+1]=(byte)moremap16[f][f2];
						arraystart+=2;
					}
					
				}
				}
				}
			
			if(minobject==false&&newscreen==true){
				array[arraystart]=(byte)0xC0;
				array[(arraystart+1)]=(byte)0x70;
				array[(arraystart+2)]=(byte)0x00;
				array[(arraystart+3)]=(byte)0x00;
				array[(arraystart+4)]=(byte)0x25;
				arraystart+=5;
			}
			
	}
	
	void spriteinsert(int page,int x,int y,int what){
		spriteenabled=true;
		//System.out.print("sprite page:"+page+" x:"+x+" y:"+y+" sprite:0x"+Integer.toHexString(what));
	   spritedata[spritetrack]=(byte)((((y>>4)&0x01)|((y<<4)&0xF0))|((page>>3)&0x02));
	   spritedata[spritetrack+1]=(byte)((x<<4)|(page&0x0F));
	   spritedata[spritetrack+2]=(byte)what;
	   spritetrack+=3;
}
	void setspriteheader(int in){
		spritedata[0]=(byte)in;
	}
	
	void deletevansprites(){
		if(spriteenabled==false){
			spritedata[0]=(byte)0x04;
			spriteenabled=true;
		}
	}
	
	byte[] affiliatesizetable(int in){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(Integer.reverseBytes(in));
		return(bb.array());
	}
	
	void buildfoundation(){
		
		Path source = Paths.get("foundation.mwl");
		Path destination = Paths.get("level.mwl");
 
		try {
			Files.copy(source, destination);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}
	
	void export(){
		try {
			buildfoundation();
			RandomAccessFile mwl = new RandomAccessFile("level.mwl", "rw");
			//mwl stuff
			mwl.seek(0x4C);
			mwl.write(affiliatesizetable(arraystart+8),0,4);//should be +1 but it causes corruption +8 for the 8 0s header that precedes the level and real header
			//end mwl stuff
			mwl.seek(0xB1A);
			/*another mwl thing*/for(int mwlheader=0;mwlheader<8;mwlheader++)mwl.write(((byte)0x00));//7 or 8
			mwl.write(array,0,arraystart);
			mwl.write((byte)0xFF);
			if(spriteenabled==true){
			long pointloc=mwl.getFilePointer();
			mwl.seek(0x58);
			mwl.write(affiliatesizetable((int)pointloc));
			mwl.write(affiliatesizetable(spritetrack+8),0,4);
			mwl.seek(pointloc);
			for(int cow=0;cow<8;cow++)mwl.write((byte)0x00);
			mwl.write(spritedata,0,spritetrack);
			mwl.write((byte)0xFF);
			}
			mwl.close();
			System.out.print("Success");
		} catch (IOException e) {
			System.out.print("Fail");
			e.printStackTrace();
		}
	}
	
	
}
