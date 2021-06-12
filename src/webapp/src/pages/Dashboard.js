import React, { Component } from 'react'
import { Provider, Context } from '@shopify/app-bridge-react';
import { TitleBar, Button, Redirect } from '@shopify/app-bridge/actions';
import { Card, DisplayText, Layout, Page, SkeletonBodyText, SkeletonDisplayText } from '@shopify/polaris';


export default class Dashboard extends Component {
    constructor() {
        super();
        this.state = {
            loading: true
        }
    }
    static contextType = Context;

    render() {
        const app = this.context;
        window.app = app;
        app.getState().then(state => console.log(state));

        const titleBarOptions = {
            title: 'Dashboard'
        };

        let { loading } = this.state;

        const myTitleBar = TitleBar.create(app, titleBarOptions);
        return (
            <Page
                fullWidth="true"
                title="Dashboard"
                subtitle="Analytics">
                <Layout>
                    <Layout.Section oneThird>
                        <Card
                            title={<DisplayText size="small">Total </DisplayText>}>
                            <Card.Section>
                                {loading ? (
                                    <SkeletonBodyText />
                                ) : (
                                    <DisplayText size="extraLarge">{11}</DisplayText>
                                )}
                            </Card.Section>
                        </Card>
                    </Layout.Section>
                    <Layout.Section oneThird>
                        <Card
                            title={<DisplayText size="small">Average </DisplayText>}>
                            <Card.Section>
                                {loading ? (
                                    <SkeletonBodyText />
                                ) : (
                                    <DisplayText size="extraLarge">{11}</DisplayText>
                                )}
                            </Card.Section>
                        </Card>
                    </Layout.Section>
                    <Layout.Section oneThird>
                        <Card
                            title={<DisplayText size="small">Count </DisplayText>}>
                            <Card.Section>
                                {loading ? (
                                    <SkeletonBodyText />
                                ) : (
                                    <DisplayText size="extraLarge">{11}</DisplayText>
                                )}
                            </Card.Section>
                        </Card>
                    </Layout.Section>
                </Layout>
            </Page>
        )
    }
}
