import React, { Component } from 'react'
import { ApiUrl, shopifyApiKey } from '../Constants';
import { Provider, Context } from '@shopify/app-bridge-react';
import createApp from '@shopify/app-bridge';
import { Redirect } from '@shopify/app-bridge/actions';
import { SkeletonPage } from '@shopify/polaris';

export default class StoreInstall extends Component {
    static contextType = Context;
    componentDidMount() {
        // debugger;
        let server_redirect_url = ApiUrl + "/store_install" + window.location.search;
        const app = this.context;
        let redirect = Redirect.create(app);
        redirect.dispatch(Redirect.Action.REMOTE, server_redirect_url);
    }
    render() {
        return (
            <div>
                <SkeletonPage>

                </SkeletonPage>
            </div>
        )
    }
}