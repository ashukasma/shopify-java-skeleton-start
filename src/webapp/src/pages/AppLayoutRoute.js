import React from "react";
import { Route } from "react-router-dom";
import { Provider } from '@shopify/app-bridge-react'
import { getShopOrigin } from "../utils/Utils";
import { shopifyApiKey } from "../Constants";

const AppLayoutRoute = ({ component: Component, ...rest }) => {
    let shopOrigin = getShopOrigin();
    const config = { apiKey: shopifyApiKey, host: shopOrigin };
    return (
        <Route
            render={(matchProps) => (
                <Provider config={config}>
                    <Component {...matchProps} {...rest} />
                </Provider>
            )}
        />
    );
};

export default AppLayoutRoute;
