package com.codeaholicguy.gearman.config;

/**
 * @author hoangnn
 */
public class Configuration {

    private String instance;
    private String host;
    private int port;
    private int size;
    private int idleTime;
    private String function;
    private String className;

    public Configuration(String instance, String host, int port, int size, int idleTime, String function, String className) {
        this.instance = instance;
        this.host = host;
        this.port = port;
        this.size = size;
        this.idleTime = idleTime;
        this.function = function;
        this.className = className;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Configuration{");
        sb.append("instance='").append(instance).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append(", size=").append(size);
        sb.append(", idleTime=").append(idleTime);
        sb.append(", function='").append(function).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
