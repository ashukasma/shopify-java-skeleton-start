import React, { Component } from 'react'
import { ApiUrl } from '../Constants';

export default class StoreInstall extends Component {
    componentDidMount() {

        let server_redirect_url = ApiUrl + "/store_install" + window.location.search;
        window.location.assign(server_redirect_url);
        //     // If the current window is the 'child', change the parent's URL with Shopify App Bridge's Redirect action
        // } else {
        //     // var app = createApp({
        //     //     apiKey: apiKey,
        //     //     shopOrigin: shopOrigin
        //     // });

        //     // Redirect.create(app).dispatch(Redirect.Action.REMOTE, permissionUrl);
        // }
    }
    render() {
        return (
            <div>

            </div>
        )
    }
}
