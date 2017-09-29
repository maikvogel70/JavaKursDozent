package w7t4_Dozent;

// Ein CheckBox, der ein beliebiges Objekt übergeben werden kann.
// Die Methode toString() des übergebenen Objekts zeigt den
// Text der CheckBox an.

import javax.swing.JCheckBox;

public class JCheckBoxListItem<T> extends JCheckBox
{

	private T item;

	public JCheckBoxListItem(T item)
	{
		setItem(item);
	}

	public T getItem()
	{
		return item;
	}

	public void setItem(T item)
	{
		this.item = item;
		this.setText(item.toString());
	}
}