package netty.echo.objectEcho;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String  toString() {
		return new StringBuilder().append("user:[")
											.append("id:").append(id).append("--")
											.append("name:").append(name).append("]").toString();
		
	}
}
