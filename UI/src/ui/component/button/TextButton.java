package ui.component.button;

import ui.Component;
import ui.component.Label;

public class TextButton extends Button
{
	public Label label;
	
	public TextButton(String text)
	{
		label = new Label();
		label.text = text;
		label.width.ratio = 1;
		label.height.ratio = 1;
		label.foreColor.addParent(content.foreColor, 1);
	}
	
	/**
	 * Sets the parent of this Component.
	 * @param p The parent component.
	 */
	@Override
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		label.setParent(content);
	}
}
