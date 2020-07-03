package PhaserAnalysis;

import java.io.*;
import java.util.Set;


public class PhaserMain {
//	public void toTxt(String addr){
//		FileWriter fw = null;
//		try {
//			File f=new File("resource/PhaserProcess");
//		    fw = new FileWriter(f, true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		PrintWriter pw = new PrintWriter(fw);
//		pw.println("追加内容");
//		pw.flush();
//		try {
//		fw.flush();
//		pw.close();
//		fw.close();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//		}
	
	public static void main(String[] args) throws Exception{
		//输出到文件模块
		FileWriter fw = null;
		try {
			File f=new File("resource/PhaserProcess.txt");
		    fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		//输出到文件模块
		
		
		String grammarAddr = "resource/phasergrammar.txt";
		ScanProgram sp = new ScanProgram();
		String[] gm = sp.readToString(grammarAddr);
		
		String middAddr = "resource/midd.txt";
		ScanProgram sp2 = new ScanProgram();
		String[] midd = sp2.readToString(middAddr);
		
		LL_Analysis la = new LL_Analysis(gm.length,midd[0]);
		la.setGrammar(gm);
		la.setIsEmptyLines();
		
		//输出文法
		char[][] grammar = la.getGrammar();
		for(int i=0;i<grammar.length;i++){
			System.out.print(i+" :");
			for(int j=0;j<grammar[i].length;j++){
				System.out.print(grammar[i][j]+" ");
			}
			System.out.println(la.isEmptyLines[i]);
		}
		System.out.println("_______________________");
		//first 测试
		System.out.println("First集");
		pw.println("First集:");
		la.getAllFirst();
		for(int i=0;i<la.numsVn;i++){
			char vn = la.Vn[i];
			System.out.print("first("+vn+"): {");
			pw.print("first("+vn+"): {");
			Set<Character> temp1 = la.first[vn].list;
			for(char ch:temp1){
				System.out.print(ch+" ");
				pw.print(ch+" ");
			}
			System.out.println("}");
			pw.println("}");
		}
		System.out.println("_____________________________________________________________________");
		pw.println("_____________________________________________________________________");
		//follow 测试
		System.out.println("Follow集:");
		pw.println("Follow集:");
		la.getFollow();
		for(int i=0;i<la.numsVn;i++){
			char vn = la.Vn[i];
			System.out.print("follow("+vn+"): {");
			pw.print("follow("+vn+"): {");
			Set<Character> temp2 = la.follow[vn].follow;
			for(char ch:temp2){
				System.out.print(ch+" ");
				pw.print(ch+" ");
			}
			System.out.println("}");
			pw.println("}");
		}
		System.out.println("_____________________________________________________________________");
		pw.println("_____________________________________________________________________");
		
		//测试select
		System.out.println("Select集:");
		pw.println("Select集:");
		la.getSelect();
		for(int i=0;i<la.select.length;i++){
			System.out.print("No."+i+" :");
			pw.print("No."+i+" :");
			Set<Character> temp3 = la.select[i].select;
			for(char ch:temp3){
				System.out.print(ch+" ");
				pw.print(ch+" ");
			}
			System.out.println();
			pw.println();
		}
		System.out.println("_____________________________________________________________________");
		pw.println("_____________________________________________________________________");
		//测试预测分析表
		
		la.setTable();
		System.out.print("\t");
		pw.print("   ");
		for(int i=0;i<la.numsVt;i++){
			System.out.print(la.Vt[i]+"\t");
			pw.print(la.Vt[i]+"   ");
		}
		System.out.println('#');
		pw.println('#');
		for(int i=0;i<la.PredictTable.length;i++){
			System.out.print(la.Vn[i]+"\t");
			pw.print(la.Vn[i]+"   ");
			for(int j=0;j<la.PredictTable[i].length;j++){
				if(la.PredictTable[i][j]==-1){
					System.out.print("\t");
					pw.print("   ");
				}
				else{
					System.out.print(la.PredictTable[i][j]+"\t");
					pw.print(la.PredictTable[i][j]+"   ");
				}
			}
			System.out.println();
			pw.println();
		}
		System.out.println("_____________________________________________________________________");
		
		//pt
		la.StackAnalysis();
		System.out.println("_____________________________________________________________________");
		
		//关闭
		pw.flush();
		try {
		fw.flush();
		pw.close();
		fw.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
//		for(int i=0;i<la.testInputs.length();i++){
//			System.out.print(la.testInputs.charAt(i));
//		}
	}
}
