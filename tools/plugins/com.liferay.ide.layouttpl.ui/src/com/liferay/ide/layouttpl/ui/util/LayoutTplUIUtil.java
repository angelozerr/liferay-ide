/*******************************************************************************
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************/

package com.liferay.ide.layouttpl.ui.util;

import com.liferay.ide.layouttpl.core.model.ModelElement;
import com.liferay.ide.layouttpl.core.model.PortletColumnElement;
import com.liferay.ide.layouttpl.core.model.PortletLayoutElement;
import com.liferay.ide.layouttpl.ui.parts.LayoutTplDiagramEditPart;
import com.liferay.ide.layouttpl.ui.parts.PortletLayoutEditPart;
import com.liferay.ide.project.core.facet.IPluginFacetConstants;
import com.liferay.ide.project.core.util.ProjectUtil;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;

/**
 * @author Greg Amerson
 */
public class LayoutTplUIUtil
{

    public static int getColumnIndex( PortletLayoutElement currentParent, PortletColumnElement column )
    {
        if( currentParent == null || column == null )
        {
            return -1;
        }

        List<ModelElement> cols = currentParent.getColumns();

        for( int i = 0; i < cols.size(); i++ )
        {
            if( column.equals( cols.get( i ) ) )
            {
                return i;
            }
        }

        return -1;
    }

    public static int getRowIndex( PortletLayoutEditPart layoutEditPart )
    {
        if( layoutEditPart == null )
        {
            return -1;
        }

        LayoutTplDiagramEditPart diagramPart = (LayoutTplDiagramEditPart) layoutEditPart.getParent();
        Object[] rows = diagramPart.getChildren().toArray();

        for( int i = 0; i < rows.length; i++ )
        {
            if( layoutEditPart.equals( rows[i] ) )
            {
                return i;
            }
        }

        return -1;
    }

    public static boolean isCreateRequest( Class<?> class1, Request request )
    {
        if( !( request instanceof CreateRequest ) )
        {
            return false;
        }

        if( !( ( (CreateRequest) request ).getNewObject().getClass() == class1 ) )
        {
            return false;
        }

        return true;
    }

    public static boolean isLayoutTplProject( IProject project )
    {
        return ProjectUtil.hasFacet( project, IPluginFacetConstants.LIFERAY_LAYOUTTPL_PROJECT_FACET );
    }

    public static int adjustWeight( int newWeight )
    {
        int retval = -1;

        // make sure that new weight is valid

        //use 35 instead of 34 because the 33 and 66 situations should be corresponding by a sum of 100
        //or when 66 is in 66, 34 is not in 33 but 35
        if( newWeight > 31 && newWeight < 35 )
        {
            retval = 33;
        }
        else if( newWeight > 65 && newWeight < 69 )
        {
            retval = 66;
        }
        else
        {
            retval = (int) Math.round( (double) newWeight / (double) 5 ) * 5;
        }

        return retval;
    }

}
