package com.montou.game.plugins;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.montou.game.plugins.annotations.AAttack;
import com.montou.game.plugins.annotations.AGraphic;
import com.montou.game.plugins.annotations.AMovement;
import com.montou.game.shared.GameInformations;

public class PluginsLoader extends ClassLoader {
	
	private String jarPath;
	
	public PluginsLoader(String path) throws ClassNotFoundException {
		this.jarPath = path;
		
		// Injection des classes "shared" pour la résolution des liens...
		this.loadClass("com.montou.game.shared.Direction");
		this.loadClass("com.montou.game.shared.GameInformations");
	}
	
	public List<Plugin> loadPlugins() {
		System.out.println(String.format("Chargement des plugins depuis '%s'...", this.jarPath));
		ArrayList<Plugin> plugins = new ArrayList<Plugin>();
		
		try {
			JarFile jarFile = new JarFile(this.jarPath);
			Enumeration<? extends JarEntry> en = jarFile.entries();
			while(en.hasMoreElements()) {
				// Tant qu'il y a des élements dans l'archive...
				JarEntry jarEntry = en.nextElement();
				if (jarEntry.getName().endsWith(".class")) {
					// Si il s'agit bien d'une classe...
					Class clazz = this.loadClass(jarEntry.getName(), true); 
					if (clazz != null) {
						Annotation[] clazzAnns = clazz.getAnnotations();
						if (clazzAnns != null && clazzAnns.length > 0) {
							for(int i = 0; i < clazzAnns.length; i++) {
								Annotation ann = clazzAnns[i];
								if (ann instanceof AGraphic) {
									plugins.add(new Plugin(PluginType.GRAPHIC, ((AGraphic) ann).useCustomData(), clazz));
								} else if (ann instanceof AAttack) {
									plugins.add(new Plugin(PluginType.ATTACK, ((AAttack) ann).useCustomData(), clazz));
								} else if (ann instanceof AMovement) {
									plugins.add(new Plugin(PluginType.MOVEMENT, ((AMovement) ann).useCustomData(), clazz));
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(String.format("%s plugin(s) chargé(s) !", plugins.size()));
		
		return plugins;
	}
	
	@Override
	public Class findClass(String url) throws ClassNotFoundException {
		
		// Hotfix pour la résolution des liens...
		if (!url.contains(".class")) {
			return Class.forName(url);
		}
		
		try {
			ZipFile zf = new ZipFile(new File(this.jarPath));
			
			Enumeration<? extends ZipEntry> en = zf.entries();
			while (en.hasMoreElements()) {
				// Tant qu'il y a des élements dans l'archive...
				ZipEntry e = en.nextElement();
				if (e != null && url.equals(e.getName())) {
					// Si le nom correspond...
					byte[] buffer = readFromZipEntry(zf.getInputStream(e));
					String className = url.split(".class")[0].replace("/", ".");
					return defineClass(className, buffer, 0, buffer.length);
				}
			}
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private byte[] readFromZipEntry(InputStream in) {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		
		try {
			int i;
			while ((i = bufferedInputStream.read(buffer)) > 0) {
				byteArrayOutputStream.write(buffer, 0, i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteArrayOutputStream.toByteArray();
	}
}
