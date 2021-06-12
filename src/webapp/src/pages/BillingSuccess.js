import React, { Component } from 'react'
import { Layout, Page, SkeletonBodyText } from "@shopify/polaris";
import { getShopOrigin, setShopOrigin, setStoreDetails } from '../utils/Utils';
import { Redirect } from '@shopify/app-bridge/actions';
import { Context } from '@shopify/app-bridge-react';
import Api from '../apis/Api';
class BillingSuccess extends Component {
    static contextType = Context;
    constructor() {
        super();
    }

    getStoreData = async () => {
        const app = this.context;
        window.app = app;
        let response = await Api.getStoreData();
        if (response.status == 200) {
            if (response.data.success) {
                let resData = response.data.data;
                setStoreDetails(resData);
                console.log(response);
                const redirect = Redirect.create(app);
                redirect.dispatch(Redirect.Action.APP, "/dashboard");
                return;
            }
        }
        console.log(response);
        const redirect = Redirect.create(app);
        redirect.dispatch(Redirect.Action.APP, "/billing");
    }
    autoRedirect = () => {
        // debugger;
        const app = this.context;
        if (window.top == window.self) {
        }
        else {
            this.getStoreData();
        }
    }

    componentDidMount() {
        this.autoRedirect();
    }

    render() {
        return (
            <Page separator>
                <Layout>
                    <Layout.Section>
                        <SkeletonBodyText />
                    </Layout.Section>
                </Layout>
            </Page>
        );
    }
}

export default BillingSuccess;