package util;

public class ListItem<V, E>
{

	private V valueMember;
	private E displayMember;
	
	
	public ListItem(V valueMember, E displayMember)
	{
		
		this.valueMember = valueMember;
		this.displayMember = displayMember;
		
	}


	public V getValueMember()
	{
		return valueMember;
	}


	public E getDisplayMember()
	{
		return displayMember;
	}


	@Override
	public String toString()
	{
		return getDisplayMember().toString();
	}
	
	
	
	
	
	
}
