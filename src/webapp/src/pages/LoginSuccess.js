import React, { Component } from 'react'
import { Layout, Page, SkeletonBodyText } from "@shopify/polaris";
import { getShopOrigin, setShopOrigin } from '../utils/Utils';

class LoginSuccess extends Component {
    constructor() {
        super();
    }

    getStoreData = () => {
        window.location.href = "/dashboard";;
    }
    componentDidMount() {
        debugger;
        const params = window.location.pathname.split("/");
        if (params.length > 2) {
            let host = decodeURIComponent(params[2]);
            // const params = new URLSearchParams(window.location.search);
            // let token = params.get('token');
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
            }
        }
        this.getStoreData();
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
