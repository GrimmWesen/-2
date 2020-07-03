package PhaserAnalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScanProgram {
	/**
	 * @param filePath
	 * @return 
	 * ���ķ�ת���ַ������飬���зָ�
	 */
	public  String[] readToString(String filePath)
    {
        File file = new File(filePath);
        Long filelength = file.length(); // ��ȡ�ļ�����
        byte[] filecontent = new byte[filelength.intValue()];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        String[] fileContentArr = new String(filecontent).split("\r\n");
        
        return fileContentArr;// �����ļ�����,Ĭ�ϱ���
    }
	
	
}
