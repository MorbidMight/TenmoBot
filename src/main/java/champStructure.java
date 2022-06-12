
public class champStructure {
	private String id;
	private String name;
	champStructure(String i, String n)
	{
		id = i;
		name = n;
	}
	
	public String getId() {return id;}
	public String getName() {return name;}
	public String toString() {String x = "("+ id + ", " + name +")"; return x;}
}
