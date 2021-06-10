import { AppLink } from "@shopify/app-bridge/actions";
import { Banner, Page } from "@shopify/polaris";
import isEmpty from "is-empty";
import React from "react";
import { Link, Route } from "react-router-dom";
import { getStoreDetails } from "../utils/Utils";



const AppLayoutRoute = ({ component: Component, ...rest }) => {

    let storeDetail = getStoreDetails();
    if (!isEmpty(storeDetail)) {
        storeDetail = JSON.parse(storeDetail);
    }
    if (storeDetail && storeDetail.currentBillingStatus == true) {
        return (
            <Route
                render={(matchProps) => (
                    <Component {...matchProps} {...rest} />
                )}
            />
        );
    }
    else {
        return (
            <Route
                render={(matchProps) => (
                    <React.Fragment>
                        <Component {...matchProps} {...rest} />
                        <Page
                            fullWidth="true">
                            <Banner
                                action={{ content: "Pay Now", url: "billing" }}
                                status="critical"
                                title="Enable Billing"
                            >
                            </Banner>
                        </Page>
                    </React.Fragment>

                )}
            />
        );
    }

};

export default AppLayoutRoute;
