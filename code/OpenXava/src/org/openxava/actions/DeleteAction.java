package org.openxava.actions;

import java.util.*;

import org.apache.commons.logging.*;
import org.openxava.model.*;
import org.openxava.util.*;
import org.openxava.validators.*;

/**
 * @author Javier Paniza
 */

public class DeleteAction extends ViewDetailAction {
	private static Log log = LogFactory.getLog(DeleteAction.class);
	
	public DeleteAction() {
		setIncrement(0);
	}
	
	public void execute() throws Exception { 
		if (getView().isKeyEditable()) {
			addError("no_delete_not_exists");
			return;
		}
		Map keyValues = getView().getKeyValues();
		try {
			MapFacade.remove(getModelName(), keyValues);
			resetDescriptionsCache();
		}
		catch (ValidationException ex) {
			addErrors(ex.getErrors());	
			return;
		}		
		addMessage("object_deleted", getModelName());
		getView().clear();
		boolean selected = false;
		if (getTab().hasSelected()) {
			Map k = calculateNextKey(keyValues);
			if (k == null) setDeleteAllSelected(true);
			else setNextKey(k);
			removeSelected(keyValues);
			selected = true;
		}
		else getTab().reset();		 		
		super.execute(); // viewDetail
		if (isNoElementsInList()) {
			if (
				(!selected && getTab().getTotalSize() > 0) ||
				(selected && getTab().getSelectedKeys().length > 0)
			) {				
				setIncrement(-1);
				getErrors().remove("no_list_elements");								
				super.execute();													
			}
			else {							
				getView().setKeyEditable(false);
				getView().setEditable(false);
			}
		}
		getErrors().clearAndClose(); // If removal is done, any additional error message may be confused
	}
	
	private void removeSelected(Map keyValues) throws XavaException {
		getTab().deselect(keyValues);
	}

	private Map calculateNextKey(Map keyValues){
		// por defecto al borrar cogemos el siguiente, si no hay siguiente cogemos el anterior y si no devolvemos null
		List l = Arrays.asList(getTab().getSelectedKeys());
		int index = l.indexOf(keyValues);
		int cantidad = l.size();
		
		if (cantidad == 1) return null;	// solo hay un elemento y lo vamos a borrar
		else if (cantidad-1 == index) return (Map) l.get(index - 1);	// estamos en el último elemento de la lista por lo que cogemos el anterior
		else return (Map) l.get(index + 1);	// cogemos el siguiente registro	
	}
	
}


