import React, { Component } from 'react'
import { Layout, Page, SkeletonBodyText } from "@shopify/polaris";
import { getShopOrigin, setShopOrigin, setStoreDetails } from '../utils/Utils';
import { Redirect } from '@shopify/app-bridge/actions';
import { Context } from '@shopify/app-bridge-react';
import Axios from 'axios';
import { ApiUrl } from '../Constants';
import Api from '../apis/Api';
class LoginSuccess extends Component {
    static contextType = Context;
    constructor() {
        super();
    }

    getStoreData = async () => {
        const app = this.context;
        let response = await Api.getStoreData(app);
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
        redirect.dispatch(Redirect.Action.APP, "/login-failed");
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
        // debugger;
        const params = window.location.pathname.split("/");
        if (params.length > 2) {
            let host = decodeURIComponent(params[2]);
            if (host) {

            } else {
                host = getShopOrigin();
            }
            if (host) {
                setShopOrigin(host);
            }

        }
        else {
            let host = getShopOrigin();
            if (host) {
                setShopOrigin(host);
                // const redirect = Redirect.create(app);
                // redirect.dispatch(Redirect.Action.APP, "/dashboard");
            }
        }
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

export default LoginSuccess;
