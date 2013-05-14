package org.openxava.ex.editor.shell;

public interface IShellEditor {
	public String render(ShellEditorContext ctx);
	public String renderReadOnly(ShellEditorContext ctx);
}
