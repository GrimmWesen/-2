package LexicalAnalysis;

import java.io.*;
import java.util.*;
public class ScanProgram {
	private String sourceAddr = null;
	private char[] origin;
	private char[] done;//ȥ��ע��
	
	public ScanProgram(String sourceAddr){
		this.sourceAddr = sourceAddr;
	}
	public char[] getdone(){
		return done;
	}
	public void scan(){
		try{
			File file  = new File(sourceAddr);
			FileReader fr = new FileReader(file);
			int length = (int) file.length();//�ֽ��������ַ��� ����Ϊchar��Сһ���ֽ�
			origin = new char[length+1];
			fr.read(origin);
			fr.close();
		}
		catch(IOException e){	
			e.printStackTrace();
		}
		
	}
	public void  flitter(){
    	int cnt=0;
    	done = new char[origin.length];
    	for(int i =0;i<origin.length;i++){
    		if(origin[i]=='/'&&origin[i+1]=='/'){
    			while(origin[i]!='\n'){
    				i++;
    			}
    		}
    		if(origin[i]=='/'&&origin[i+1]=='*'){
    			i=i+2;
    			while(origin[i]!='*'||origin[i+1]!='/'){
    				i++;
    			}
    			i=i+2;
    		}
    			done[cnt++]=origin[i];
    		
    	}
   
    }
	//����ע��
//	public void flitter(){
//    	int cnt=0;
//    	int cnts=0;
//    	char [] temp = new char[origin.length];
//    	for(int i =0;i<origin.length;i++){
//    		if(origin[i]=='/'&&origin[i+1]=='/'){
//    			while(origin[i]!='\n'){
//    				i++;
//    			}
//    		}
//    		if(origin[i]=='/'&&origin[i+1]=='*'){
//    			i=i+2;
//    			while(origin[i]!='*'||origin[i+1]!='/'){
//    				i++;
//    			}
//    			i=i+2;
//    		}
//    			temp[cnt++]=origin[i];
//    	}
//    	for(int i=0;i<temp.length;i++){
//    		if((int)temp[i]!=0&&(int)temp[i]!=10&&(int)temp[i]!=13&&(int)temp[i]!=9&&(int)temp[i]!=32){
//    			cnts++;
//    		}
//    	}
//    	int count=0;
//    	done = new char[cnts];
//    	for(int i=0;i<temp.length;i++){
//    		if((int)temp[i]!=0&&(int)temp[i]!=10&&(int)temp[i]!=13&&(int)temp[i]!=9&&(int)temp[i]!=32){
//    			done[count++]=temp[i];
//    		}
//    	}
//    }
}
