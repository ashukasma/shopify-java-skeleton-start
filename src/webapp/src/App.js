import React, { Component } from 'react'
import { BrowserRouter } from "react-router-dom";
import { AppProvider } from "@shopify/polaris";
import translations from "@shopify/polaris/locales/en.json";
import Routes from './Routes';

class App extends Component {
    render() {
        return (
            <div>
                <BrowserRouter>
                    <AppProvider i18n={translations}>
                        <Routes></Routes>
                    </AppProvider>
                </BrowserRouter>
            </div >
        )
    }
}

export default App;