package net.sourceforge.texlipse;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class FileOpener implements IStartup {

	public void earlyStartup() {
		System.err.println("Starting early");
		try {
			startListening();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void startListening() throws Exception {
		ServerSocket serverSocket = new ServerSocket(6789);
		Socket socket = null;
		while ((socket = serverSocket.accept()) != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String command = reader.readLine();
				command = command.substring(6, command.length()-9).trim();
				String file = command.substring(0, command.lastIndexOf("-"));
				String line = command.substring(command.lastIndexOf("-")+1, command.length());
				IFile iFile = (IFile) ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new File(file).toURI())[0];
				openEditorAt(iFile, Integer.parseInt(line));
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			finally {
				socket.close();
			}
		}
	}
	
	protected void openEditorAt(final IFile file, final int line) {
		if (file == null) return;
		
		final FileEditorInput fileinput=new FileEditorInput(file);
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					int realLine = line;
					if (realLine == 0) realLine = 1;
					
					AbstractTextEditor editor = (AbstractTextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(fileinput,desc.getId(), false);		
					IDocument doc = editor.getDocumentProvider().getDocument(fileinput);
					editor.selectAndReveal(doc.getLineOffset(realLine - 1),doc.getLineLength(realLine - 1) - 1);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}				
			}
		});
	}
}
