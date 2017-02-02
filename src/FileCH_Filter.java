import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileCH_Filter extends FileFilter{
	public boolean accept(File f){
	    if (f.isDirectory()){
			return true;
	    }
	    String extension = getExtension(f);
		if (extension.equals("txt")){
			return true;
		}
		return false;
	}
	public String getDescription(){
		return "Supported Image Files";
	}
	protected static String getExtension(File f){
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1){
			return s.substring(i+1).toLowerCase();
		}
		return "";
	}
}
