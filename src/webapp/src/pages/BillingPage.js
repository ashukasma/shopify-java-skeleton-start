import React, { Component } from 'react';
import { Context } from '@shopify/app-bridge-react';
import { TitleBar, Redirect } from '@shopify/app-bridge/actions';
import {
    Page,
    Layout,
    Card,
    TextContainer,
    CalloutCard,
    List,
    TextStyle,
    Badge,
} from "@shopify/polaris";
import Api from '../apis/Api';

export default class BillingPage extends Component {
    static contextType = Context;
    constructor() {
        super();
        this.state = {
            loading: true,
            processing: false,
        };
    }
    approveCharges = async () => {
        const app = this.context;
        let response = await Api.getBillingUrl();
        if (response.status == 200) {
            if (response.data.success) {
                let resData = response.data.data;
                console.log(response);
                const redirect = Redirect.create(app);
                redirect.dispatch(Redirect.Action.REMOTE, resData.confirmation_url);
                return;
            }
        }
    }
    render() {
        const app = this.context;
        window.app = app;
        const titleBarOptions = {
            title: 'Billing'
        };

        const myTitleBar = TitleBar.create(app, titleBarOptions);

        return (
            <Page
                fullWidth="true"
                breadcrumbs={[
                    {
                        content: "Billing",
                        url: "/dashboard",
                    },
                ]}
                title="Billing"
                separator
            >
                <Layout>
                    <Layout.Section>
                        <Card
                            primaryFooterAction={{
                                content: "Approve",
                                onAction: this.approveCharges,
                                loading: this.state.processing,
                            }}
                        >
                            <Card.Header title={
                                <TextStyle variation="strong">Plan: $0.01/ Message
                                
                                </TextStyle>
                            }></Card.Header>
                            <Card.Section title="OTP Message">
                                <TextContainer>
                                    We will charge on each action
								</TextContainer>
                            </Card.Section>
                        </Card>
                    </Layout.Section>
                </Layout>
            </Page>
        )
    }
}
