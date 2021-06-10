import React, { Component } from 'react';
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

export default class BillingPage extends Component {
    constructor() {
        super();
        this.state = {
            loading: true,
            processing: false,
        };
    }
    render() {
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
                                &nbsp;
								<Badge status="info"></Badge>
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
