/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.odata;

import com.microsoft.office365.odata.interfaces.DependencyResolver;

public class EntryPoint extends BaseODataContainer {

    public EntryPoint(String url, DependencyResolver resolver) {
        super(url, resolver);
    }

    public UserODataComponent getMe() {
        return new UserODataComponent("Me", this);
    }
    public UserODataComponent getUsers() {
        return new UserODataComponent("Users", this);
    }
}