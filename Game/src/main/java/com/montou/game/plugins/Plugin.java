package com.montou.game.plugins;

public class Plugin {
	
	private PluginType type;
	private boolean useCustomData;
	private Class clazz;
	private Object instance;
	
	public Plugin(PluginType type, boolean useCustomData, Class clazz) {
		this.type = type;
		this.useCustomData = useCustomData;
		this.clazz = clazz;
	}
	
	public PluginType getType() {
		return this.type;
	}
	
	public boolean useCustomData() {
		return this.useCustomData;
	}
	
	public Class getClazz() {
		return this.clazz;
	}
	
	public String getName() {
		return this.clazz.getName();
	}
	
	public Object getInstance() throws InstantiationException, IllegalAccessException {
		if (this.instance == null)
			this.instance = this.clazz.newInstance();
		return this.instance;
	}
	
	@Override
	public String toString() {
		return clazz.getSimpleName();
	}
}
