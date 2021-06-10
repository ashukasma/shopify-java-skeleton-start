import React, { Component } from 'react'
import { BrowserRouter } from "react-router-dom";
import { AppProvider, SkeletonPage, SkeletonThumbnail } from "@shopify/polaris";
import translations from "@shopify/polaris/locales/en.json";
import Routes from './Routes';
import isEmpty from 'is-empty';
import { Provider as AppBridgeProvider } from '@shopify/app-bridge-react'
import { getShopOrigin, setShopOrigin } from "./utils/Utils";
import { ApiUrl, shopifyApiKey } from "./Constants";
import createApp from '@shopify/app-bridge';
import { Redirect } from '@shopify/app-bridge/actions';
import { ReturnMinor } from '@shopify/polaris-icons';
import { shopConfig } from './config/appsettings';

const params = new URLSearchParams(window.location.search)
let shopOrigin = params.get('host');
if (!isEmpty(shopOrigin)) {
    setShopOrigin(shopOrigin);
}


shopOrigin = getShopOrigin();
if (shopOrigin) {
    // debugger;
    // const app = createApp(config);
    // console.log(app);
    // const server_redirect_url = ApiUrl + "/store_install" + window.location.search;
    // let redirect = Redirect.create(app);
    // redirect.dispatch(Redirect.Action.REMOTE, server_redirect_url);
}

class App extends Component {



    componentDidMount() {

    }
    render() {

        let shopOrigin = getShopOrigin();
        if (shopOrigin) {
            return (
                <div>
                    <BrowserRouter>
                        <AppProvider i18n={translations}>
                            <AppBridgeProvider config={shopConfig}>
                                <Routes></Routes>
                            </AppBridgeProvider>
                        </AppProvider>
                    </BrowserRouter>
                </div >
            )
        }
        else {
            return (<div>
                <SkeletonPage>

                </SkeletonPage>
            </div>);
        }
    }
}

export default App;