/**
  * MyFaces - the free JSF implementation
  * Copyright (C) 2003, 2004  The MyFaces Team (http://myfaces.sourceforge.net)
  *
  * This library is free software; you can redistribute it and/or
  * modify it under the terms of the GNU Lesser General Public
  * License as published by the Free Software Foundation; either
  * version 2.1 of the License, or (at your option) any later version.
  *
  * This library is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this library; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package javax.faces.event;

import javax.faces.component.UIComponent;

/**
  * @author Thomas Spiegl (latest modification by $Author$)
  * @version $Revision$ $Date$
*/
public class ActionEvent extends FacesEvent
{

	// FIELDS

	// CONSTRUCTORS
	public ActionEvent(UIComponent uiComponent)
	{
        super(uiComponent);
        if (uiComponent == null) throw new IllegalArgumentException("uiComponent");
	}

	// METHODS
	public boolean isAppropriateListener(FacesListener facesListeners)
	{
		return facesListeners instanceof ActionListener;
	}

	public void processListener(FacesListener facesListeners)
	{
		((ActionListener)facesListeners).processAction(this);
	}

}
