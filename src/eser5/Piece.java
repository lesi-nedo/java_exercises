/**
 * 
 */
package eser5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author nedo1993
 *
 */
public class Piece {
	private String path;
	private String file_names;
	private String dir_names;
	private FileOutputStream file_files;
	private FileOutputStream file_dir;
	private String abs_file_name;
	private String abs_dir_name;
	private static Charset charset = StandardCharsets.UTF_16;
	Path currentRelativePath;
	String s;
	public Piece(String path, String file_names, String dir_names) throws FileNotFoundException {
		this.path=path;
		this.file_names=file_names;
		this.dir_names=dir_names;
		this.currentRelativePath = Paths.get("");
		this.s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current absolute path is: " + s);
		this.file_files=new FileOutputStream((this.abs_file_name = path+"/"+this.file_names), true);
		this.file_dir=new FileOutputStream((this.abs_dir_name=path+"/"+this.dir_names), true);
		Piece.start_tran(this.path, file_files, file_dir);
	}
	public static void start_tran(String path, FileOutputStream file_files, FileOutputStream file_dir) {
		Path dir = Paths.get(path);
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
			for(Path file: stream) {
				if(file.toFile().isFile()) {
					file_files.write((file.getFileName().toString()+"\n").getBytes(Piece.charset));
				} else {
					file_dir.write((file.getFileName().toString()+"\n").getBytes(Piece.charset));
					start_tran(file.toAbsolutePath().toString(), file_files, file_dir);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		
	}
}
