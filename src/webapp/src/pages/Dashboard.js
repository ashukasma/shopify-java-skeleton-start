import React, { Component } from 'react'
import { Provider, Context } from '@shopify/app-bridge-react';
import { TitleBar, Button, Redirect } from '@shopify/app-bridge/actions';


export default class Dashboard extends Component {
    static contextType = Context;


    render() {
        const app = this.context;
        app.getState().then(state => console.log(state));
        const breadcrumb = Button.create(app, { label: 'My breadcrumb' });
        breadcrumb.subscribe(Button.Action.CLICK, () => {
            app.dispatch(Redirect.toApp({ path: '/breadcrumb-link' }));
        });

        const titleBarOptions = {
            title: 'My page title',
            breadcrumbs: breadcrumb
        };

        const myTitleBar = TitleBar.create(app, titleBarOptions);
        return (
            <div>

            </div>
        )
    }
}
