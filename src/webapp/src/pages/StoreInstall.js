import React, { Component } from 'react'
import { ApiUrl, shopifyApiKey } from '../Constants';
import { Provider, Context } from '@shopify/app-bridge-react';
import createApp from '@shopify/app-bridge';
import { Redirect } from '@shopify/app-bridge/actions';
import { SkeletonPage } from '@shopify/polaris';
import isEmpty from 'is-empty';

export default class StoreInstall extends Component {
    static contextType = Context;
    componentDidMount() {
        const app = this.context;
        const params = new URLSearchParams(window.location.search)
        let shopOrigin = params.get('host');
        if (isEmpty(shopOrigin)) {
            const redirect = Redirect.create(app);
            redirect.dispatch(Redirect.Action.APP, "/dashboard");
        }
        else {
            // debugger;
            let server_redirect_url = ApiUrl + "/store_install" + window.location.search;
            let redirect = Redirect.create(app);
            redirect.dispatch(Redirect.Action.REMOTE, server_redirect_url);
        }
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